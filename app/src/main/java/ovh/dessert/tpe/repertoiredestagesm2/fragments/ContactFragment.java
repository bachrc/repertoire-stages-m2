package ovh.dessert.tpe.repertoiredestagesm2.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.adapters.ContactAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Contact;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactAdapter.ContactAdapterListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    protected static List<Contact> contactList;

    public ContactFragment() {
        // Required empty public constructor
    }


    public static ContactFragment newInstance(List<Contact> list) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        contactList = list;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.liste_contact);
        ContactAdapter adapter = new ContactAdapter(this.getContext(), contactList);
        adapter.addListener(this);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClickContact(Contact item, int position) {
        String phone = item.getTelephone();
        Intent phoneCall = new Intent(Intent.ACTION_DIAL);
        phoneCall.setData(Uri.parse("tel:"+phone));
        try{
            startActivity(phoneCall);
        }catch (Exception e){
            Toast.makeText(getContext(),getString(R.string.erreur_phone), Toast.LENGTH_SHORT).show();
        }
    }


}
