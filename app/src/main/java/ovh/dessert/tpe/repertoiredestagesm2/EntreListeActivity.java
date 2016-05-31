package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.EntrepriseAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;

public class EntreListeActivity extends AppCompatActivity implements EntrepriseAdapter.EntrepriseAdapterListener {

    protected List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();

        // Dans le cas où une personne veut chercher un stage partout dans le monde, on modifie la valeur de cette dernière pour que la recherche fonctionne.
        String dist = intent.getStringExtra("<Distance>");
        if(dist.equals("Partout"))
            dist = "20038 km";

        Geocoder geo = new Geocoder(this);
        LatLng centre = null;

        try {
            List<Address> list = geo.getFromLocationName(intent.getStringExtra("<City>"), 1);
            if (list.size() > 0) {
                centre = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
            }
        }catch(Exception e){
            Log.d("Erreur", e.getLocalizedMessage());
        }



        try {
            List<Entreprise> affichage = StagesDAO.getInstance(EntreListeActivity.this).searchEntreprises(EntreListeActivity.this.getApplicationContext(), intent.getStringExtra("<Nom>"), dist, intent.getStringExtra("<City>"), intent.getStringExtra("<Tags>"));
            EntrepriseAdapter adapter = new EntrepriseAdapter(this, affichage, centre);
            adapter.addListener(this);
            ListView tl = (ListView) findViewById(R.id.listlayout);
            tl.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(EntreListeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.carte_search:
                // TODO DESSERT
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(EntreListeActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
