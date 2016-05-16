package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        Intent intent = new Intent(MainMenuActivity.this, SearchMap.class);
        intent.putExtra("<Distance>", "10 km");
        intent.putExtra("<City>", "Le Havre, France");
        startActivity(intent);
    }

    public void openSettings(View v) {
        Intent intent = new Intent(MainMenuActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}
