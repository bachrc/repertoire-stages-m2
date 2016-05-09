package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Localisation {
    private String nom;
    private double latitude; // Peut être nul (égal à 0)
    private double longitude; // Peut être nul (égal à 0)
    private String adresse; // Peut être nul
    private String entreprise;

    public Localisation(Cursor cursor) {
        this(cursor.getString(0), (cursor.isNull(1) ? 0 : cursor.getDouble(1)), (cursor.isNull(2) ? 0 : cursor.getDouble(2)), (cursor.isNull(3) ? null : cursor.getString(3)), cursor.getString(4));
    }

    public Localisation(String nom, double latitude, double longitude, String adresse, String entreprise) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.entreprise = entreprise;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getNom() {
        return this.nom;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public Entreprise getEntreprise() throws Exception {
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }
}
