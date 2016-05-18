package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.EntrepriseAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;

public class EntreListeActivity extends AppCompatActivity implements EntrepriseAdapter.EntrepriseAdapterListener {

    private String city, distance;
    protected List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        EntrepriseAdapter adapter = new EntrepriseAdapter(this);
        adapter.addListener(this);
        ListView tl = (ListView) findViewById(R.id.listlayout);
        tl.setAdapter(adapter);
    }


    @Override
    public void onClickEntreprise(Entreprise item, int position) {
        Intent intent = new Intent(EntreListeActivity.this, TabbedActivity.class);
        intent.putExtra("<Code>", item.getAbbr());
        startActivity(intent);
    }
}
