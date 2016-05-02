package ovh.dessert.tpe.repertoiredestagesm2.entities;


import java.sql.Date;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Stage {
    private int id;
    private String sujet;
    private String lienRapport;
    private String stagiaire;
    private String entreprise;
    private Date dateDebut;
    private Date dateFin;
    private String nomTuteur;
    private String nomMaitre;

    public Stage(int id, String sujet, String lienRapport, String stagiaire, String entreprise, Date dateDebut, Date dateFin, String nomTuteur, String nomMaitre) {
        this.id = id;
        this.sujet = sujet;
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

    public String getLienRapport() {
        return lienRapport;
    }

    public Stagiaire getStagiaire() {
        // TODO
        return null;
    }

    public Entreprise getEntreprise() {
        // TODO
        return null;
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
