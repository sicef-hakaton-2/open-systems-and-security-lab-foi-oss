package party.sicef.borderless.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import party.sicef.borderless.R;
import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.RefugeResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.RefugeesController;
import party.sicef.borderless.model.ProfileModel;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.CameraHelper;
import party.sicef.borderless.util.DBHelper;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class IdentificationSingleActivity extends AppCompatActivity implements BaseController.OnDataReadListener{


    private ImageView imgProfile;
    private File photoFile;
    private String mAvatarPath;
    private ProfileModel profile;
    private EditText firstName, lastName, firstInput, secondInput;
    private Location location;
    private RefugeesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification_single);

        profile= DBHelper.getProfile();
        controller=new RefugeesController(this);
        controller.setOnDataReadListener(this);
        imgProfile = (ImageView) findViewById(R.id.image_profile);
        firstName=(EditText) findViewById(R.id.text_name);
        lastName=(EditText) findViewById(R.id.text_last_name);
        firstInput =(EditText) findViewById(R.id.text_first_input);
        secondInput=(EditText) findViewById(R.id.text_second_input);

        Button btnQRCode = (Button) findViewById(R.id.button_qr);
        btnQRCode.setOnClickListener(qrCodeListener);

        if(!checkIfDeepLinked())
            showData();
        else
            ifDeepLink();

    }

    private void showData(){
        String type= PreferencesManager.getRole(this);
        if(type.equals(AppConstants.ROLE_REFUGEE)){
            showDataForRefugee();
        }else if(type.equals(AppConstants.ROLE_CITIZEN)){
            showDataForCitizen();
        }else{
            showDataForAuthority();
        }


    }

    private void showDataForRefugee(){
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        firstInput.setText(profile.getBirthDate());
        secondInput.setText(profile.getCountry());
        firstInput.setHint("Birth date");
        secondInput.setHint("Country");
        imgProfile.setOnClickListener(profileClickListener);
        if(profile.getImage()!=null) {
            setProfileImage(RestAPI.web + profile.getImage());
        }else {
            setProfileImage("http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png");
        }

    }


    private void showDataForAuthority(){

        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        firstInput.setText(profile.getCountry());
        firstInput.setHint("Country");
        secondInput.setVisibility(View.GONE);
        if (!checkIfDeepLinked()) {
            imgProfile.setOnClickListener(profileClickListener);
            if(profile.getImage()!=null) {
                setProfileImage(RestAPI.web + profile.getImage());
            }else {
                setProfileImage("http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png");
            }
        } else {
            location = getCurrentLocation();
            if (location != null) {
                Intent intent = getIntent();
                Uri deepLink = intent.getData();
                String identification = deepLink.getQueryParameter("user");
                Log.d("test", "isDeepLink");


                if (identification != null) {
                    Log.d("Identification", identification);
                } else {
                    Log.d("Identification", "Failed");
                }
            }
        }
    }

    private void ifDeepLink(){
        Log.d("test","deeplink");
        location = getCurrentLocation();
        if (location != null) {
            Intent intent = getIntent();
            Uri deepLink = intent.getData();
            String identification = deepLink.getQueryParameter("user");

            controller.getRefugee(identification);

            if (identification != null) {
                Log.d("Identification", identification);
            } else {
                Log.d("Identification", "Failed");
            }
        }else{
            Intent intent = getIntent();
            Uri deepLink = intent.getData();
            String identification = deepLink.getQueryParameter("user");

            controller.getRefugee(identification);

            if (identification != null) {
                Log.d("Identification", identification);
            } else {
                Log.d("Identification", "Failed");
            }
        }
    }

    private void showDataForCitizen(){
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        firstInput.setText(profile.getCountry());
        firstInput.setHint("Country");
        secondInput.setVisibility(View.GONE);
        if (!checkIfDeepLinked()) {
            imgProfile.setOnClickListener(profileClickListener);
            if(profile.getImage()!=null) {
                setProfileImage(RestAPI.web + profile.getImage());
            }else {
                setProfileImage("http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png");
            }
        }

    }

    /**
     * Check if activity was opened by deep link intent
     * @return true if it is, else false
     */
    private boolean checkIfDeepLinked() {
        Intent intent = getIntent();
        return !(intent == null || intent.getData() == null);
    }

    private void setProfileImage(String url) {
        Picasso.with(this)
                .load(url)
                .fit()
                .into(imgProfile);
    }

    public Location getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // create criteria for location to get best location provider
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);
        // get best provider for given criteria and use it to get current location
        String provider = lm.getBestProvider(criteria, true);
        return lm.getLastKnownLocation(provider);
    }

    private View.OnClickListener profileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // check if device has camera
            PackageManager packageManager = getPackageManager();
            if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) && !packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
                Toast.makeText(IdentificationSingleActivity.this, R.string.camera_not_available, Toast.LENGTH_SHORT)
                        .show();
            } else {
                }
            }

    };

    /**
     * Create image file to use for saving avatar
     * @return File for saving image
     * @throws IOException
     */
    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.error_opening_file, Toast.LENGTH_SHORT).show();
        }

        // Save a file: path for use with ACTION_VIEW intents
        if (image != null) {
            mAvatarPath = image.getAbsolutePath();
        }

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            }

    }

    private View.OnClickListener qrCodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(IdentificationSingleActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_qr, null);
            ImageView image = (ImageView) view.findViewById(R.id.image_qr);
            builder.setView(view);
            builder.show();

            Picasso.with(IdentificationSingleActivity.this)
                    .load(getQRCode())
                    .into(image);

        }
    };

    private String getQRCode() {
        return "http://www.chicagonow.com/digital-media-curator/files/2013/07/QR-code.jpg";
    }

    @Override
    public void onDataReceive(BaseResponse response) {
        RefugeResponse ref=(RefugeResponse) response;
        profile=DBHelper.convertRefuge(ref.getData());
        showDataForRefugee();
    }
}
