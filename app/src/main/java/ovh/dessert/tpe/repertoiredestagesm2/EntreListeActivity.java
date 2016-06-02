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

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.EntrepriseAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class EntreListeActivity extends AppCompatActivity implements EntrepriseAdapter.EntrepriseAdapterListener {

    protected List<String> data;
    private ArrayList<Localisation> localisations;
    private String dist, ville;

    /**
     * Crée une activité qui affiche la liste des entreprises par recherche.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();

        // Dans le cas où une personne veut chercher un stage partout dans le monde, on modifie la valeur de cette dernière pour que la recherche fonctionne.
        dist = intent.getStringExtra("<Distance>");
        if(dist.equals("Partout"))
            dist = "20038 km";

        Geocoder geo = new Geocoder(this);
        LatLng centre = null;

        try {
            ville = intent.getStringExtra("<City>");
            List<Address> list = geo.getFromLocationName(ville, 1);
            if (list.size() > 0) {
                centre = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
            }
        }catch(Exception e){
            Log.d("Erreur", e.getLocalizedMessage());
        }



        try {
            this.localisations = new ArrayList<>();
            List<Entreprise> affichage = StagesDAO.getInstance(EntreListeActivity.this).searchEntreprises(EntreListeActivity.this.getApplicationContext(), localisations, intent.getStringExtra("<Nom>"), dist, intent.getStringExtra("<City>"), intent.getStringExtra("<Tags>"));
            EntrepriseAdapter adapter = new EntrepriseAdapter(this, affichage, centre);
            adapter.addListener(this);
            ListView tl = (ListView) findViewById(R.id.listlayout);
            tl.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(EntreListeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            finish();
        }

    }

    /**
     * Prépare les boutons d'affichage sur le menu supérieur.
     * @param menu Le menu supérieur
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    /**
     * Lance une activité différence sur tap d'un bouton du menu.
     * @param item L'item du menu supérieur
     * @return true, si l'activité a été lancée.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.carte_search:
                Intent intent = new Intent(EntreListeActivity.this, SearchMap.class);
                intent.putExtra("<City>", this.ville);
                intent.putExtra("<Distance>", this.dist);
                intent.putParcelableArrayListExtra("<Localisations>", this.localisations);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Lance une activité de détails d'entreprise sur l'item correspondant.
     * @param item L'item de la liste
     * @param position La position de cet item
     */
    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(EntreListeActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
