package ovh.dessert.tpe.repertoiredestagesm2.entities;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Localisation {
    private int id;
    private double latitude;
    private double longitude;
    private String adresse;
    private String entreprise;

    public Localisation(int id, double latitude, double longitude, String adresse, String entreprise) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.entreprise = entreprise;
    }

    public int getId() {
        return id;
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

    public Entreprise getEntreprise() {
        // TODO
        return null;
    }
}
