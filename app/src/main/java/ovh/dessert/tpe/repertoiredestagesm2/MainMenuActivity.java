package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    /**
     * Crée le menu principal.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Lance le menu de recherche.
     * @param v
     */
    public void openAdvSearch(View v){
        Intent intent = new Intent(MainMenuActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    /**
     * Ouvre la liste entière des entreprises.
     * @param v
     */
    public void openList(View v){
        Intent intent = new Intent(MainMenuActivity.this, FullListActivity.class);
        startActivity(intent);
    }

    /**
     * Ouvre la carte, centrée sur Le Havre.
     * @param v
     */
    public void openMap(View v){
        try {
            Intent intent = new Intent(MainMenuActivity.this, SearchMap.class);
            intent.putExtra("<City>", "Le Havre, France");

            intent.putParcelableArrayListExtra("<Localisations>", (ArrayList<? extends Parcelable>) StagesDAO.getInstance(MainMenuActivity.this).getAllLocalisations());
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(MainMenuActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Ouvre les options.
     * @param v
     */
    public void openSettings(View v) {
        Intent intent = new Intent(MainMenuActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}
