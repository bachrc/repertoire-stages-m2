package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class DebuggingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debugging);
    }

    public void frag(View v){
        Intent intent = new Intent(DebuggingActivity.this, TabbedActivity.class);
        startActivity(intent);
    }

    public void initDB(View v) {
        final TextView textViewToChange = (TextView) findViewById(R.id.textView);
        try {
            String aAfficher;
            StagesDAO test = StagesDAO.getInstance(getApplicationContext());
            test.updateLocal(this.getApplicationContext());
            List<Entreprise> ent = test.getAllEntreprises();
            aAfficher = "C'est coule : " + ent.size() + "\n";
            if(!ent.isEmpty()) {
                for(Entreprise e:ent) {
                    aAfficher += e.getNom() + '\n';
                    for(Localisation l : e.getLocalisations()) {
                         aAfficher += l.getNom() + '\n';
                    }
                }
            }
            textViewToChange.setText(aAfficher);
        }catch(Exception e) {
            textViewToChange.setText("C'est pas coule.");
            Log.d("initdb", e.toString());
        }
    }
}
