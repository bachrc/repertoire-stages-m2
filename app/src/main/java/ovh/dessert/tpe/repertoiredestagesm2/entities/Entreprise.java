package ovh.dessert.tpe.repertoiredestagesm2.entities;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Entreprise {
    private int id;
    private String nom;
    private String siteweb;
    private String abbr;

    public Entreprise(int id, String nom, String siteweb, String abbr) {
        this.id = id;
        this.nom = nom;
        this.siteweb = siteweb;
        this.abbr = abbr;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public String getAbbr() {
        return abbr;
    }
}
