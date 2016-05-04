package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import java.sql.Date;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Stage {
    private String sujet;
    private String mots_cles;
    private String lienRapport;
    private String stagiaire;
    private String entreprise;
    private Date dateDebut;
    private Date dateFin;
    private String nomTuteur;
    private String nomMaitre;

    public Stage(String sujet, String mots_cles, String lienRapport, String stagiaire, String entreprise, Date dateDebut, Date dateFin, String nomTuteur, String nomMaitre) {
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
