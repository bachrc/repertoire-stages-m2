package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    private String ent_code, tel;

    /**
     * Crée une activité contenant des détails sur un contact particulier.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        String nom, mail, poste;
        TextView nomT, mailT, posteT;
        LinearLayout telT;

        tel = (getIntent().getStringExtra("<Tel>") == null ? "Non renseigné" : getIntent().getStringExtra("<Tel>"));
        telT = (LinearLayout) findViewById(R.id.phone_contact);
        if(telT != null){
            TextView phoneT = (TextView) findViewById(R.id.phone_contact_tv);
            if(phoneT != null){
                phoneT.setText("Téléphone : " + tel);
                telT.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                        phoneCall.setData(Uri.parse("tel:"+tel));
                        try {
                            startActivity(phoneCall);
                        }catch(Exception e){
                            Toast.makeText(ContactActivity.this, "Impossible d'ouvrir le téléphone", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }

        nom = (getIntent().getStringExtra("<Nom>") == null ? "Non renseigné" : getIntent().getStringExtra("<Nom>"));
        nomT = (TextView) findViewById(R.id.nom_contact);
        if(nomT != null){
            nomT.setText(nom);
        }

        mail = (getIntent().getStringExtra("<Mail>") == null ? "Non renseigné" : getIntent().getStringExtra("<Mail>"));
        mailT = (TextView) findViewById(R.id.mail_contact);
        if(mailT != null){
            mailT.setText("Mail : " + mail);
        }

        try{
            poste = (getIntent().getStringExtra("<Poste>") == null ? "Travaille" : getIntent().getStringExtra("<Poste>"));
            if (getIntent().getStringExtra("<Entreprise>") != null || !getIntent().getStringExtra("<Entreprise>").equals("\"Non affilié\"")){
                String f = StagesDAO.getInstance(this).getEntreprise(getIntent().getStringExtra("<Entreprise>")).getNom();
                ent_code = getIntent().getStringExtra("<Entreprise>");
                poste += " à " + f;
            }else{
                poste = "Non affilié";
            }
        }catch (Exception e){
            poste = "Non affilié";
        }

        posteT = (TextView) findViewById(R.id.poste_contact);
        if (posteT != null) {
            posteT.setText(poste);
            posteT.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            posteT.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            if(!poste.equals("Non affilié")){
                posteT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ContactActivity.this, TabbedActivity.class);
                        intent.putExtra("<Code>", ent_code);
                        startActivity(intent);
                    }
                });
            }
        }


    }
}
