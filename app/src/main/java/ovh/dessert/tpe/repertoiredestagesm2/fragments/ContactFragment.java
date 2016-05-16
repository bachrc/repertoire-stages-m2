package ovh.dessert.tpe.repertoiredestagesm2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ovh.dessert.tpe.repertoiredestagesm2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ContactFragment() {
        // Required empty public constructor
    }


    public static ContactFragment newInstance(String sectionNumber) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
       /* TextView textView = (TextView) rootView.findViewById(R.id.contact_title);
        textView.setText(getArguments().getString(ARG_SECTION_NUMBER));*/
        return rootView;
    }

}
