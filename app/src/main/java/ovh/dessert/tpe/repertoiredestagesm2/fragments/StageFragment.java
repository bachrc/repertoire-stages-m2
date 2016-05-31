package ovh.dessert.tpe.repertoiredestagesm2.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ovh.dessert.tpe.repertoiredestagesm2.R;
import ovh.dessert.tpe.repertoiredestagesm2.StageDetailActivity;
import ovh.dessert.tpe.repertoiredestagesm2.adapters.StageAdapter;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Stage;

/**
 * A simple {@link Fragment} subclass.
 */
public class StageFragment extends Fragment implements StageAdapter.StageAdapterListener{


    private static final String ARG_SECTION_NUMBER = "section_number";
    protected static List<Stage> stageList;

    public StageFragment() {
        // Required empty public constructor
    }


    public static StageFragment newInstance(List<Stage> list) {
        StageFragment fragment = new StageFragment();
        Bundle args = new Bundle();
        stageList = list;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stage, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.stage_list);
        StageAdapter adapter = new StageAdapter(this.getContext(), stageList);
        adapter.addListener(this);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClickStage(Stage item, int position) {
        Intent intent = new Intent(this.getContext(), StageDetailActivity.class);
        try {
            String dateDebut = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE).format(item.getDateDebut());
            String dateFin = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE).format(item.getDateFin());

            intent.putExtra("<Sujet>", item.getSujet());
            intent.putExtra("<Login>", item.getStagiaire().getLogin());
            intent.putExtra("<Entreprise>", item.getEntreprise().getAbbr());
            intent.putExtra("<Debut>", dateDebut);
            intent.putExtra("<Fin>", dateFin);
            intent.putExtra("<Tuteur>", item.getNomTuteur());
            intent.putExtra("<Maitre>", item.getNomMaitre());
            intent.putExtra("<Rapport>", item.getLienRapport());


            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(StageFragment.this.getContext(), getString(R.string.erreur_stage), Toast.LENGTH_LONG).show();
        }
    }
}
