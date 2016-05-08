package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;
import ovh.dessert.tpe.repertoiredestagesm2.exceptions.InvalidCSVException;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class StagesDAO extends SQLiteOpenHelper {

    private static StagesDAO db = null;

    private static final String DATABASE_NAME = "repertoire.db";
    private static final int DATABASE_VERSION = 2;

    private StagesDAO(Context context) {
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

    public void update(UpdateContext uc, Context context) throws Exception {
        CSVReader contactReader, entrepriseReader, stageReader, stagiaireReader;
        switch (uc) {
            case OFFLINE: // Dans le cas où l'update doive se faire en offline
                contactReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.contact)));
                entrepriseReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.entreprise)));
                stageReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.stage)));
                stagiaireReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.stagiaire)));
                break;
            case ONLINE:
                // TODO: 07/05/16 Télécharger les CSV depuis le serveur
                throw new UnsupportedOperationException("Téléchargement non opérationnel");
            default:
                throw new IllegalArgumentException("Cause invalide.");
        }

        // TODO: 08/05/16 Faire des toInsert.putNull si la String est vide

        SQLiteDatabase db = StagesDAO.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        int i = 1;
        String fichier = "";
        try {
            i = 1;
            this.reinit(db);
            this.onCreate(db);
            // On initialise les entreprises
            String[] nextLine;
            fichier = "entreprise.csv";
            while((nextLine = entrepriseReader.readNext()) != null) {
                if(nextLine.length % 4 != 3) // Si la taille du fichier est invalide
                    throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);
                ContentValues toInsert = new ContentValues();
                toInsert.put("nom_entreprise", nextLine[0]);
                toInsert.put("site_web", nextLine[1]);
                toInsert.put("abbr", nextLine[2]);
                db.insertOrThrow("entreprise", null, toInsert);

                for (int j = 3; j < nextLine.length; j+=4) {
                    if(nextLine[j].isEmpty()) continue; // S'il n'y a pas de nom, on skip
                    toInsert = new ContentValues();
                    toInsert.put("nom", nextLine[j]);
                    // TODO: 08/05/16 Recupérer les LatLng à partir d'une adresse
                    toInsert.put("latitude", nextLine[j+1]);
                    toInsert.put("longitude", nextLine[j+2]);
                    toInsert.put("adresse", nextLine[j+3]);
                    toInsert.put("entreprise", nextLine[2]);
                    db.insertOrThrow("Localisation", null, toInsert);
                }
                i++;
            }

            fichier = "stagiaire.csv";
            i = 1;

            while((nextLine = stagiaireReader.readNext()) != null) {
                if (nextLine.length != 8) // Si la taille du fichier est invalide
                    throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

                ContentValues toInsert = new ContentValues();

                toInsert.put("nom", nextLine[0]);
                toInsert.put("prenom", nextLine[1]);
                toInsert.put("login", nextLine[2]);
                toInsert.put("promotion", nextLine[3]);
                toInsert.put("mail", nextLine[4]);
                toInsert.put("tel", nextLine[5]);

                db.insertOrThrow("Stagiaire", null, toInsert);

                if(!nextLine[6].isEmpty() && !nextLine[7].isEmpty()) {
                    toInsert = new ContentValues();
                    toInsert.put("entreprise", nextLine[6]);
                    toInsert.put("poste", nextLine[7]);
                    toInsert.put("stagiaire", nextLine[2]);
                }
                i++;
            }

            fichier = "stage.csv";
            i = 1;
            while((nextLine = stageReader.readNext()) != null) {
                if(nextLine.length != 9) // Si la taille du fichier est invalide
                    throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

                try {
                    SimpleDateFormat test = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

                    Date debut = new Date(test.parse(nextLine[3]).getTime());
                    Date fin = new Date(test.parse(nextLine[4]).getTime());

                    ContentValues toInsert = new ContentValues();
                    toInsert.put("sujet", nextLine[0]);
                    toInsert.put("mots_cles", nextLine[1]);
                    toInsert.put("lien_rapport", nextLine[2]);
                    toInsert.put("date_debut", debut.toString());
                    toInsert.put("date_fin", fin.toString());
                    toInsert.put("nom_maitre_stage", nextLine[5]);
                    toInsert.put("nom_tuteur_stage", nextLine[6]);
                    toInsert.put("stagiaire", nextLine[7]);
                    toInsert.put("entreprise", nextLine[8]);

                    db.insertOrThrow("Stage", null, toInsert);
                } catch(ParseException pe) {
                    throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "Fichier stage.csv ligne " + i + " : Date invalide (jj/MM/aaaa)");
                } catch(SQLiteException ex) {
                    throw ex;
                }
                i++;
            }

            fichier = "contact.csv";
            i = 1;
            while((nextLine = contactReader.readNext()) != null) {
                if (nextLine.length != 7) // Si la taille du fichier est invalide
                    throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

                ContentValues toInsert = new ContentValues();
                int civ;
                if(nextLine[0].toLowerCase().equals("monsieur")) civ = 0;
                else if (nextLine[0].toLowerCase().equals("madame")) civ = 1;
                else throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "Ligne " + i + ", civilité invalide");

                toInsert.put("civilite", civ);
                toInsert.put("nom", nextLine[1]);
                toInsert.put("prenom", nextLine[2]);
                toInsert.put("entreprise", nextLine[3]);
                toInsert.put("telephone", nextLine[4]);
                toInsert.put("mail", nextLine[5]);
                toInsert.put("poste", nextLine[6]);

                db.insertOrThrow("Contact", null, toInsert);
            }
        }catch(SQLiteException sqe) {
            db.endTransaction();
            throw new InvalidCSVException(InvalidCSVException.Cause.REFERENCE_INVALIDE, "Fichier : " + fichier + sqe.getLocalizedMessage());
        }catch(InvalidCSVException ice) {
            db.endTransaction();
            throw ice;
        }catch (IOException ioe){
            db.endTransaction();
            throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "Ligne" + i);
        }



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
                "FOREIGN KEY (entreprise) REFERENCES Entreprise(abbr)");

        db.execSQL("CREATE TABLE Contact(" +
                "civilite INTEGER," +
                "nom TEXT," +
                "prenom TEXT NULL," +
                "entreprise TEXT," +
                "telephone TEXT NULL," +
                "mail TEXT NULL" +
                "poste TEXT" +
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

    private void reinit(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Stage");
        db.execSQL("DROP TABLE IF EXISTS Emploi");
        db.execSQL("DROP TABLE IF EXISTS Stagiaire");
        db.execSQL("DROP TABLE IF EXISTS Contact");
        db.execSQL("DROP TABLE IF EXISTS Localisation");
        db.execSQL("DROP TABLE IF EXISTS Entreprise");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        reinit(db);
        onCreate(db);
    }

    public enum UpdateContext {
        OFFLINE, ONLINE
    }

}
