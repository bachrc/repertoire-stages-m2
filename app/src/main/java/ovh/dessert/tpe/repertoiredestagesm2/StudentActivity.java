package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ovh.dessert.tpe.repertoiredestagesm2.adapters.StageAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.adapters.StageStudentAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Emploi;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;

public class StudentActivity extends AppCompatActivity implements StageAdapter.StageAdapterListener{

    /**
     * Crée une activité contenant tous les détails sur un étudiant.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        String login, nom, mail, promo, tel; // Login étudiant, nom, addresse mail, promotion et téléphone
        StageStudentAdapter adapter; // Liste de stages à afficher
        Stagiaire stagiaire;
        TextView nomTv, promoTv, mailTv, telTv, labelEmp, entrEmp, posteEmp; // Textviews associés aux différents attributs
        ArrayList<Stage> stages; // Liste de stages en interne
        Emploi emploi; // Emploi si existant



        nomTv = (TextView) findViewById(R.id.nom_etu);
        promoTv = (TextView) findViewById(R.id.promotion_etu);
        mailTv = (TextView) findViewById(R.id.mail_etu);
        telTv = (TextView) findViewById(R.id.phone_etu);
        labelEmp = (TextView) findViewById(R.id.label_emploi);
        entrEmp = (TextView) findViewById(R.id.entreprise_emploi);
        posteEmp = (TextView) findViewById(R.id.poste_emploi);
        ListView listView = (ListView) findViewById(R.id.liste_stage);

        // Si aucun des TextViews ne plantent
        if(nomTv != null
                && promoTv != null
                && mailTv != null
                && telTv != null
                && labelEmp != null
                && entrEmp != null
                && posteEmp != null
                && listView != null){

            // On récupère le stagiaire.
            login = getIntent().getStringExtra("<Login>");
            try {
                stagiaire = StagesDAO.getInstance(this).getStagiaire(login);
            } catch (Exception e) {
                stagiaire = null;
            }

            // S'il existe, alors
            if (stagiaire != null) {

                // On récupère les stages...
                try {
                    stages = (ArrayList<Stage>) stagiaire.getStages();
                }catch(Exception e){
                    stages = null;
                }

                // ... l'emploi, s'il en a un...
                try{
                    emploi = stagiaire.getEmploi();
                }catch(Exception e){
                    emploi = null;
                }

                // Son nom, sa promotion, son mail et son téléphone.
                nom = stagiaire.toString();
                promo = "Promotion : " + (stagiaire.getPromotion() == null ? "Non renseignée" : stagiaire.getPromotion());
                mail = "Mail : " + (stagiaire.getMail() == null ? "Non renseigné" : stagiaire.getMail());
                tel = "Téléphone : " + (stagiaire.getTel() == null ? "Non renseigné" : stagiaire.getTel());


                // On paramétrise le layout, pour mieux accomoder l'espace. Si l'emploi est non-existant,
                // les textviews associées sont "supprimés" (taille: 0dp x 0dp)
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
                    }else{
                        // Sinon, on affiche tout simplement l'emploi.
                        labelEmp.setText("Employé dans:");
                        entrEmp.setText(emploi.getEntreprise().getNom());
                        posteEmp.setText(emploi.getPoste());
                    }

                } catch(Exception e){
                    Toast.makeText(this,"Erreur lors de la formation des données", Toast.LENGTH_LONG).show();
                }

                // On affiche la liste des stages existants dans la base de données pour un étudiant.
                if (stages != null) {
                    adapter = new StageStudentAdapter(this, stages);
                    adapter.addListener(this);
                    listView.setAdapter(adapter);
                }

            }else{
                // Si l'étudiant n'existe pas dans la BDD, on garde en mémoire un "template erreur".
                nom = "Nom inconnu";
                mail = "";
                promo = "";
                tel = "";
            }
            // Et tous les TextViews associés à l'étudiant sont modifiées pour afficher les informations.
            nomTv.setText(nom);
            promoTv.setText(promo);
            mailTv.setText(mail);
            telTv.setText(tel);
        }
    }


    /**
     * Ouvre une activité contenant les détails d'un stage dans la liste des stages de l'étudiant.
     * @param item L'item tapé sur l'écran.
     * @param position La position de cet item.
     */
    @Override
    public void onClickStage(Stage item, int position) {
        // On se prépare à ouvrir une nouvelle activité
        Intent intent = new Intent(this, StageDetailActivity.class);
        try {
            // Chaînes de formattage de dates, type "1 janvier 1970"
            String dateDebut = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE).format(item.getDateDebut());
            String dateFin = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE).format(item.getDateFin());

            // On ajoute les données suivantes à passer entre les activités :
            intent.putExtra("<Sujet>", item.getSujet()); // Sujet du stage
            intent.putExtra("<Login>", item.getStagiaire().getLogin()); // Login de l'étudiant
            intent.putExtra("<Entreprise>", item.getEntreprise().getAbbr()); // Code de l'entreprise
            intent.putExtra("<Debut>", dateDebut); // Début du stage
            intent.putExtra("<Fin>", dateFin); // Fin du stage
            intent.putExtra("<Tuteur>", item.getNomTuteur()); // Nom du tuteur de stage
            intent.putExtra("<Maitre>", item.getNomMaitre()); // Nom du maître de stage
            intent.putExtra("<Rapport>", item.getLienRapport()); // URL du rapport


            startActivity(intent); // On démarre l'activité.
        }catch(Exception e){
            Toast.makeText(this, getString(R.string.erreur_stage), Toast.LENGTH_LONG).show();
        }
    }
}