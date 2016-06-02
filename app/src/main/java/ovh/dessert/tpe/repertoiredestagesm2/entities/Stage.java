package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import java.sql.Date;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Classe représentant un stage
 * Created by Yohann Bacha on 02/05/16.
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

    /**
     * Constructeur à partir des valeurs d'un objet Cursor
     * @param results L'objet Cursor à faire passer au constructeur
     */
    public Stage(Cursor results) {
        this(results.getString(0), (results.isNull(1) ? null : results.getString(1)), (results.isNull(2) ? null : results.getString(2)), results.getString(3), results.getString(4), Date.valueOf(results.getString(5)), Date.valueOf(results.getString(6)), (results.isNull(7) ? null : results.getString(7)), (results.isNull(8) ? null : results.getString(8)));
    }

    /**
     * Constructeur d'un objet Stage
     * @param sujet Le sujet du stage
     * @param mots_cles Les mots-clés d'un stage (Peuvent être nuls)
     * @param lienRapport Le lien pour télécharger le rapport de stage (Peut être nul)
     * @param stagiaire Le login du stagiaire concerné
     * @param entreprise L'abbréviation de l'entreprise concernée
     * @param dateDebut La date de début du stage
     * @param dateFin La date de fin du stage
     * @param nomTuteur Le nom du Tuteur de stage (Peut être nul)
     * @param nomMaitre Le nom du Maitre de stage (Peut être nul
     */
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

    /**
     * Retourne le Stagiaire concerné
     * @return L'objet Stagiaire du stagiaire concerné par le stage
     * @throws Exception Si erreur SQL il y a
     */
    public Stagiaire getStagiaire() throws Exception{
        return StagesDAO.getInstance(null).getStagiaire(stagiaire);
    }

    /**
     * Retourne l'Entreprise concernée
     * @return L'objet Entreprise de l'entreprise concernée par le stage
     * @throws Exception Si erreur SqL il y a
     */
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
