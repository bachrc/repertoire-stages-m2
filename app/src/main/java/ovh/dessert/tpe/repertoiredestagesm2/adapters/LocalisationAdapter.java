package ovh.dessert.tpe.repertoiredestagesm2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

/**
 * Created by Unmei Muma on 16/05/2016.
 */
public class LocalisationAdapter extends BaseAdapter {

    private List<Localisation> localisations;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    public LocalisationAdapter(Context context, List<Localisation> localisations) {
        this.mContext = context;
        this.localisations = localisations;
        this.mInflater = LayoutInflater.from(mContext);
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
            Log.d("AUSECOURS", "A");
            item = (LinearLayout) mInflater.inflate(R.layout.localisation_list_item, parent, false);
        }else{

            item = (LinearLayout) convertView;
        }

        adresse = (TextView) item.findViewById(R.id.localisation);
        String adr = localisations.get(position).getAdresse();
        adresse.setText(adr);

        return item;
    }
}
