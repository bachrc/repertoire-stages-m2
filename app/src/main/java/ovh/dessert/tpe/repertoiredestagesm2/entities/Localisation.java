package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Localisation implements Parcelable {
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

    public Entreprise getEntreprise() throws Exception {
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public String getAbbr() {
        return entreprise;
    }

    // Attributs parcelable
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
