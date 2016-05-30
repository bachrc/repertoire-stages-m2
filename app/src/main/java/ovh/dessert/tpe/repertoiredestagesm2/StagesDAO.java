package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stagiaire;
import ovh.dessert.tpe.repertoiredestagesm2.exceptions.InvalidCSVException;

public class StagesDAO extends SQLiteOpenHelper {

    private static StagesDAO db = null;
    private static Context context;

    private static final String DATABASE_NAME = "repertoire.db";
    private static final int DATABASE_VERSION = 3;

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

    public List<Entreprise> searchEntreprises(Context context, String nom, String rayon, String ville, String tags) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        List<Entreprise> retour = new ArrayList<>();

        // Génération de la requête SQL de recherche

        String requete = "SELECT * FROM Entreprise";

        if(!nom.isEmpty() || !tags.isEmpty()) {
            requete += " WHERE ";
            if(!nom.isEmpty()) {
                requete += "nom_entreprise LIKE '%" + nom.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[", "![").replace("'", "!'") + "%'";
                if (!tags.isEmpty())
                    requete += " AND ";
            }
            if(!tags.isEmpty()) {
                requete += "abbr IN(SELECT DISTINCT entreprise FROM Stage WHERE ";
                for(String tag:tags.split(";")) {
                    requete += "mots_cles LIKE '%" + tag.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[", "![").replace("'", "!'") + "%' AND ";
                }
                requete = requete.substring(0, requete.length() - 5) + " ESCAPE '!' )";
            }
        }

        Cursor results = db.rawQuery(requete, null);

        try {
            if (results.moveToFirst()) {
                do {
                    Entreprise temp = new Entreprise(results);
                    if(!ville.isEmpty()) {
                        int distance = Integer.parseInt(rayon.split(" ")[0]);

                        Geocoder geo = new Geocoder(context);
                        List<Address> list = geo.getFromLocationName(ville, 1);
                        if (list.size() > 0) {
                            for(Localisation loc:temp.getLocalisations()) {
                                if(Localisation.distance(list.get(0).getLatitude(), loc.getLatitude(), list.get(0).getLongitude(), loc.getLongitude()) <= distance) {
                                    retour.add(temp);
                                    break;
                                }
                            }
                        }
                    } else {
                        retour.add(temp);
                    }
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed())
                results.close();
        }

        return retour;

    }

    public static synchronized StagesDAO getInstance(Context context) {
        if (db == null)
            db = new StagesDAO(context.getApplicationContext());

        return db;
    }

    private void readEntreprise(CSVReader reader, SQLiteDatabase db, boolean getLatLng, Context context) throws Exception {
        int i = 1;
        String[] nextLine;
        String fichier = "entreprise.csv";

        while((nextLine = reader.readNext()) != null) {
            if(nextLine.length % 4 != 3) // Si la taille du fichier est invalide
                throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);
            ContentValues toInsert = new ContentValues();
            putIfNull(toInsert,"nom_entreprise", nextLine[0]);
            putIfNull(toInsert,"site_web", nextLine[1]);
            putIfNull(toInsert,"abbr", nextLine[2]);
            db.insertOrThrow("entreprise", null, toInsert);

            for (int j = 3; j < nextLine.length; j+=4) {
                if(nextLine[j].isEmpty()) continue; // S'il n'y a pas de nom, on skip
                toInsert = new ContentValues();
                putIfNull(toInsert,"nom", nextLine[j]);
                if(getLatLng)
                    if(nextLine[j+1].isEmpty() && nextLine[j+2].isEmpty() && !nextLine[j+3].isEmpty()) {
                        Geocoder geo = new Geocoder(context);
                        List<Address> list = geo.getFromLocationName(nextLine[j+3], 1);
                        if(list.size() > 0) {
                            Address temp = list.get(0);
                            nextLine[j+1] = Double.toString(temp.getLatitude());
                            nextLine[j+2] = Double.toString(temp.getLongitude());
                            Log.d("chocolat", temp.toString());
                        }
                    }

                putIfNull(toInsert,"latitude", nextLine[j+1]);
                putIfNull(toInsert,"longitude", nextLine[j+2]);
                putIfNull(toInsert,"adresse", nextLine[j+3]);
                putIfNull(toInsert,"entreprise", nextLine[2]);
                db.insertOrThrow("Localisation", null, toInsert);
            }
            i++;
        }
    }

    private void readStagiaire(CSVReader reader, SQLiteDatabase db) throws Exception {
        String fichier = "stagiaire.csv";
        String[] nextLine;
        int i = 1;

        while((nextLine = reader.readNext()) != null) {
            if (nextLine.length != 8) // Si la taille du fichier est invalide
                throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

            ContentValues toInsert = new ContentValues();

            putIfNull(toInsert,"nom", nextLine[0]);
            putIfNull(toInsert,"prenom", nextLine[1]);
            putIfNull(toInsert,"login", nextLine[2]);
            putIfNull(toInsert,"promotion", nextLine[3]);
            putIfNull(toInsert,"mail", nextLine[4]);
            putIfNull(toInsert,"tel", nextLine[5]);

            db.insertOrThrow("Stagiaire", null, toInsert);

            if(!nextLine[6].isEmpty() && !nextLine[7].isEmpty()) {
                toInsert = new ContentValues();
                putIfNull(toInsert,"entreprise", nextLine[6]);
                putIfNull(toInsert,"poste", nextLine[7]);
                putIfNull(toInsert,"stagiaire", nextLine[2]);

                db.insertOrThrow("Emploi", null, toInsert);
            }
            i++;
        }
    }

    private void readStage(CSVReader reader, SQLiteDatabase db) throws Exception {
        String fichier = "stage.csv";
        String[] nextLine;
        int i = 1;

        while((nextLine = reader.readNext()) != null) {
            if(nextLine.length != 9) // Si la taille du fichier est invalide
                throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

            try {
                SimpleDateFormat test = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

                Date debut = new Date(test.parse(nextLine[3]).getTime());
                Date fin = new Date(test.parse(nextLine[4]).getTime());

                ContentValues toInsert = new ContentValues();
                putIfNull(toInsert,"sujet", nextLine[0]);
                putIfNull(toInsert,"mots_cles", nextLine[1]);
                putIfNull(toInsert,"lien_rapport", nextLine[2]);
                putIfNull(toInsert,"date_debut", debut.toString());
                putIfNull(toInsert,"date_fin", fin.toString());
                putIfNull(toInsert,"nom_maitre_stage", nextLine[5]);
                putIfNull(toInsert,"nom_tuteur_stage", nextLine[6]);
                putIfNull(toInsert,"stagiaire", nextLine[7]);
                putIfNull(toInsert,"entreprise", nextLine[8]);

                db.insertOrThrow("Stage", null, toInsert);
            } catch(ParseException pe) {
                throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "Fichier stage.csv ligne " + i + " : Date invalide (jj/MM/aaaa)");
            }
            i++;
        }
    }

    private void readContact(CSVReader reader, SQLiteDatabase db) throws Exception {
        String fichier = "contact.csv";
        String[] nextLine;
        int i = 1;

        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length != 7) // Si la taille du fichier est invalide
                throw new InvalidCSVException(InvalidCSVException.Cause.LONGUEUR_INVALIDE, "Fichier " + fichier + " ligne " + i);

            ContentValues toInsert = new ContentValues();
            int civ;
            if (nextLine[0].toLowerCase().equals("monsieur")) civ = 0;
            else if (nextLine[0].toLowerCase().equals("madame")) civ = 1;
            else throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "Ligne " + i + ", civilité invalide");

            toInsert.put("civilite", civ);
            putIfNull(toInsert,"nom", nextLine[1]);
            putIfNull(toInsert,"prenom", nextLine[2]);
            putIfNull(toInsert,"entreprise", nextLine[3]);
            putIfNull(toInsert,"telephone", nextLine[4]);
            putIfNull(toInsert,"mail", nextLine[5]);
            putIfNull(toInsert,"poste", nextLine[6]);

            db.insertOrThrow("Contact", null, toInsert);

            i++;
        }
    }

    public void update(CSVReader entrepriseReader, CSVReader stagiaireReader, CSVReader stageReader, CSVReader contactReader, boolean getLatLng, Context context) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            this.reinit(db);
            this.onCreate(db);
            // On initialise les entreprises
            this.readEntreprise(entrepriseReader, db, getLatLng, context);
            this.readStagiaire(stagiaireReader, db);
            this.readStage(stageReader, db);
            this.readContact(contactReader, db);
            db.setTransactionSuccessful();
        }catch(SQLiteException sqe) {
            throw new InvalidCSVException(InvalidCSVException.Cause.REFERENCE_INVALIDE, sqe.getLocalizedMessage());
        }catch (IOException ioe){
            throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "");
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateLocal(Context context) throws Exception {
        CSVReader contactReader, entrepriseReader, stageReader, stagiaireReader;

        contactReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.contact)), ',', '"', 1);
        entrepriseReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.entreprise)), ',', '"', 1);
        stageReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.stage)), ',', '"', 1);
        stagiaireReader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.stagiaire)), ',', '"', 1);

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            this.reinit(db);
            this.onCreate(db);
            // On initialise les entreprises
            this.readEntreprise(entrepriseReader, db, false, context);
            this.readStagiaire(stagiaireReader, db);
            this.readStage(stageReader, db);
            this.readContact(contactReader, db);
            db.setTransactionSuccessful();
        }catch(SQLiteException sqe) {
            throw new InvalidCSVException(InvalidCSVException.Cause.REFERENCE_INVALIDE, sqe.getLocalizedMessage());
        }catch (IOException ioe){
            throw new InvalidCSVException(InvalidCSVException.Cause.VALEUR_INVALIDE, "");
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    private void putIfNull(ContentValues cv, String champ, String valeur) {
        if(valeur.isEmpty())
            cv.putNull(champ);
        else
            cv.put(champ, valeur);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
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
                "FOREIGN KEY (entreprise) REFERENCES Entreprise(abbr))");

        db.execSQL("CREATE TABLE Contact(" +
                "civilite INTEGER," +
                "nom TEXT," +
                "prenom TEXT NULL," +
                "entreprise TEXT," +
                "telephone TEXT NULL," +
                "mail TEXT NULL," +
                "poste TEXT," +
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

}
