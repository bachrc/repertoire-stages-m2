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

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    private List<EntrepriseAdapterListener> mListener;

    public EntrepriseAdapter(Context context, List<Entreprise> entreprises) {
        this.mContext = context;
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
        TextView nom, contact;
        // TextView distance;

        if(convertView == null){
            item = (LinearLayout) mInflater.inflate(R.layout.entre_list_item, parent, false);
        }else{
            item = (LinearLayout) convertView;
        }

        nom = (TextView) item.findViewById(R.id.entreprise_nom);
        contact = (TextView) item.findViewById(R.id.entreprise_contact);
        // distance = (TextView) item.findViewById(R.id.distance);

        String title = entreprises.get(position).getNom();

        try {
            title += " (" + entreprises.get(position).getStages().size() + ")";
        } catch (Exception e) {
            title += " (?)";
        }

        nom.setText(title);

        try {
            List<Contact> contacts = entreprises.get(position).getContacts();
            contact.setText(entreprises.get(position).getContacts().get(0).toString());
        } catch (Exception e) {
            contact.setText("Aucun contact enregistré");
        }

        nom.setTag(position);
        nom.setOnClickListener(new View.OnClickListener() {
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
