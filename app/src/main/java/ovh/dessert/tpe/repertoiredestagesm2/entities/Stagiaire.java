package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Stagiaire {
    private String nom;
    private String prenom;
    private String login;
    private String promotion;
    private String mail;
    private String tel;

    public Stagiaire(String nom, String prenom, String login, String promotion, String mail, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.promotion = promotion;
        this.mail = mail;
        this.tel = tel;
    }

    public boolean isEmployed() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();

        Cursor results = db.rawQuery("SELECT * FROM Emploi WHERE stagiaire = ?", new String[]{this.login});

        try {
            return results.getColumnCount() != 0;
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }
    }

    public Emploi getEmploi() throws Exception{
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Emploi WHERE stagiaire = ?", new String[]{this.login});

        try {
            if (results.moveToFirst()) {
                return new Emploi(results.getString(0), results.getString(1), results.getString(2));
            } else
                throw new Exception("Stagiaire non employé par la suite.");
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }
    }

    public List<Stage> getStages() throws Exception{
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        List<Stage> retour = new ArrayList<>();

        Cursor results = db.rawQuery("SELECT * FROM Stage WHERE stagiaire = ?", new String[]{this.login});
        try {
            if (results.moveToFirst()) {
                do {
                    Stage temp = new Stage(results.getString(0), results.getString(1), results.getString(2), results.getString(3), results.getString(4), Date.valueOf(results.getString(5)), Date.valueOf(results.getString(6)), results.getString(7), results.getString(8));
                    retour.add(temp);
                } while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed())
                results.close();
        }

        return retour;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }
}
