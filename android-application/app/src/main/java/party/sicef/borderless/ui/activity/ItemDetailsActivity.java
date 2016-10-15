package party.sicef.borderless.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import party.sicef.borderless.R;
import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.NewResponse;
import party.sicef.borderless.controller.BaseController;
import party.sicef.borderless.controller.NewsController;

/**
 * Created by Andro on 11/14/2015.
 */
public class ItemDetailsActivity extends AppCompatActivity implements BaseController.OnDataReadListener{

    public static final String TAG_IMAGE_DETAILS = "image_details";
    public static final String TAG_TITLE_DETAILS = "title_details";
    public static final String TAG_ID = "id";

    public NewsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String imageUrl = bundle.getString(TAG_IMAGE_DETAILS);

        ImageView imgDetails = (ImageView) findViewById(R.id.text_distance);
        Picasso.with(this)
                .load(RestAPI.web + imageUrl)
                .into(imgDetails);
        controller=new NewsController(this);
        controller.setOnDataReadListener(this);

        int id = bundle.getInt(TAG_ID);
        controller.getNew(id);
    }

    @Override
    public void onDataReceive(BaseResponse response) {
        NewResponse newResponse=(NewResponse) response;
        TextView tvDetails = (TextView) findViewById(R.id.details);
        tvDetails.setText(newResponse.getData().getText());
    }
}