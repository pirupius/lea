package com.agritech.lea.accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.SelectLanguage;
import com.agritech.lea.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_register;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
//        session.checkLogin();

//        if(session.isLoggedIn()){
//            finish();
//        }

        btn_login = (Button) findViewById(R.id.btn_submit);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.createLoginSession("Test", "Male", "0752100100", "demo@lea.test", "Kampala", "Kiira");

                // Launch login activity
                Intent intent = new Intent(getApplicationContext(), SelectLanguage.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
