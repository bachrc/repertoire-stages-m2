package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Classe représentant un stagiaire
 * Created by Yohann Bacha on 02/05/16.
 */
public class Stagiaire {
    private String nom;
    private String prenom;
    private String login;
    private String promotion;
    private String mail; // Peut être nul
    private String tel; // Peut être nul

    /**
     * Constructeur à partir des valeurs d'un objet Cursor
     * @param results L'objet Cursor à faire passer au constructeur
     */
    public Stagiaire(Cursor results) {
        this(results.getString(0), results.getString(1), results.getString(2), results.getString(3), (results.isNull(4) ? null : results.getString(4)), (results.isNull(5) ? null : results.getString(5)));
    }

    /**
     * Constructeur d'un objet Stagiaire
     * @param nom Le nom du stagiaire
     * @param prenom Le prénom du stagiaire
     * @param login Le login du stagiaire (Doit être UNIQUE)
     * @param promotion La promotion de l'élève
     * @param mail Le mail de l'élève (Peut être nul)
     * @param tel Le téléphone de l'élève (Peut être nul)
     */
    public Stagiaire(String nom, String prenom, String login, String promotion, String mail, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.promotion = promotion;
        this.mail = mail;
        this.tel = tel;
    }

    /**
     * Indique si l'élève a été employé depuis
     * @return Booléen indiquant si un emploi est répertorié dans la base
     * @throws Exception Si erreur SQL il y a
     */
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

    /**
     * Retourne l'Emploi rattaché à ce stagiaire
     * @return L'objet Emploi
     * @throws Exception Renvoyée s'il y a une erreur SQL, ou si aucun emploi n'existe.
     */
    public Emploi getEmploi() throws Exception{
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Emploi WHERE stagiaire = ?", new String[]{this.login});

        try {
            if (results.moveToFirst()) {
                return new Emploi(results);
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

    /**
     * Retourne la liste des Stages du stagiaire
     * @return La liste en question
     * @throws Exception Si erreur SQL il y a
     */
    public List<Stage> getStages() throws Exception{
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        List<Stage> retour = new ArrayList<>();

        Cursor results = db.rawQuery("SELECT * FROM Stage WHERE stagiaire = ?", new String[]{this.login});
        try {
            if (results.moveToFirst()) {
                do {
                    Stage temp = new Stage(results);
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

    public String toString() { return ((prenom != null)? prenom + " " : "") + nom.toUpperCase(); }

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
