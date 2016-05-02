package ovh.dessert.tpe.repertoiredestagesm2.entities;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Emploi {
    private int id;
    private String entreprise;
    private String stagiaire;
    private String poste;

    public Emploi(int id, String entreprise, String stagiaire, String poste) {
        this.id = id;
        this.entreprise = entreprise;
        this.stagiaire = stagiaire;
        this.poste = poste;
    }

    public int getId() {
        return id;
    }

    public Entreprise getEntreprise() {
        // TODO
        return null;
    }

    public Stagiaire getStagiaire() {
        // TODO
        return null;
    }

    public String getPoste() {
        return poste;
    }
}
