package ovh.dessert.tpe.repertoiredestagesm2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class SearchMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String city, distance, ent, adr;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra("<City>") != null)
            city = getIntent().getStringExtra("<City>");

        if (getIntent().getStringExtra("<Distance>") != null)
            distance = getIntent().getStringExtra("<Distance>").split("[a-z ]")[0];

        if (getIntent().getStringExtra("<Adresse>") != null)
            adr = getIntent().getStringExtra("<Adresse>");

        if (getIntent().getStringExtra("<Nom>") != null)
            ent = getIntent().getStringExtra("<Nom>");

        lat = getIntent().getDoubleExtra("<Latitude>", 0);
        lng = getIntent().getDoubleExtra("<Longitude>", 0);
        // Toast.makeText(SearchMap.this, distance + "FLUFF" + city, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_search_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng centre;
        Geocoder gc = new Geocoder(SearchMap.this);

        if(city == null){
            centre = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(centre).title(ent));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 12.5f));
        }else{
            try {
                List<Address> addresses = gc.getFromLocationName(city, 1, -90.0, -180.0, 90.0, 180.0);
                if (addresses.get(0).hasLatitude() && addresses.get(0).hasLongitude()) {
                    centre = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    mMap.addMarker(new MarkerOptions().position(centre).title("Centre"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 13.0f));
                }
            } catch (Exception e) {
                Log.d("Erreur", e.getMessage());
            }
        }
    }
}
