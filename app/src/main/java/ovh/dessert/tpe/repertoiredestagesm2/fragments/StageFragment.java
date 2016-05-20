package ovh.dessert.tpe.repertoiredestagesm2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.R;
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

    }
}
