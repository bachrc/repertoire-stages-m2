package ovh.dessert.tpe.repertoiredestagesm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.EntrepriseAdapter;

public class EntreListeActivity extends AppCompatActivity {

    private String city, distance;
    protected List<String> data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        EntrepriseAdapter adapter = new EntrepriseAdapter(this);
        ListView tl = (ListView) findViewById(R.id.listlayout);
        tl.setAdapter(adapter);
    }
}
