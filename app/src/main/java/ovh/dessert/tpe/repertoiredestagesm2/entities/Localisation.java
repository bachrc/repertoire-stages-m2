package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Classe représentant une localisation
 * Created by Yohann Bacha on 02/05/16.
 */
public class Localisation implements Parcelable {
    private String nom;
    private double latitude; // Peut être nul (égal à 0)
    private double longitude; // Peut être nul (égal à 0)
    private String adresse; // Peut être nul
    private String entreprise;

    /**
     * Constructeur à partir des valeurs d'un objet Cursor
     * @param cursor L'objet Cursor à faire passer au constructeur
     */
    public Localisation(Cursor cursor) {
        this(cursor.getString(0), (cursor.isNull(1) ? 0 : cursor.getDouble(1)), (cursor.isNull(2) ? 0 : cursor.getDouble(2)), (cursor.isNull(3) ? null : cursor.getString(3)), cursor.getString(4));
    }

    /**
     * Constructeur d'un objet Localisation
     * @param nom Le nom de la localisation
     * @param latitude La latitude du point. (Peut être égal à 0)
     * @param longitude La longitude du point. (Peut-être égal à 0)
     * @param adresse L'adresse du point. (Peut être nulle)
     * @param entreprise L'abbréviation de l'entreprise relatée
     */
    public Localisation(String nom, double latitude, double longitude, String adresse, String entreprise) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.entreprise = entreprise;
    }

    /**
     * Méthode statique calculant la distance entre deux points.
     * @param lat1 La latitude du point 1
     * @param lat2 La latitude du point 2
     * @param long1 La longitude du point 1
     * @param long2 La longitude du point 2
     * @return La distance en kilomètres entre les deux points.
     */
    public static double distance(double lat1, double lat2, double long1, double long2) {
        double rayon = 6371;
        if(lat1 == 0 && long1 == 0 || lat2 == 0 && long2 == 0) // Si un des couples est nul, il n'est pas défini, la distance est nulle.
            return 0;

        return Math.abs(Math.acos(Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(long1) - Math.toRadians(long2))) * rayon);
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

    /**
     * Retourne l'Entreprise liée à cette localisation
     * @return L'entreprise concernée
     * @throws Exception Si erreur SQL il y a
     */
    public Entreprise getEntreprise() throws Exception {
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public String getAbbr() {
        return entreprise;
    }

    /**
     * Attributs Parcelable, nécéssaires afin de faire passer une liste de localisations
     * d'une activité à une autre, en passant par les extras.
     */
    public static final Parcelable.Creator<Localisation> CREATOR =
        new Parcelable.Creator<Localisation>() {

            @Override
            public Localisation createFromParcel(Parcel source) {
                return new Localisation(source);
            }

            @Override
            public Localisation[] newArray(int size) {
                return new Localisation[size];
            }

        };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.adresse);
        dest.writeString(this.entreprise);
    }

    private Localisation(Parcel in) {
        this.nom = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.adresse = in.readString();
        this.entreprise = in.readString();
    }
}
