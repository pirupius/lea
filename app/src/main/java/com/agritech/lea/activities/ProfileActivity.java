package com.agritech.lea.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.agritech.lea.R;
import com.agritech.lea.utils.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    TextView name, phone, district, location;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(getApplicationContext());

        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        district = (TextView) findViewById(R.id.district);
        location = (TextView) findViewById(R.id.location);

        HashMap<String, String> user = sessionManager.getUserDetails();

        name.setText(user.get(SessionManager.KEY_NAME));
        phone.setText(user.get(SessionManager.KEY_PHONE));
        district.setText(user.get(SessionManager.KEY_DISTRICT));
        location.setText(user.get(SessionManager.KEY_LOCATION));
    }
}
