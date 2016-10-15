package party.sicef.borderless.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import party.sicef.borderless.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String KEY_LAT = "lat";
    public static final String KEY_LON = "lon";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";

    private GoogleMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getIntent().getExtras() != null) {
            double lat = Double.valueOf(getIntent().getExtras().getString(KEY_LAT));
            double lng = Double.valueOf(getIntent().getExtras().getString(KEY_LON));
            String distance = getIntent().getExtras().getString(KEY_DISTANCE);
            String title = getIntent().getExtras().getString(KEY_TITLE);
            String description = getIntent().getExtras().getString(KEY_DESCRIPTION);

            TextView tvDistance = (TextView) findViewById(R.id.text_distance);
            tvDistance.setText(distance);

            TextView tvTitle = (TextView) findViewById(R.id.title);
            tvTitle.setText(title);

            TextView tvDescription = (TextView) findViewById(R.id.details);
            tvDescription.setText(description);

            latLng = new LatLng(lat, lng);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }
}
