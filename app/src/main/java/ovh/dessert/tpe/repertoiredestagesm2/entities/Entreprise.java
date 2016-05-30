package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Entreprise implements Parcelable {
    private String nom;
    private String siteweb; // Peut être nul
    private String abbr;

    public Entreprise(Cursor results) {
        this(results.getString(0), (results.isNull(1) ? null : results.getString(1)), results.getString(2));
    }

    public Entreprise(String nom, String siteweb, String abbr) {
        this.nom = nom;
        this.siteweb = siteweb;
        this.abbr = abbr;
    }

    public List<Contact> getContacts() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Contact WHERE entreprise = ?", new String[]{this.abbr});
        List<Contact> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Contact temp = new Contact(results);
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public List<Localisation> getLocalisations() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Localisation WHERE entreprise = ?", new String[]{this.abbr});
        List<Localisation> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Localisation temp = new Localisation(results);
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public List<Stage> getStages() throws Exception {
        SQLiteDatabase db = StagesDAO.getInstance(null).getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM Stage WHERE entreprise = ?", new String[]{this.abbr});
        List<Stage> retour = new ArrayList<>();
        try {
            if (results.moveToFirst()) {
                do {
                    Stage temp = new Stage(results);
                    retour.add(temp);
                }while(results.moveToNext());
            }
        } catch(Exception e) {
            throw new Exception("Erreur lors de l'éxecution de la requête.");
        } finally {
            if (results != null && !results.isClosed()) {
                results.close();
            }
        }

        return retour;
    }

    public String getNom() {
        return nom;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public String getAbbr() {
        return abbr;
    }

    // Attributs parcelable
    public static final Parcelable.Creator<Entreprise> CREATOR =
            new Parcelable.Creator<Entreprise>() {

                @Override
                public Entreprise createFromParcel(Parcel source) {
                    return new Entreprise(source);
                }

                @Override
                public Entreprise[] newArray(int size) {
                    return new Entreprise[size];
                }

            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeString(this.siteweb);
        dest.writeString(this.abbr);
    }

    public Entreprise(Parcel parcel) {
        this.nom = parcel.readString();
        this.siteweb = parcel.readString();
        this.abbr = parcel.readString();
    }

    public double getClosestDistanceToPoint(LatLng centre){
        List<Localisation> localisations;
        double lat, lng, lowest = Double.MAX_VALUE, distance;
        try {
            localisations = this.getLocalisations();
        }catch(Exception e){
            localisations = null;
        }

        if (localisations != null && localisations.size() > 0){
            lat = centre.latitude;
            lng = centre.longitude;
            for(Localisation l: localisations){
                distance = Localisation.distance(lat, l.getLatitude(), lng, l.getLongitude());
                if(distance < lowest)
                    lowest = distance;
            }

            return lowest;
        }else{
            return 0d;
        }

    }

}
