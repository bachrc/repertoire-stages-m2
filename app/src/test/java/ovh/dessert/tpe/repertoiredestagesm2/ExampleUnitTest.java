package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;

import com.opencsv.CSVReader;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ovh.dessert.tpe.repertoiredestagesm2.exceptions.InvalidCSVException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void testDB() {
        try {
            SimpleDateFormat test = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String paramDateAsString = "25/12/2007";

            Date myDate = test.parse(paramDateAsString);
            System.out.println(myDate.toString());
            assertTrue(true);
        }catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testCSV() throws Exception {
        CSVReader contactReader, entrepriseReader, stageReader, stagiaireReader;
        contactReader = new CSVReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("res/raw/contact.csv")));
        entrepriseReader = new CSVReader(new InputStreamReader(new FileInputStream("res/raw/entreprise.csv")));
        stageReader = new CSVReader(new InputStreamReader(new FileInputStream("res/raw/stage.csv")));
        stagiaireReader = new CSVReader(new InputStreamReader(new FileInputStream("res/raw/stagiaire.csv")));

        // TODO: 08/05/16 Faire des toInsert.putNull si la String est vide

        int i = 1;
        String fichier = "";
        try {
            i = 1;
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
                System.out.println(toInsert);

                for (int j = 3; j < nextLine.length; j+=4) {
                    if(nextLine[j].isEmpty()) continue; // S'il n'y a pas de nom, on skip
                    toInsert = new ContentValues();
                    toInsert.put("nom", nextLine[j]);
                    // TODO: 08/05/16 Recupérer les LatLng à partir d'une adresse
                    toInsert.put("latitude", nextLine[j+1]);
                    toInsert.put("longitude", nextLine[j+2]);
                    toInsert.put("adresse", nextLine[j+3]);
                    toInsert.put("entreprise", nextLine[2]);
                    System.out.println(toInsert);
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

                System.out.println(toInsert);

                if(!nextLine[6].isEmpty() && !nextLine[7].isEmpty()) {
                    toInsert = new ContentValues();
                    toInsert.put("entreprise", nextLine[6]);
                    toInsert.put("poste", nextLine[7]);
                    toInsert.put("stagiaire", nextLine[2]);

                    System.out.println(toInsert);
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

                    java.sql.Date debut = new java.sql.Date(test.parse(nextLine[3]).getTime());
                    java.sql.Date fin = new java.sql.Date(test.parse(nextLine[4]).getTime());

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

                    System.out.println(toInsert);
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

                System.out.println(toInsert);

                i++;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}