package fedi.trabelsi.tp2.ia2.eniso.eniso.Welcome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fedi.trabelsi.tp2.ia2.eniso.eniso.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tasks extends Fragment {


    public Tasks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

}
