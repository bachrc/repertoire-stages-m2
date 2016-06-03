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

    /**
     * Crée un adapter pour une liste de stages.
     * @param context Le contexte associé à l'utilisation de l'adapter
     * @param stages La liste de stages
     */
    public StageStudentAdapter(Context context, List<Stage> stages) {
        super(context, stages);
    }

    /**
     * Affiche un item de liste.
     * @param position la position de l'item
     * @param convertView
     * @param parent
     * @return Un item de liste formaté selon un layout prédéfini.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item; // Conteneur de l'item
        TextView sujet; // Conteneur de l'intitulé
        String sujetS; // Intitulé sujet

        if (convertView == null) {
            item = (LinearLayout) mInflater.inflate(R.layout.stagestudent_list_item, parent, false);
        } else {
            item = (LinearLayout) convertView;
        }

        // On récupère un TextView associé pour le sujet, et y mettons l'intitulé à l'intérieur
        sujet = (TextView) item.findViewById(R.id.sujet);
        sujetS = stages.get(position).getSujet();
        sujet.setText(sujetS);

        // On prépare un écouteur
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