package ovh.dessert.tpe.repertoiredestagesm2.entities;

import android.database.Cursor;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
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

    public Contact(Cursor results) {
        this(results.getInt(0), results.getString(1), (results.isNull(2) ? null : results.getString(2)), results.getString(3), (results.isNull(4) ? null : results.getString(4)), (results.isNull(5) ? null : results.getString(5)), results.getString(6));
    }

    public Contact(int civilite, String nom, String prenom, String entreprise, String telephone, String mail, String poste) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.entreprise = entreprise;
        this.telephone = telephone;
        this.mail = mail;
        this.poste = poste;
    }

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
