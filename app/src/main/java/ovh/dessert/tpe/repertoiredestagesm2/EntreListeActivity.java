package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

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

        try {
            List<Entreprise> affichage = StagesDAO.getInstance(EntreListeActivity.this).searchEntreprises(EntreListeActivity.this.getApplicationContext(), intent.getStringExtra("<Nom>"), dist, intent.getStringExtra("<City>"), intent.getStringExtra("<Tags>"));
            EntrepriseAdapter adapter = new EntrepriseAdapter(this, affichage);
            adapter.addListener(this);
            ListView tl = (ListView) findViewById(R.id.listlayout);
            tl.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(EntreListeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            finish();
        }

    }


    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(EntreListeActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
