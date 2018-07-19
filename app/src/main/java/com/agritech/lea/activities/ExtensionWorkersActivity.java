package com.agritech.lea.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.agritech.lea.AppController;
import com.agritech.lea.AppInterface;
import com.agritech.lea.R;
import com.agritech.lea.models.ExtensionWorkerItem;
import com.agritech.lea.models.ExtensionWorkerItemAdapter;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtensionWorkersActivity extends AppCompatActivity implements AppInterface {

    private static final String TAG = ExtensionWorkersActivity.class.getCanonicalName();

    private RecyclerView ew_list;
    private ProgressDialog pDialog;
    private ExtensionWorkerItemAdapter ewAdapter;
    private List<ExtensionWorkerItem> mItems;

    ArrayList<HashMap<String, String>> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_workers);

        newList = new ArrayList<HashMap<String, String>>();

        ew_list = (RecyclerView) findViewById(R.id.list);

        mItems = new ArrayList<ExtensionWorkerItem>();

        ewAdapter = new ExtensionWorkerItemAdapter(mItems);

        ew_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        ew_list.setLayoutManager(mLayoutManager);
        ew_list.setItemAnimator(new DefaultItemAnimator());
        ew_list.setAdapter(ewAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(extension_workers);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            pDialog = new ProgressDialog(ExtensionWorkersActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Please wait...");
            pDialog.show();

            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    extension_workers, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplication(),
                            "Network error! Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray newsArray = response.getJSONArray("extension_workers");
            hidePDialog();

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject feedObj = (JSONObject) newsArray.get(i);

                ExtensionWorkerItem extension_workers = new ExtensionWorkerItem();

                extension_workers.setId(feedObj.getString("id"));
                extension_workers.setName(feedObj.getString("name"));
                extension_workers.setLocation(feedObj.getString("village") + " ( "
                        + feedObj.getString("district") + ") ");
                extension_workers.setPhone(feedObj.getString("phone"));
                mItems.add(extension_workers);
            }

            // notify data changes to list adapater
            ewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            hidePDialog();
        }
    }

    private void hidePDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
