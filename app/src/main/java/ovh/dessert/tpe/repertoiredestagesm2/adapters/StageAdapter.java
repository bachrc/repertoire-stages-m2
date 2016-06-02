package ovh.dessert.tpe.repertoiredestagesm2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;

/**
 * Created by Unmei Muma on 20/05/2016.
 */
public class StageAdapter extends BaseAdapter {

    public interface StageAdapterListener {
        public void onClickStage(Stage item, int position);
    }

    //
    protected List<Stage> stages;

    //Le contexte dans lequel est présent notre adapter
    protected Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    protected LayoutInflater mInflater;

    //
    protected List<StageAdapterListener> mListener;

    public StageAdapter(Context context, List<Stage> stages) {
        this.mContext = context;
        this.stages = stages;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return stages.size();
    }

    @Override
    public Object getItem(int position) {
        return stages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item;
        TextView nom, sujet;
        String nomS, sujetS;
        if(convertView == null){
            item = (LinearLayout) mInflater.inflate(R.layout.stage_list_item, parent, false);
        }else{
            item = (LinearLayout) convertView;
        }

        nom = (TextView) item.findViewById(R.id.nom_stagiaire);

        try {
            nomS = stages.get(position).getStagiaire().toString();
        } catch (Exception e) {
            nomS = "Aucun nom";
        }
        sujet = (TextView) item.findViewById(R.id.sujet);
        sujetS = stages.get(position).getSujet();

        nom.setText(nomS);
        sujet.setText(sujetS);

        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(stages.get(position), position);
            }
        });

        return item;
    }

    public void addListener(StageAdapterListener stageAdapterListener){
        mListener.add(stageAdapterListener);
    }

    public void sendListener(Stage item, int position){
        for(int i = mListener.size()-1; i>=0; i--){
            mListener.get(i).onClickStage(item, position);
        }
    }
}
