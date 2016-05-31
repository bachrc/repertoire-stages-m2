package ovh.dessert.tpe.repertoiredestagesm2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Contact;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;

/**
 * Created by totorolepacha on 10/05/16.
 */
public class EntrepriseAdapter extends BaseAdapter {

    public interface EntrepriseAdapterListener {
        public void onClickEntreprise(Entreprise item, int position);
    }

    private List<Entreprise> entreprises;

    private LatLng centre;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    private List<EntrepriseAdapterListener> mListener;

    public EntrepriseAdapter(Context context, List<Entreprise> entreprises, LatLng centre) {
        this.mContext = context;
        this.centre = centre;
        this.entreprises = entreprises;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    public EntrepriseAdapter(Context context, List<Entreprise> entreprises) {
        this.mContext = context;
        this.centre = null;
        this.entreprises = entreprises;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    public EntrepriseAdapter(Context context) {
        try {
            this.entreprises = StagesDAO.getInstance(context).getAllEntreprises();
        }catch(Exception e) {
            this.entreprises = new ArrayList<>();
        }finally {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
            this.mListener = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return this.entreprises.size();
    }

    @Override
    public Object getItem(int position) {
        return this.entreprises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout item;
        double lowDist;
        TextView nom, contact;
        TextView distance;

        if (convertView == null) {
            item = (LinearLayout) mInflater.inflate(R.layout.entre_list_item, parent, false);
        } else {
            item = (LinearLayout) convertView;
        }

        nom = (TextView) item.findViewById(R.id.entreprise_nom);
        contact = (TextView) item.findViewById(R.id.entreprise_contact);
        distance = (TextView) item.findViewById(R.id.distance);

        String title = this.entreprises.get(position).getNom();

        try {
            title += " (" + this.entreprises.get(position).getStages().size() + ")";
        } catch (Exception e) {
            title += " (?)";
        }

        nom.setText(title);

        try {
            List<Contact> contacts = this.entreprises.get(position).getContacts();
            contact.setText(this.entreprises.get(position).getContacts().get(0).toString());
        } catch (Exception e) {
            contact.setText("Aucun contact enregistré");
        }

        if (centre != null){
            lowDist = this.entreprises.get(position).getClosestDistanceToPoint(centre);
            if (lowDist > 0) {
                distance.setText(String.format("%.1f", lowDist) + " km");
            } else {
                distance.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0f));
            }
        }else{
            distance.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0f));
        }

        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(entreprises.get(position), position);
            }
        });


        return item;
    }

    public void addListener(EntrepriseAdapterListener entrepriseAdapterListener){
        mListener.add(entrepriseAdapterListener);
    }

    public void sendListener(Entreprise item, int position){
        for(int i = mListener.size()-1; i>=0; i--){
            mListener.get(i).onClickEntreprise(item, position);
        }
    }
}
