package com.agritech.lea.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SuppliersFragment extends Fragment implements AppInterface {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private RecyclerView supplier_list;
    private ProgressDialog pDialog;
    private SupplierItemAdapter supplierAdapter;
    private List<SupplierItem> mItems;

    ArrayList<HashMap<String, String>> newList;

    public SuppliersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_suppliers, container, false);

        newList = new ArrayList<HashMap<String, String>>();

        supplier_list = (RecyclerView) rootView.findViewById(R.id.supplier_list);

        mItems = new ArrayList<SupplierItem>();

        supplierAdapter = new SupplierItemAdapter(mItems);

        supplier_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
            pDialog = new ProgressDialog(this.getActivity());
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
                    Toast.makeText(getActivity(),
                            "Network error! Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

        return rootView;
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
                suppliers.setLocation(
                        feedObj.getString("village") + " - " + feedObj.getString("subcounty") +
                                ", " + feedObj.getString("district")
                );
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
