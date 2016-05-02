package ovh.dessert.tpe.repertoiredestagesm2.entities;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Contact {

    private int id;
    private int civilite; // 0 : Monsieur, 1 : Madame
    private String nom;
    private String prenom;
    private String entreprise;

    public Contact(int id, int civilite, String nom, String prenom, String entreprise) {
        this.id = id;
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.entreprise = entreprise;
    }

    public Entreprise getEntreprise() {
        //TODO
        return null;
    }

    public int getId() {
        return id;
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
}
