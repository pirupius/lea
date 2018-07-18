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
import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.models.SupplierItem;
import com.agritech.lea.models.SupplierItemAdapter;
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

public class SuppliersActivity extends AppCompatActivity implements AppInterface{

    private static final String TAG = MainActivity.class.getCanonicalName();

    private RecyclerView supplier_list;
    private ProgressDialog pDialog;
    private SupplierItemAdapter supplierAdapter;
    private List<SupplierItem> mItems;

    ArrayList<HashMap<String, String>> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        newList = new ArrayList<HashMap<String, String>>();

        supplier_list = (RecyclerView) findViewById(R.id.supplier_list);

        mItems = new ArrayList<SupplierItem>();

        supplierAdapter = new SupplierItemAdapter(mItems);

        supplier_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        supplier_list.setLayoutManager(mLayoutManager);
        supplier_list.setItemAnimator(new DefaultItemAnimator());
        supplier_list.setAdapter(supplierAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(suppliers);
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
            pDialog = new ProgressDialog(SuppliersActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Please wait...");
            pDialog.show();

            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    suppliers, null, new Response.Listener<JSONObject>() {

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
            JSONArray newsArray = response.getJSONArray("suppliers");
            hidePDialog();

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject feedObj = (JSONObject) newsArray.get(i);

                SupplierItem suppliers = new SupplierItem();

                suppliers.setId(feedObj.getString("id"));
                suppliers.setName(feedObj.getString("name"));
                suppliers.setLocation(feedObj.getString("location"));
                suppliers.setChemicals("Pesticides: " + feedObj.getString("pesticides"));
                suppliers.setPhone(feedObj.getString("phone"));
                mItems.add(suppliers);
            }

            // notify data changes to list adapater
            supplierAdapter.notifyDataSetChanged();

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
