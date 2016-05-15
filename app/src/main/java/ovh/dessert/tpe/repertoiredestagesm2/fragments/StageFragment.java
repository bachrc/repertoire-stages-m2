package ovh.dessert.tpe.repertoiredestagesm2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ovh.dessert.tpe.repertoiredestagesm2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StageFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    public StageFragment() {
        // Required empty public constructor
    }


    public static StageFragment newInstance(String sectionNumber) {
        StageFragment fragment = new StageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stage, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.stage_title);
        textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
        return rootView;
    }

}
