package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Classe contenant les informations d'un emploi
 * Created by Yohann Bacha on 02/05/16.
 */
public class Emploi {
    private String entreprise;
    private String stagiaire;
    private String poste;

    /**
     * Constructeur à partir des valeurs d'un objet Cursor
     * @param results L'objet Cursor à faire passer au constructeur
     */
    public Emploi(Cursor results) {
        this(results.getString(0), results.getString(1), results.getString(2));
    }

    /**
     * Constructeur d'un emploi
     * @param entreprise L'abbréviation de l'entreprise relatée
     * @param stagiaire Le login du stagiaire concerné
     * @param poste Le poste occupé
     */
    public Emploi(String entreprise, String stagiaire, String poste) {
        this.entreprise = entreprise;
        this.stagiaire = stagiaire;
        this.poste = poste;
    }

    /**
     * Retourne l'entreprise concernée
     * @return L'objet Entreprise concernée
     * @throws Exception Si erreur SQL il y a
     */
    public Entreprise getEntreprise() throws Exception {
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    /**
     * Retourne le stagiaire concerné
     * @return L'objet Stagiaire concerné
     * @throws Exception Si erreur SQL il y a
     */
    public Stagiaire getStagiaire() throws Exception {
        return StagesDAO.getInstance(null).getStagiaire(stagiaire);
    }

    public String getPoste() {
        return poste;
    }
}
