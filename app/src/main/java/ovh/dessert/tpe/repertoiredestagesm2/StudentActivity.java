package ovh.dessert.tpe.repertoiredestagesm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.StageStudentAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Emploi;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;

public class StudentActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        String login, nom, mail, promo, tel;
        StageStudentAdapter adapter;
        Stagiaire stagiaire;
        TextView nomTv, promoTv, mailTv, telTv, labelEmp, entrEmp, posteEmp;
        ArrayList<Stage> stages;
        Emploi emploi;



        nomTv = (TextView) findViewById(R.id.nom_etu);
        promoTv = (TextView) findViewById(R.id.promotion_etu);
        mailTv = (TextView) findViewById(R.id.mail_etu);
        telTv = (TextView) findViewById(R.id.phone_etu);
        labelEmp = (TextView) findViewById(R.id.label_emploi);
        entrEmp = (TextView) findViewById(R.id.entreprise_emploi);
        posteEmp = (TextView) findViewById(R.id.poste_emploi);
        ListView listView = (ListView) findViewById(R.id.liste_stage);

        if(nomTv != null
                && promoTv != null
                && mailTv != null
                && telTv != null
                && labelEmp != null
                && entrEmp != null
                && posteEmp != null
                && listView != null){

            login = getIntent().getStringExtra("<Login>");
            try {
                stagiaire = StagesDAO.getInstance(this).getStagiaire(login);
            } catch (Exception e) {
                stagiaire = null;
            }

            if (stagiaire != null) {

                try {
                    stages = (ArrayList<Stage>) stagiaire.getStages();
                }catch(Exception e){
                    stages = null;
                }

                try{
                    emploi = stagiaire.getEmploi();
                }catch(Exception e){
                    emploi = null;
                }

                nom = stagiaire.toString();
                promo = "Promotion : " + (stagiaire.getPromotion() == null ? "Non renseignée" : stagiaire.getPromotion());
                mail = "Mail : " + (stagiaire.getMail() == null ? "Non renseigné" : stagiaire.getMail());
                tel = "Téléphone : " + (stagiaire.getTel() == null ? "Non renseigné" : stagiaire.getTel());

                try{
                    if (emploi == null){
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        lp.setMargins(0, 0, 0, 0);

                        labelEmp.setHeight(0);
                        labelEmp.setLayoutParams(lp);
                        entrEmp.setHeight(0);
                        entrEmp.setLayoutParams(lp);
                        posteEmp.setHeight(0);
                        posteEmp.setLayoutParams(lp);
                    }else {
                        labelEmp.setText("Employé dans:");
                        entrEmp.setText(emploi.getEntreprise().getNom());
                        posteEmp.setText(emploi.getPoste());
                    }

                } catch(Exception e){
                    Toast.makeText(this,"Erreur lors de la formation des données", Toast.LENGTH_LONG).show();
                }

                if (stages != null) {
                    adapter = new StageStudentAdapter(this, stages);
                    listView.setAdapter(adapter);
                }

            }else{
                nom = "Nom inconnu";
                mail = "";
                promo = "";
                tel = "";
            }

            nomTv.setText(nom);
            promoTv.setText(promo);
            mailTv.setText(mail);
            telTv.setText(tel);
        }
    }

}