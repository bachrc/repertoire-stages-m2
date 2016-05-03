package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class StagesDAO extends SQLiteOpenHelper {

    private static StagesDAO db = null;

    private static final String DATABASE_NAME = "repertoire.db";
    private static final int DATABASE_VERSION = 1;

    public StagesDAO(Context context) throws SQLException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                "telephone TEXT," +
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
