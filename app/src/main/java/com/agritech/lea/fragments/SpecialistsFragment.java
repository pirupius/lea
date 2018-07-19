package com.agritech.lea.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.agritech.lea.AppInterface;
import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.activities.AgroExpertsActivity;
import com.agritech.lea.activities.ExtensionWorkersActivity;
import com.agritech.lea.activities.SuppliersActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialistsFragment extends Fragment implements AppInterface {

    Button suppliers, agromists, workers;

    private static final String TAG = MainActivity.class.getCanonicalName();

    public SpecialistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_specialists, container, false);

        suppliers = (Button) rootView.findViewById(R.id.suppliers);
        agromists = (Button) rootView.findViewById(R.id.agromists);
        workers = (Button) rootView.findViewById(R.id.workers);

        suppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getActivity(), SuppliersActivity.class);
                startActivity(i1);
            }
        });

        agromists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getActivity(), AgroExpertsActivity.class);
                startActivity(i2);
            }
        });

        workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getActivity(), ExtensionWorkersActivity.class);
                startActivity(i3);
            }
        });

        return rootView;
    }

}
