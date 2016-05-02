package ovh.dessert.tpe.repertoiredestagesm2;

/**
 * Created by totorolepacha on 02/05/16.
 */
public class StagesDAO {

    private static StagesDAO db = null;

    private StagesDAO() {
        
    }

    private void setupDatabase() {

    }

    public static StagesDAO getInstance() {
        if (db == null)
            db = new StagesDAO();

        return db;
    }
}
