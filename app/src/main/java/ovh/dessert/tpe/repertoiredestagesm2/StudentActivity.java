package ovh.dessert.tpe.repertoiredestagesm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Emploi;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;

public class StudentActivity extends AppCompatActivity {

    private String login;
    private Stagiaire stagiaire;
    private String nom, mail, promo, tel;
    private Emploi emploi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        login = getIntent().getStringExtra("<Login>");
        try {
            stagiaire = StagesDAO.getInstance(this).getStagiaire(login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (stagiaire != null) {
            Log.d("Stagiaire", stagiaire.toString());
            nom = stagiaire.toString();
            promo = "Promotion : " + (stagiaire.getPromotion() == null ? "Non renseignée" : stagiaire.getPromotion());
            mail = "Mail : " + (stagiaire.getMail() == null ? "Non renseigné" : stagiaire.getMail());
            tel = "Téléphone : " + (stagiaire.getTel() == null ? "Non renseigné" : stagiaire.getTel());
        }else{
            nom = "Nom Inconnu";
            mail = "Mail Inconnu";
            promo = "Promotion Inconnue";
            tel = "Tel Inconnu";
        }
        TextView nomTv = (TextView) findViewById(R.id.nom_etu);
        TextView promoTv = (TextView) findViewById(R.id.promotion_etu);
        TextView mailTv = (TextView) findViewById(R.id.mail_etu);
        TextView telTv = (TextView) findViewById(R.id.phone_etu);

        nomTv.setText(nom);
        promoTv.setText(promo);
        mailTv.setText(mail);
        telTv.setText(tel);


    }

}