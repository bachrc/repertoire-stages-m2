package ovh.dessert.tpe.repertoiredestagesm2.exceptions;

/**
 * Created by totorolepacha on 07/05/16.
 */
public class InvalidCSVException extends Exception {
    public enum Cause {
        LONGUEUR_INVALIDE("Longueur de la ligne invalide"),
        REFERENCE_INVALIDE("Référence à l'objet inexistante"),
        VALEUR_INVALIDE("La valeur renseignée n'est pas valide");

        private String message;

        Cause(String message) {
            this.message = message;
        }

        public String toString() {
            return this.message;
        }
    }

    private Cause cause;
    private String message;

    public InvalidCSVException(Cause cause, String message) {
        this.cause = cause;
        this.message = message;
    }

    public Cause getCSVCause() {
        return this.cause;
    }

    public String toString() {
        return this.cause + " : " + this.message;
    }

}
