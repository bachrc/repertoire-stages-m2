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
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

/**
 * Created by Unmei Muma on 16/05/2016.
*/
public class LocalisationAdapter extends BaseAdapter {

    public interface LocalisationAdapterListener {
        public void onClickLocalisation(Localisation item, int position);
    }

    private List<Localisation> localisations;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    private List<LocalisationAdapterListener> mListener;

    public LocalisationAdapter(Context context, List<Localisation> localisations) {
        this.mContext = context;
        this.localisations = localisations;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    /*public LocalisationAdapter(Context context) {
        try {
            this.localisations = StagesDAO.getInstance(context).getAllLocalisations();
        }catch(Exception e) {
            this.localisations = new ArrayList<>();
        }finally {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
        }
    }*/
    @Override
    public int getCount() {
        return this.localisations.size();
    }

    @Override
    public Object getItem(int position) {
        return this.localisations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item;
        TextView adresse;
        if(convertView == null){
            item = (LinearLayout) mInflater.inflate(R.layout.localisation_list_item, parent, false);
        }else{

            item = (LinearLayout) convertView;
        }

        adresse = (TextView) item.findViewById(R.id.localisation);
        String adr = localisations.get(position).getAdresse();
        adresse.setText(adr);

        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(localisations.get(position), position);
            }
        });

        return item;
    }

    public void addListener(LocalisationAdapterListener localisationAdapterListener){
        mListener.add(localisationAdapterListener);
    }

    public void sendListener(Localisation item, int position){
        for(int i = mListener.size()-1; i>=0; i--){
            mListener.get(i).onClickLocalisation(item, position);
        }
    }
}
