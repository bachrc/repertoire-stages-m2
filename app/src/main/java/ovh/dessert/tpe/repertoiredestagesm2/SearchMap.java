package ovh.dessert.tpe.repertoiredestagesm2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class SearchMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String city, distance;
    private ArrayList<Localisation> localisations;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getStringExtra("<City>") != null)
            city = getIntent().getStringExtra("<City>");
        else
            city = "Le Havre";

        if (getIntent().getParcelableArrayListExtra("<Localisations>") != null)
            localisations = getIntent().getParcelableArrayListExtra("<Localisations>");
        else
            localisations = new ArrayList<>();

        try {
            if (getIntent().getParcelableArrayListExtra("<Entreprises>") != null) {
                ArrayList<Entreprise> temp = getIntent().getParcelableArrayListExtra("<Entreprises>");
                localisations.clear();
                for (Entreprise ent : temp)
                    for (Localisation l : ent.getLocalisations())
                        localisations.add(l);
            }
        } catch(Exception e) {
            Toast.makeText(SearchMap.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

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

        try {
            List<Address> temp = gc.getFromLocationName(city, 1);
            if(!temp.isEmpty())
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(temp.get(0).getLatitude(), temp.get(0).getLongitude()), 12.5f));

            if(!localisations.isEmpty())
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localisations.get(0).getLatitude(), localisations.get(0).getLongitude()), 12.5f));

            for(Localisation l : this.localisations) {
                if(l.getLatitude() != 0 && l.getLongitude() != 0)
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(l.getLatitude(), l.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(l.getAbbr().hashCode() % 360))
                            .title(l.getEntreprise().getNom())
                            .snippet(l.getNom()));
            }

        } catch (Exception e) {
            Log.d("Erreur", e.getMessage());
            Toast.makeText(SearchMap.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
