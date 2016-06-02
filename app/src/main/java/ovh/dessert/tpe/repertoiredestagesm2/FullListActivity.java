package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.EntrepriseAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;

public class FullListActivity extends AppCompatActivity implements EntrepriseAdapter.EntrepriseAdapterListener {

    /**
     * Crée la liste entière d'entreprises depuis la base de données.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_list);

        // On essaie de récupérer la liste d'entreprises, ainsi que d'écouter chaque item qui correspond.
        // Si la liste ne conclut pas, alors on renvoie une erreur.
        try {
            List<Entreprise> affichage = StagesDAO.getInstance(this).getAllEntreprises();
            EntrepriseAdapter adapter = new EntrepriseAdapter(this, affichage);
            adapter.addListener(this);
            ListView tl = (ListView) findViewById(R.id.list_entreprise);
            tl.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Lance une activité de détails d'entreprise sur l'item correspondant.
     * @param item L'item de la liste
     * @param position La position de cet item
     */
    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(FullListActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
