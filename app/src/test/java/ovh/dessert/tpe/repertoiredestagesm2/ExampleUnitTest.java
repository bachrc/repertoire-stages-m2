package ovh.dessert.tpe.repertoiredestagesm2;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;

import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void testDB() {
        try {
            SimpleDateFormat test = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String paramDateAsString = "25/12/2007";

            Date myDate = test.parse(paramDateAsString);
            System.out.println(myDate.toString());
            assertTrue(true);
        }catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}