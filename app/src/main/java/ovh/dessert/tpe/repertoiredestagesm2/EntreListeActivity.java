package ovh.dessert.tpe.repertoiredestagesm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.entities.DessertClass;

public class EntreListeActivity extends AppCompatActivity {

    private String city, distance;
    protected List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // List<Entreprise> ents = jsp;
        List<String> ents = new ArrayList<>();
        for(int i = 0; i < 10 ; i++){
            ents.add("Communauté de Communes de la Cote d'Albatre DESSERT DESSERT DESSERT DESSERT DESSERT" + i);
        }
        // EntrepriseAdapter adapter = new EntrepriseAdapter(this, ents);
        DessertClass adapter = new DessertClass(this, ents);
        ListView tl = (ListView) findViewById(R.id.listlayout);
        tl.setAdapter(adapter);
    }
}
