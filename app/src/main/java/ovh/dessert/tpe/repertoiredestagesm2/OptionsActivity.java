package ovh.dessert.tpe.repertoiredestagesm2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.net.URL;

public class OptionsActivity extends AppCompatActivity {

    String[] list = new String[]{"Mise à jour/Connexion internet requise", "A propos/A propos de l'application"};

    private String urlStagiaire = "http://dessert.ovh/tpe/stagiaire.csv";
    private String urlStage = "http://dessert.ovh/tpe/stage.csv";
    private String urlEntreprise = "http://dessert.ovh/tpe/entreprise.csv";
    private String urlContact = "http://dessert.ovh/tpe/contact.csv";

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(OptionsActivity.this);
        mProgressDialog.setMessage("Mise à jour..");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

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
                        aPropos();
                        break;
                }
            }
        });
        tl.setAdapter(adapter);
    }

    private void updateDatabase() {
        final UpdateTask update = new UpdateTask(OptionsActivity.this);
        update.execute();

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                update.cancel(true);
            }
        });
    }

    private void aPropos() {
        new AlertDialog.Builder(OptionsActivity.this).setTitle(R.string.aboutTitle).setMessage(Html.fromHtml("<big><b>" + getString(R.string.app_name) + "</b></big>") + "\n\n" + getString(R.string.about) + "\n\n" + getString(R.string.versionInt) + " " + getString(R.string.version)).setPositiveButton(android.R.string.ok, null).setIcon(android.R.drawable.ic_dialog_info).show();
    }



    public class UpdateTask extends AsyncTask<Void, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public UpdateTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... oui) {
            try {
                CSVReader contactReader = new CSVReader(new InputStreamReader(new URL(urlContact).openStream()), ',', '"', 1);
                publishProgress(20);
                CSVReader entrepriseReader = new CSVReader(new InputStreamReader(new URL(urlEntreprise).openStream()), ',', '"', 1);
                publishProgress(40);
                CSVReader stageReader = new CSVReader(new InputStreamReader(new URL(urlStage).openStream()), ',', '"', 1);
                publishProgress(60);
                CSVReader stagiaireReader = new CSVReader(new InputStreamReader(new URL(urlStagiaire).openStream()), ',', '"', 1);
                publishProgress(80);
                StagesDAO.getInstance(this.context).update(entrepriseReader, stagiaireReader, stageReader, contactReader, true, context);
                publishProgress(100);
            } catch (Exception e) {
                return e.toString();
            } finally {
                publishProgress(0);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Erreur : " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,getString(R.string.update_finished), Toast.LENGTH_SHORT).show();
        }

    }
}
