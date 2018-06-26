package com.agritech.lea.accounts;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText name, phone, email, district, location;
    RadioButton radio_male, radio_female;
    Button btn_register;

    String gender;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(getApplicationContext());

        name = (TextInputEditText)findViewById(R.id.name);
        phone = (TextInputEditText)findViewById(R.id.phone);
        email = (TextInputEditText)findViewById(R.id.email);
        district = (TextInputEditText)findViewById(R.id.district);
        location = (TextInputEditText)findViewById(R.id.location);

        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.createLoginSession(
                        name.getText().toString(),
                        "Male",
                        phone.getText().toString(),
                        email.getText().toString(),
                        district.getText().toString(),
                        location.getText().toString()
                );

                Toast.makeText(getApplicationContext(),
                        "Thank you for registering ", Toast.LENGTH_LONG).show();

                // Launch login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // male
                    break;
            case R.id.radio_female:
                if (checked)
                    // female
                    break;
        }
    }


}
