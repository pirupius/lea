package com.agritech.lea.accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agritech.lea.AppController;
import com.agritech.lea.AppInterface;
import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.SelectLanguage;
import com.agritech.lea.utils.SessionManager;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements AppInterface{

    private static final String TAG = LoginActivity.class.getCanonicalName();

    Button btn_login, btn_register;
    EditText phone_number;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn()){
            finish();
        }

        phone_number = (EditText) findViewById(R.id.phone_number);
        btn_login = (Button) findViewById(R.id.btn_submit);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phone_number.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(phone)) {
                    phone_number.setError("Contact is required");
                    focusView = phone_number;
                    cancel = true;
                }

                loginUser(phone);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     *
     * @param phone
     */
    private void loginUser(final String phone) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.GET,
                login + phone, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONObject user = jObj.getJSONObject("farmer");

                        String id = user.getString("id");
                        String name = user.getString("name");
                        String gender = user.getString("gender");
                        String phone = user.getString("phone");
                        String email = user.getString("email");
                        String district = user.getString("district");
                        String location = user.getString("location");

                        session.createLoginSession(id, name, gender, phone, email, district, location);

                        Toast.makeText(getApplicationContext(),
                                "Successfully logged in", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Failed "+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
