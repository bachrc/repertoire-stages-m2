package ovh.dessert.tpe.repertoiredestagesm2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EntreListeActivity extends AppCompatActivity {

    public static View view;
    private String city, distance;
    protected String data;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this.getBaseContext());
        view = inflater.inflate(R.layout.activity_list, null, false);
        city = getIntent().getStringExtra("<City>");
        distance = getIntent().getStringExtra("<Distance>").split("[a-z ]")[0];

        TableLayout tl = (TableLayout) view.findViewById(R.id.listlayout);
       Log.d("RTSICTRIST","RT");
        for(int i=0;i<10;i++) {
            createBox(tl);
           Log.d("GODDAMMIT","PUTAIN ");
        }
        setContentView(R.layout.activity_list);
    }

    protected void createBox(TableLayout tl){
        TableRow tr = new TableRow(this.getBaseContext());
        tr.setGravity(Gravity.CENTER_VERTICAL);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.toString();
            }
        });

        LinearLayout ll = new LinearLayout(this.getBaseContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView te = new TextView(this.getBaseContext());
        te.setText("DESSERT (4)");
        Log.d("dessert", "4");

        TextView tm = new TextView(this.getBaseContext());
        tm.setText("Franck Boss");

        ll.addView(te);
        ll.addView(tm);
        tr.addView(ll);

        TextView td = new TextView(this.getBaseContext());
        td.setGravity(Gravity.RIGHT);
        td.setText("10 km");
        td.setTextSize(25);

        tr.addView(td);
        tl.addView(tr);


    }
}
