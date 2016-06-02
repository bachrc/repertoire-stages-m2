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

    /**
     * Crée une activité contenant tous les détails sur un stage.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);

        String sujet, date, ent, etu, tut, mait, rapport; // Sujet, date, entreprise, étudiant, tuteur, maitre, lien du rapport
        TextView sT, etuT, entT, dateT, tutT, maitT, rapportT, etuL, entL; // Et les textviews associées.

        // On récupère le sujet du stage...
        sujet = (getIntent().getStringExtra("<Sujet>") == null ? "Non renseigné" : getIntent().getStringExtra("<Sujet>"));

        //... le login étudiant ainsi que son nom...
        try {
            etu = StagesDAO.getInstance(this).getStagiaire(getIntent().getStringExtra("<Login>")).toString();
            etu_code = getIntent().getStringExtra("<Login>");
        }catch (Exception e){
            etu = "Non renseigné";
            etu_code = NR;
        }

        // ... l'entreprise ainsi que son nom...
        try {
            ent = StagesDAO.getInstance(this).getEntreprise(ent = getIntent().getStringExtra("<Entreprise>")).getNom();
            ent_code = getIntent().getStringExtra("<Entreprise>");
        }catch (Exception e){
            ent = "Non renseigné";
            ent_code = NR;
        }

        // ... la date de début et de fin. S'il n'existe pas une des deux dates, alors les dates ne sont pas renseignées...
        if(getIntent().getStringExtra("<Debut>") != null && getIntent().getStringExtra("<Fin>") != null){
            date = "Du " + getIntent().getStringExtra("<Debut>") + " au " + getIntent().getStringExtra("<Fin>");
        }else{
            date = "Non renseignée";
        }

        // ... et le tuteur, maître et lien du rapport de stage.
        tut = (getIntent().getStringExtra("<Tuteur>") == null ? "Non renseigné(e)" : getIntent().getStringExtra("<Tuteur>"));
        mait = (getIntent().getStringExtra("<Maitre>") == null ? "Non renseigné(e)" : getIntent().getStringExtra("<Maitre>"));
        rapport = (getIntent().getStringExtra("<Rapport>") == null ? "Non disponible" : getIntent().getStringExtra("<Rapport>"));

        // On place ensuite les données qu'on a extraites dans les TextViews qui correspondent.
        // Stage
        sT = (TextView) findViewById(R.id.sta_sujet);
        if(sT != null)
            sT.setText(sujet);

        // Etudiant
        etuT = (TextView) findViewById(R.id.sta_nom_etu);
        if(etuT != null) {
            etuT.setText(etu);
            etuT.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG); // On souligne pour indiquer qu'une action est possible
            etuT.setTextColor(ContextCompat.getColor(this, R.color.colorAccent)); // Et on change la couleur, pour que cette action potentielle soit visible.
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

        // Entreprise. On souligne et recolore de la même manière que pour Etudiant.
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

        // Date
        dateT = (TextView) findViewById(R.id.sta_date);
        if(dateT != null)
            dateT.setText(date);

        // Tuteur de stage
        tutT = (TextView) findViewById(R.id.sta_tuteur);
        if(tutT != null)
            tutT.setText(tut);

        // Maître de stage
        maitT = (TextView) findViewById(R.id.sta_maitre);
        if(maitT != null)
            maitT.setText(mait);

        // Lien du rapport de stage
        rapportT = (TextView) findViewById(R.id.sta_rapport);
        if(rapportT != null)
            rapportT.setText(rapport);


        // On ajoute également l'action pour les labels "étudiant" et "entreprise", pour agrandir la
        // zone où l'action peut être effectuée.
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
