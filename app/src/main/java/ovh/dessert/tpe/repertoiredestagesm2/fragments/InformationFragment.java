package ovh.dessert.tpe.repertoiredestagesm2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.SearchMap;
import ovh.dessert.tpe.repertoiredestagesm2.adapters.LocalisationAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Localisation;

public class InformationFragment extends Fragment implements LocalisationAdapter.LocalisationAdapterListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_NAME = "name";
    private static final String ARG_WEBSITE = "website";
    protected static List<Localisation> LIST;

    public InformationFragment() {
    //    STAGES = StagesDAO.getInstance(this.getContext());
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InformationFragment newInstance(String name, String website, List<Localisation> list) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_WEBSITE, website);
        LIST = list;
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
        LocalisationAdapter adapter = new LocalisationAdapter(this.getContext(), LIST);
        adapter.addListener(this);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClickLocalisation(Localisation item, int position) {
        double latitude = item.getLatitude();
        double longitude = item.getLongitude();
        String entreprise, adresse;
        try {
            entreprise = item.getEntreprise().getNom();
            adresse = item.getAdresse();
        } catch (Exception e) {
            entreprise = "Aucun nom";
            adresse = "Aucune adresse";
        }

        Intent intent = new Intent(this.getContext(), SearchMap.class);
        intent.putExtra("<Nom>", entreprise);
        intent.putExtra("<Adresse>", adresse);
        intent.putExtra("<Latitude>", latitude);
        intent.putExtra("<Longitude>", longitude);

        startActivity(intent);
    }
}