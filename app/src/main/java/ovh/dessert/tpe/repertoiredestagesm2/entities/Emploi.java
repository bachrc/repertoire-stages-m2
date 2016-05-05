package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Emploi {
    private String entreprise;
    private String stagiaire;
    private String poste;

    public Emploi(Cursor results) {
        this(results.getString(0), results.getString(1), results.getString(2));
    }

    public Emploi(String entreprise, String stagiaire, String poste) {
        this.entreprise = entreprise;
        this.stagiaire = stagiaire;
        this.poste = poste;
    }

    public Entreprise getEntreprise() throws Exception {
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public Stagiaire getStagiaire() throws Exception {
        return StagesDAO.getInstance(null).getStagiaire(stagiaire);
    }

    public String getPoste() {
        return poste;
    }
}
