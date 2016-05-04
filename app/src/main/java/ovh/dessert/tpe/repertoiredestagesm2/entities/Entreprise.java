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
public class Entreprise {
    private String nom;
    private String siteweb;
    private String abbr;

    public Entreprise(String nom, String siteweb, String abbr) {
        this.nom = nom;
        this.siteweb = siteweb;
        this.abbr = abbr;
    }

    public List<Contact> getContacts() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Contact WHERE entreprise = ?", new String[]{this.abbr});
        List<Contact> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Contact temp = new Contact(results.getInt(0), results.getString(1), results.getString(2), results.getString(3), results.getString(4));
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public List<Localisation> getLocalisations() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Localisation WHERE entreprise = ?", new String[]{this.abbr});
        List<Localisation> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Localisation temp = new Localisation(results.getDouble(0), results.getDouble(1), results.getString(2), results.getString(3));
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public List<Stage> getStages() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Stage WHERE entreprise = ?", new String[]{this.abbr});
        List<Stage> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Stage temp = new Stage(results.getString(0), results.getString(1), results.getString(2), results.getString(3), results.getString(4), Date.valueOf(results.getString(5)), Date.valueOf(results.getString(6)), results.getString(7), results.getString(8));
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public String getNom() {
        return nom;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public String getAbbr() {
        return abbr;
    }
}
