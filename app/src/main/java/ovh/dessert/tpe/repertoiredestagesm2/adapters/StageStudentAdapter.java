package ovh.dessert.tpe.repertoiredestagesm2.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;

/**
 * Created by Unmei Muma on 30/05/2016.
 */
public class StageStudentAdapter extends StageAdapter {

    public StageStudentAdapter(Context context, List<Stage> stages) {
        super(context, stages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item;
        TextView sujet;
        String  sujetS;
        if (convertView == null) {
            item = (LinearLayout) mInflater.inflate(R.layout.stagestudent_list_item, parent, false);
        } else {
            item = (LinearLayout) convertView;
        }

        sujet = (TextView) item.findViewById(R.id.sujet);
        sujetS = stages.get(position).getSujet();

        sujet.setText(sujetS);

        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(stages.get(position), position);
            }
        });

        return item;
    }
}