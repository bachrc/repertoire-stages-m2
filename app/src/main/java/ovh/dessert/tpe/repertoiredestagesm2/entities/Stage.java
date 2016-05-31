package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import java.sql.Date;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Stage {
    private String sujet;
    private String mots_cles; // Peut être null
    private String lienRapport; // Peut être null
    private String stagiaire;
    private String entreprise;
    private Date dateDebut;
    private Date dateFin;
    private String nomTuteur; // Peut être null
    private String nomMaitre; // Peut être null

    public Stage(Cursor results) {
        this(results.getString(0), (results.isNull(1) ? null : results.getString(1)), (results.isNull(2) ? null : results.getString(2)), results.getString(3), results.getString(4), Date.valueOf(results.getString(5)), Date.valueOf(results.getString(6)), (results.isNull(7) ? null : results.getString(7)), (results.isNull(8) ? null : results.getString(8)));
    }

    public Stage(String sujet, String mots_cles, String lienRapport, String stagiaire, String entreprise, Date dateDebut, Date dateFin, String nomMaitre, String nomTuteur) {
        this.sujet = sujet;
        this.mots_cles = mots_cles;
        this.lienRapport = lienRapport;
        this.stagiaire = stagiaire;
        this.entreprise = entreprise;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nomTuteur = nomTuteur;
        this.nomMaitre = nomMaitre;
    }

    public String getNomMaitre() {
        return nomMaitre;
    }

    public String getSujet() {
        return sujet;
    }

    public String getMots_cles() {
        return mots_cles;
    }

    public String getLienRapport() {
        return lienRapport;
    }

    public Stagiaire getStagiaire() throws Exception{
        return StagesDAO.getInstance(null).getStagiaire(stagiaire);
    }

    public Entreprise getEntreprise() throws Exception{
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getNomTuteur() {
        return nomTuteur;
    }
}
