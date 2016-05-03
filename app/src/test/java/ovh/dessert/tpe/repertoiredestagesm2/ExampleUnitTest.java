package ovh.dessert.tpe.repertoiredestagesm2;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void initDB() {
        try {
            StagesDAO test = new StagesDAO(null);
            SQLiteDatabase db = test.getWritableDatabase();
            assertTrue(true);
        }catch(SQLException e) {
            assertTrue(false);
        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}