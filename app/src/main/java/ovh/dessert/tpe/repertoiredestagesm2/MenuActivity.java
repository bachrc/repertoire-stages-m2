package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Restons sérieux, ok
    }

    public void goToDebug(View v) {
        Intent intent = new Intent(MenuActivity.this, DebuggingActivity.class);
        startActivity(intent);
    }
}
