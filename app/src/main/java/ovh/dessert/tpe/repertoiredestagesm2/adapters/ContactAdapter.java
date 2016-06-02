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
import ovh.dessert.tpe.repertoiredestagesm2.entities.Contact;

/**
 * Created by Unmei Muma on 18/05/2016.
 */
public class ContactAdapter extends BaseAdapter {


    /**
     * Ecouteur sur un item de liste Contact
     */
    public interface ContactAdapterListener {
        public void onClickContact(Contact item, int position);
    }

    // Liste de contacts
    private List<Contact> contacts;

    // Le contexte dans lequel est présent notre adapter
    private Context mContext;

    // Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    // Liste d'écouteurs de contacts.
    private List<ContactAdapterListener> mListener;

    /**
     * Crée un adapter, pour afficher une liste de contacts.
     * @param context Le contexte associé à l'utilisation de l'adapter
     * @param contacts La liste de contacts
     */
    public ContactAdapter(Context context, List<Contact> contacts) {
        this.mContext = context;
        this.contacts = contacts;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    /**
     * Renvoie le nombre de contacts.
     * @return Nombre de contacts présents
     */
    @Override
    public int getCount() {
        return this.contacts.size();
    }


    /**
     * Renvoie un contact à une position donnée
     * @param position Une position particulière
     * @return Le contact qui se trouve à cette position
     */
    @Override
    public Object getItem(int position) {
        return this.contacts.get(position);
    }

    /**
     * Renvoie un ID associé à la liste de contacts
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
        TextView nom, phone;
        String nomS, phoneS;
        if(convertView == null){
            item = (LinearLayout) mInflater.inflate(R.layout.contact_list_item, parent, false);
        }else{

            item = (LinearLayout) convertView;
        }

        nom = (TextView) item.findViewById(R.id.nom_contact);
        nomS = contacts.get(position).toString();
        phone = (TextView) item.findViewById(R.id.phone_contact);
        phoneS = contacts.get(position).getTelephone();

        nom.setText(nomS);
        phone.setText(phoneS);

        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(contacts.get(position), position);
            }
        });

        return item;
    }

    /**
     * Ajoute un écouteur de contact
     * @param contactAdapterListener L'écouteur de contact
     */
    public void addListener(ContactAdapterListener contactAdapterListener){
        mListener.add(contactAdapterListener);
    }

    public void sendListener(Contact item, int position){
        for(int i = mListener.size()-1; i>=0; i--){
            mListener.get(i).onClickContact(item, position);
        }
    }
}
