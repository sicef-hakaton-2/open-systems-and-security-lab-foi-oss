package party.sicef.borderless.ui.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import party.sicef.borderless.R;
import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.controller.RefugeesController;
import party.sicef.borderless.model.ProfileModel;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class ContactActivity extends AppCompatActivity {

    public static String KEY_NAME="key_name";
    public static String KEY_LASTNAME="key_lastname";
    public static String KEY_birthDate="birth_date";
    public static String KEY_COUNTRY="country";
    public static String KEY_EMAIL="email";
    public static String KEY_IMAGE="image";


    private ImageView imgProfile;
    private String mAvatarPath;
    private EditText firstName, lastName, birthDay, country,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgProfile = (ImageView) findViewById(R.id.image_profile);
        firstName=(EditText) findViewById(R.id.text_name);
        lastName=(EditText) findViewById(R.id.text_last_name);
        birthDay =(EditText) findViewById(R.id.text_first_input);
        country=(EditText) findViewById(R.id.text_second_input);
        email=(EditText) findViewById(R.id.text_third_input);

        showData();

    }

    private void showData(){
        Intent intent=getIntent();

        firstName.setText(intent.getStringExtra(KEY_NAME));
        lastName.setText(intent.getStringExtra(KEY_LASTNAME));
        if(intent.getStringExtra(KEY_birthDate)!=null)
            birthDay.setText(intent.getStringExtra(KEY_birthDate));
        else
            birthDay.setVisibility(View.GONE);
        country.setText(intent.getStringExtra(KEY_COUNTRY));
        email.setText(intent.getStringExtra(KEY_EMAIL));

        if(intent.getStringExtra(KEY_IMAGE)!=null) {
            setProfileImage(RestAPI.web + intent.getStringExtra(KEY_IMAGE));
        }else {
            setProfileImage("http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png");
        }

    }


    private void setProfileImage(String url) {
        Picasso.with(this)
                .load(url)
                .fit()
                .into(imgProfile);
    }
}
