package ovh.dessert.tpe.repertoiredestagesm2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class SearchMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String city, distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getIntent().getStringExtra("<City>");
        distance = getIntent().getStringExtra("<Distance>").split("[a-z ]")[0];

        // Toast.makeText(SearchMap.this, distance + "FLUFF" + city, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_search_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng centre;

        try {
            Geocoder gc = new Geocoder(SearchMap.this);
            List<Address> addresses = gc.getFromLocationName(city, 1, -90.0, -180.0, 90.0, 180.0);
            if (addresses.get(0).hasLatitude() && addresses.get(0).hasLongitude()) {
                centre = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                mMap.addMarker(new MarkerOptions().position(centre).title("Centre"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(centre));
            }
        } catch (Exception e) {

        }
    }
}
