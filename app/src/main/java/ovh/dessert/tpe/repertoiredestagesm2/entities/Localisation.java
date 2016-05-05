package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Localisation {
    private double latitude; // Peut être nul
    private double longitude; // Peut être nul
    private String adresse; // Peut être nul
    private String entreprise;

    public Localisation(Cursor cursor) {
        this((cursor.isNull(0) ? null : cursor.getDouble(0)), (cursor.isNull(1) ? null : cursor.getDouble(1)), (cursor.isNull(2) ? null : cursor.getString(2)), cursor.getString(3));
    }

    public Localisation(double latitude, double longitude, String adresse, String entreprise) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.entreprise = entreprise;
    }

    public double getLatitude() {
        return latitude;
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
