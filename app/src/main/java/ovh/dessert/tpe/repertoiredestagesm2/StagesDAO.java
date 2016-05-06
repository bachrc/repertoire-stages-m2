package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class StagesDAO extends SQLiteOpenHelper {

    private static StagesDAO db = null;

    private static final String DATABASE_NAME = "repertoire.db";
    private static final int DATABASE_VERSION = 2;

    public StagesDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Entreprise> getAllEntreprises() throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Entreprise> retour = new ArrayList<>();

        Cursor results = db.rawQuery("SELECT * FROM Entreprise", null);
        try {
            if (results.moveToFirst()) {
                do {
                    Entreprise temp = new Entreprise(results);
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed())
                results.close();
        }

        return retour;
    }

    public Stagiaire getStagiaire(String login) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Stagiaire retour;

        Cursor results = db.rawQuery("SELECT * FROM Stagiaire WHERE login= ?", new String[]{login});
        try {
            if (results.moveToFirst())
                retour = new Stagiaire(results);
            else
                throw new Exception("Stagiaire inexistant.");

        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public Entreprise getEntreprise(String abbr) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Entreprise retour;

        Cursor results = db.rawQuery("SELECT * FROM Entreprise WHERE abbr = ?", new String[]{abbr});
        try {
            if (results.moveToFirst())
                retour = new Entreprise(results);
            else
                throw new Exception("Entreprise non existante.");

        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public static synchronized StagesDAO getInstance(Context context) {
        if (db == null)
            db = new StagesDAO(context.getApplicationContext());

        return db;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Entreprise(" +
                "nom_entreprise TEXT," +
                "site_web TEXT," +
                "abbr TEXT PRIMARY KEY)");


        db.execSQL("CREATE TABLE Localisation(" +
                "nom TEXT," +
                "latitude REAL NULL," +
                "longitude REAL NULL," +
                "adresse TEXT NULL," +
                "entreprise TEXT," +
                "FOREIGN KEY (entreprise) REFERENCES Entreprise(abbr)," +
                "CHECK (adresse is not null or (latitude is not null and longitude is not null)))");

        db.execSQL("CREATE TABLE Contact(" +
                "civilite INTEGER," +
                "nom TEXT," +
                "prenom TEXT NULL," +
                "entreprise TEXT," +
                "telephone TEXT NULL," +
                "mail TEXT NULL" +
                "FOREIGN KEY(entreprise) REFERENCES Entreprise(abbr))");

        db.execSQL("CREATE TABLE Stagiaire(" +
                "nom TEXT," +
                "prenom TEXT," +
                "login TEXT PRIMARY KEY," +
                "promotion TEXT," +
                "mail TEXT NULL," +
                "tel TEXT NULL)");

        db.execSQL("CREATE TABLE Emploi(" +
                "entreprise TEXT," +
                "stagiaire TEXT," +
                "poste TEXT," +
                "FOREIGN KEY(entreprise) REFERENCES Entreprise(abbr)," +
                "FOREIGN KEY(stagiaire) REFERENCES Stagiaire(login))");

        db.execSQL("CREATE TABLE Stage(" +
                "sujet TEXT," +
                "mots_cles TEXT NULL," +
                "lien_rapport TEXT NULL," +
                "stagiaire TEXT," +
                "entreprise TEXT," +
                "date_debut TEXT," +
                "date_fin TEXT," +
                "nom_maitre_stage TEXT NULL," +
                "nom_tuteur_stage TEXT NULL," +
                "FOREIGN KEY(stagiaire) REFERENCES Stagiaire(login)," +
                "FOREIGN KEY(entreprise) REFERENCES Entreprise(abbr))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Stage");
        db.execSQL("DROP TABLE IF EXISTS Emploi");
        db.execSQL("DROP TABLE IF EXISTS Stagiaire");
        db.execSQL("DROP TABLE IF EXISTS Contact");
        db.execSQL("DROP TABLE IF EXISTS Localisation");
        db.execSQL("DROP TABLE IF EXISTS Entreprise");

        onCreate(db);
    }


}
