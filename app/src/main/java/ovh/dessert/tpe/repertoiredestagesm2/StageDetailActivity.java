package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class StageDetailActivity extends AppCompatActivity  {

    private String ent_code, etu_code;
    private final String NR = "ITEM_NON_RENSEIGNE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);

        String sujet, date, ent, etu, tut, mait, rapport;
        TextView sT, etuT, entT, dateT, tutT, maitT, rapportT, etuL, entL;

        sujet = (getIntent().getStringExtra("<Sujet>") == null ? "Non renseigné" : getIntent().getStringExtra("<Sujet>"));
        try {
            etu = StagesDAO.getInstance(this).getStagiaire(getIntent().getStringExtra("<Login>")).toString();
            etu_code = getIntent().getStringExtra("<Login>");
        }catch (Exception e){
            etu = "Non renseigné";
            etu_code = NR;
        }

        try {
            ent = StagesDAO.getInstance(this).getEntreprise(ent = getIntent().getStringExtra("<Entreprise>")).getNom();
            ent_code = getIntent().getStringExtra("<Entreprise>");
        }catch (Exception e){
            ent = "Non renseigné";
            ent_code = NR;
        }

        if(getIntent().getStringExtra("<Debut>") != null && getIntent().getStringExtra("<Fin>") != null){
            date = "Du " + getIntent().getStringExtra("<Debut>") + " au " + getIntent().getStringExtra("<Fin>");
        }else{
            date = "Non renseignée";
        }

        tut = (getIntent().getStringExtra("<Tuteur>") == null ? "Non renseigné(e)" : getIntent().getStringExtra("<Tuteur>"));
        mait = (getIntent().getStringExtra("<Maitre>") == null ? "Non renseigné(e)" : getIntent().getStringExtra("<Maitre>"));
        rapport = (getIntent().getStringExtra("<Rapport>") == null ? "Non disponible" : getIntent().getStringExtra("<Rapport>"));

        sT = (TextView) findViewById(R.id.sta_sujet);
        if(sT != null)
            sT.setText(sujet);

        etuT = (TextView) findViewById(R.id.sta_nom_etu);
        if(etuT != null) {
            etuT.setText(etu);
            etuT.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            etuT.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if(!etu.equals(NR)){
                etuT.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(StageDetailActivity.this, StudentActivity.class);
                        i.putExtra("<Login>", etu_code);
                        startActivity(i);
                    }
                });
            }
        }

        entT = (TextView) findViewById(R.id.sta_nom_ent);
        if(entT != null) {
            entT.setText(ent);
            entT.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            entT.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if(!ent.equals(NR)){
                entT.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                    Intent intent = new Intent(StageDetailActivity.this, TabbedActivity.class);
                    intent.putExtra("<Code>", ent_code);
                    startActivity(intent);
                    }
                });
            }
        }

        dateT = (TextView) findViewById(R.id.sta_date);
        if(dateT != null)
            dateT.setText(date);

        tutT = (TextView) findViewById(R.id.sta_tuteur);
        if(tutT != null)
            tutT.setText(tut);

        maitT = (TextView) findViewById(R.id.sta_maitre);
        if(maitT != null)
            maitT.setText(mait);

        rapportT = (TextView) findViewById(R.id.sta_rapport);
        if(rapportT != null)
            rapportT.setText(rapport);

        etuL = (TextView) findViewById(R.id.sta_label_etu);
        if(etuL != null && !etu.equals(NR)) {
            etuL.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(StageDetailActivity.this, StudentActivity.class);
                    i.putExtra("<Login>", etu_code);
                    startActivity(i);
                }
            });
        }

        entL = (TextView) findViewById(R.id.sta_label_ent);
        if(entL != null && !ent_code.equals(NR)){
            entL.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StageDetailActivity.this, TabbedActivity.class);
                    intent.putExtra("<Code>", ent_code);
                    startActivity(intent);
                }
            });
        }
    }
}
