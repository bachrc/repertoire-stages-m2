package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openAdvSearch(View v){
        Intent intent = new Intent(MainMenuActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void openList(View v){
        Intent intent = new Intent(MainMenuActivity.this, EntreListeActivity.class);
        startActivity(intent);
    }

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

    public void openSettings(View v) {
        Intent intent = new Intent(MainMenuActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}
