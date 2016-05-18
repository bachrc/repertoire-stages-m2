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

    public interface ContactAdapterListener {
        public void onClickLocalisation(Contact item, int position);
    }

    private List<Contact> contacts;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    private List<ContactAdapterListener> mListener;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.mContext = context;
        this.contacts = contacts;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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

/*        item.setTag(position);
        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                sendListener(localisations.get(position), position);
            }
        });
*/
        return item;
    }
}
