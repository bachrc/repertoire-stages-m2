package ovh.dessert.tpe.repertoiredestagesm2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ovh.dessert.tpe.repertoiredestagesm2.R;

public class InformationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_NAME = "name";
    private static final String ARG_WEBSITE = "website";

    public InformationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InformationFragment newInstance(String name, String website) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_WEBSITE, website);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.title_entreprise);
        textView.setText(getArguments().getString(ARG_NAME));

        TextView textViewSite = (TextView) rootView.findViewById(R.id.site_entreprise);
        textViewSite.setText(getArguments().getString(ARG_WEBSITE));

        return rootView;
    }
}