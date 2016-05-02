package ovh.dessert.tpe.repertoiredestagesm2.entities;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class Stagiaire {
    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String promotion;
    private String mail;
    private String tel;

    public Stagiaire(int id, String nom, String prenom, String login, String promotion, String mail, String tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.promotion = promotion;
        this.mail = mail;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }
}
