package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Restons sérieux, ok
        String[] distances = {
                "5 km",
                "10 km",
                "25 km",
                "50 km",
                "100 km",
                "250 km"
        };

        Spinner sp = (Spinner) findViewById(R.id.spinner);
        Button et = (Button) findViewById(R.id.send);

        sp.setOnItemSelectedListener(this);

        List<String> str = new ArrayList<>();
        for (String s : distances) {
            str.add(s);
        }
        et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner sp = (Spinner) findViewById(R.id.spinner);
                EditText city = (EditText) findViewById(R.id.town);
                Intent intent = new Intent(MenuActivity.this, EntreListeActivity.class);
                intent.putExtra("<Distance>", (String) sp.getItemAtPosition(sp.getSelectedItemPosition()));
                intent.putExtra("<City>", city.getText().toString());
                startActivity(intent);

            }
        });

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.spinner_item, str);
        aa.setDropDownViewResource(R.layout.spinner_item);
        sp.setAdapter(aa);
    }

    public void goToDebug(View v) {
        Intent intent = new Intent(MenuActivity.this, DebuggingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
