package ovh.dessert.tpe.repertoiredestagesm2;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

public class OptionsActivity extends AppCompatActivity {

    String[] list = new String[]{"Mise à jour/Connexion internet requise", "A propos/A propos de l'application"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        ListView tl = (ListView) findViewById(R.id.options_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OptionsActivity.this, android.R.layout.simple_list_item_2, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View row;
                if(convertView == null){
                    LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(android.R.layout.simple_list_item_2, null);
                }else{
                    row = convertView;
                }
                TextView entete = (TextView) row.findViewById(android.R.id.text1);
                TextView soustitre = (TextView) row.findViewById(android.R.id.text2);

                String[] data = list[position].split("/", 2);
                entete.setText(data[0]);
                soustitre.setText(data[1]);

                entete.setTextColor(Color.BLACK);
                soustitre.setTextColor(Color.BLACK);

                return row;
            }
        };

        tl.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        updateDatabase();
                        break;
                    case 1:
                        new AlertDialog.Builder(OptionsActivity.this).setTitle(R.string.aboutTitle).setMessage(Html.fromHtml("<big><b>" + getString(R.string.app_name) + "</b></big>") + "\n\n" + getString(R.string.about) + "\n\n" + getString(R.string.versionInt) + " " + getString(R.string.version)).setPositiveButton(android.R.string.ok, null).setIcon(android.R.drawable.ic_dialog_info).show();
                        break;
                }
            }
        });
        tl.setAdapter(adapter);
    }

    private void updateDatabase() {

    }

    private void aPropos() {

    }
}
