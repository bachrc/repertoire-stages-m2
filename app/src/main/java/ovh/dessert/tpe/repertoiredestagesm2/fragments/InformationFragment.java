package ovh.dessert.tpe.repertoiredestagesm2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;
import ovh.dessert.tpe.repertoiredestagesm2.adapters.LocalisationAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class InformationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static StagesDAO STAGES;
    private static final String ARG_NAME = "name";
    private static final String ARG_WEBSITE = "website";

    public InformationFragment() {
    //    STAGES = StagesDAO.getInstance(this.getContext());
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

        ListView listView = (ListView) rootView.findViewById(R.id.locations_entreprise);
        List<Localisation> ll = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            Localisation l = new Localisation(
                    "Test",
                    180*Math.random()-90,
                    360*Math.random()-180,
                    (int)(28*Math.random()+1) + ", Rue de L'Eglise " + (int)(98998*Math.random()+1001) + " Bigarville",
                    "Entreprise test"
            );
            ll.add(l);
        }
        LocalisationAdapter la = new LocalisationAdapter(this.getContext(), ll);
        listView.setAdapter(la);

        return rootView;
    }
}