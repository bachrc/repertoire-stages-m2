package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Classe contenant les informations d'un Contact
 * Created by totorolepacha on 02/05/16.
 */
public class Contact {

    private int civilite; // 0 : Monsieur, 1 : Madame
    private String nom;
    private String prenom; // Peut être nul
    private String entreprise;
    private String telephone; // Peut être nul
    private String mail; // Peut être nul
    private String poste;

    /**
     * Constructeur à partir des valeurs d'un objet Cursor
     * @param results L'objet Cursor à faire passer au constructeur
     */
    public Contact(Cursor results) {
        this(results.getInt(0), results.getString(1), (results.isNull(2) ? null : results.getString(2)), results.getString(3), (results.isNull(4) ? null : results.getString(4)), (results.isNull(5) ? null : results.getString(5)), results.getString(6));
    }

    /**
     * Constructeur d'un objet Contact
     * @param civilite Civilité du contact (0: Homme /1: Femme)
     * @param nom Le nom du contact
     * @param prenom Le prénom du contact (Peut être nul)
     * @param entreprise L'abbréviation de l'entreprise rattachée au contact
     * @param telephone Le numéro de téléphone du contact (Peut être nul)
     * @param mail L'adresse mail du contact (Peut être nul)
     * @param poste Le poste du contact dans l'entreprise
     */
    public Contact(int civilite, String nom, String prenom, String entreprise, String telephone, String mail, String poste) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.entreprise = entreprise;
        this.telephone = telephone;
        this.mail = mail;
        this.poste = poste;
    }

    /**
     * Retourne l'Entreprise à laquelle est rattaché le contact
     * @return L'entreprise en question
     * @throws Exception Si erreur SQL il y a.
     */
    public Entreprise getEntreprise() throws Exception{
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public int getCivilite() {
        return civilite;
    }

    public String toString() { return ((civilite == 0)? "M. " : "Mme ") + ((prenom != null)? prenom + " " : "") + nom.toUpperCase(); }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPoste() {
        return this.poste;
    }
}
