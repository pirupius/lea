package com.agritech.lea.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agritech.lea.R;
import com.agritech.lea.utils.SessionManager;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView name, phone, district, location;

    SessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getContext());

        name = (TextView) rootView.findViewById(R.id.name);
        phone = (TextView) rootView.findViewById(R.id.phone);
        district = (TextView) rootView.findViewById(R.id.district);
        location = (TextView) rootView.findViewById(R.id.location);

        HashMap<String, String> user = sessionManager.getUserDetails();

        name.setText(user.get(SessionManager.KEY_NAME));
        phone.setText(user.get(SessionManager.KEY_PHONE));
        district.setText(user.get(SessionManager.KEY_DISTRICT));
        location.setText(user.get(SessionManager.KEY_LOCATION));

        return rootView;
    }

}
