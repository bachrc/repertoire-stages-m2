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

    /**
     * Ecouteur sur un item de liste Localisation
     */
    public interface LocalisationAdapterListener {
        public void onClickLocalisation(Localisation item, int position);
    }

    // Liste de localications
    private List<Localisation> localisations;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    // Liste d'écouteurs de localisations
    private List<LocalisationAdapterListener> mListener;


    /**
     * Crée un adapter, pour afficher une liste de localisations.
     * @param context Le contexte associé à l'utilisation de l'adapter
     * @param localisations La liste de localisations
     */
    public LocalisationAdapter(Context context, List<Localisation> localisations) {
        this.mContext = context;
        this.localisations = localisations;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    /**
     * Renvoie le nombre de localisations
     * @return Nombre de localisations présents
     */
    @Override
    public int getCount() {
        return this.localisations.size();
    }

    /**
     * Renvoie une localisation à une position donnée
     * @param position Une position particulière
     * @return La localisation qui se trouve à cette position
     */
    @Override
    public Object getItem(int position) {
        return this.localisations.get(position);
    }

    /**
     * Renvoie un ID associé à la liste de localisations
     * @param position Une position
     * @return L'ID de l'objet à cette position
     */
    @Override
    public long getItemId(int position) {
        return position;
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
        LinearLayout item;
        TextView adresse;
        if(convertView == null){
            item = (LinearLayout) mInflater.inflate(R.layout.localisation_list_item, parent, false);
        }else{

            item = (LinearLayout) convertView;
        }

        // On affiche l'adresse et prépare les taps sur la carte.
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

    /**
     * Ajoute un écouteur de localisation
     * @param localisationAdapterListener L'écouteur de localisation
     */
    public void addListener(LocalisationAdapterListener localisationAdapterListener){
        mListener.add(localisationAdapterListener);
    }

    public void sendListener(Localisation item, int position){
        for(int i = mListener.size()-1; i>=0; i--){
            mListener.get(i).onClickLocalisation(item, position);
        }
    }
}
