package party.sicef.borderless.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import party.sicef.borderless.R;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.LogInController;
import party.sicef.borderless.gcm.RegistrationIntentService;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;
import retrofit.RetrofitError;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class LogInActivity extends AppCompatActivity implements View.OnClickListener, BaseController.OnDataReadListener, BaseController.OnDataErrorListener{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private Button logIn;
    private AppCompatEditText username, password;
    private CheckBox rememberMe;
    private LogInController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setupViews();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        if(PreferencesManager.getRole(this)!=null && PreferencesManager.getRole(this).equals(AppConstants.ROLE_REFUGEE)){
            Button ident=(Button) findViewById(R.id.btnIdent);
            ident.setVisibility(View.VISIBLE);
            ident.setOnClickListener(this);
        }
    }

    private void setupViews() {
        username = (AppCompatEditText) findViewById(R.id.email);
        password = (AppCompatEditText) findViewById(R.id.password);
        rememberMe = (CheckBox) findViewById(R.id.chckRememberMe);
        logIn = (Button) findViewById(R.id.btnLogin);
        logIn.setOnClickListener(this);
        controller=new LogInController(this);
        controller.setOnDataReadListener(this);
        controller.setOnDataErrorListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(LogInActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                } else {
                    controller.logIn(username.getText().toString(),password.getText().toString(), PreferencesManager.getGcmToken(this));
                }
                break;
            case R.id.btnIdent:
                startActivity(new Intent(this,IdentificationSingleActivity.class));
                break;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onDataReceive(BaseResponse response) {
        if (PreferencesManager.getRole(LogInActivity.this).equals(AppConstants.ROLE_AUTHORITY)) {
            startActivity(new Intent(this, RefugeesActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void onDataErrorReceive(RetrofitError error) {
        // TODO write message if user is not able to login
    }
}

