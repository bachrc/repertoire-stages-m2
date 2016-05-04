package ovh.dessert.tpe.repertoiredestagesm2.entities;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Contact {

    private int civilite; // 0 : Monsieur, 1 : Madame
    private String nom;
    private String prenom;
    private String entreprise;
    private String telephone;

    public Contact(int civilite, String nom, String prenom, String entreprise, String telephone) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.entreprise = entreprise;
        this.telephone = telephone;
    }

    public Entreprise getEntreprise() throws Exception{
        return StagesDAO.getInstance(null).getEntreprise(entreprise);
    }

    public int getCivilite() {
        return civilite;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }
}
