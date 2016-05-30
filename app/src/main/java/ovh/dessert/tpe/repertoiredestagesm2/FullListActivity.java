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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_list);

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

    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(FullListActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
