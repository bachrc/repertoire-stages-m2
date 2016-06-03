package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class SearchMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private String city, distance;
    private ArrayList<Localisation> localisations;
    private HashMap<Marker, String> markers;

    /**
     * Crée une activité de carte GMaps.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markers = new HashMap<>();

        if (getIntent().getStringExtra("<City>") != null)
            city = getIntent().getStringExtra("<City>");
        else
            city = "Le Havre";

        if (getIntent().getStringExtra("<Distance>") != null)
            distance = getIntent().getStringExtra("<Distance>").split(" ")[0];
        else
            distance = "0";

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

    /**
     * Prépare une carte contenant les entreprises (présentes dans un rayon si cette carte est appelée depuis une recherche par distance).
     * @param googleMap La carte à utiliser pour mettre les différents marqueurs.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng centre;
        Geocoder gc = new Geocoder(SearchMap.this);

        try {
            mMap.setOnInfoWindowClickListener(this);
            List<Address> temp = gc.getFromLocationName(city, 1);
            int rayon = Integer.parseInt(this.distance) * 1000;
            if(!temp.isEmpty())
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(temp.get(0).getLatitude(), temp.get(0).getLongitude()), 12.5f));

            else if(!localisations.isEmpty())
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localisations.get(0).getLatitude(), localisations.get(0).getLongitude()), 12.5f));

            if(rayon > 0) {
                mMap.addCircle(new CircleOptions()
                        .center(mMap.getCameraPosition().target)
                        .radius(rayon)
                        .strokeColor(0x441976D2)
                        .fillColor(0x66BBDEFB));
            }

            for(Localisation l : this.localisations) {
                if(l.getLatitude() != 0 && l.getLongitude() != 0) {
                    Marker markTemp = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(l.getLatitude(), l.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(l.getAbbr().hashCode() % 360))
                            .title(l.getEntreprise().getNom())
                            .snippet(l.getNom()));
                    this.markers.put(markTemp, l.getAbbr());
                }
            }

        } catch (Exception e) {
            Log.d("Erreur", e.getMessage());
            Toast.makeText(SearchMap.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /**
     * Lance une activité de détails d'entreprises au tap d'une infobulle.
     * @param marker Le marqueur de l'entreprise.
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(SearchMap.this, TabbedActivity.class);
        intent.putExtra("<Code>", this.markers.get(marker));
        startActivity(intent);
    }
}
