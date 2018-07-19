package com.agritech.lea.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.agritech.lea.AppController;
import com.agritech.lea.AppInterface;
import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.models.TrackerItem;
import com.agritech.lea.models.TrackerItemAdapter;
import com.agritech.lea.utils.SessionManager;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TrackerFragment extends Fragment implements AppInterface {

    TextView start_date;
    FloatingActionButton fab;

    private static final String TAG = MainActivity.class.getCanonicalName();
    private RecyclerView plant_tracker;
    private ProgressDialog pDialog;
    private TrackerItemAdapter trackerAdapter;
    private List<TrackerItem> mItems;

    ArrayList<HashMap<String, String>> newList;

    SessionManager sessionManager;

    DatePickerDialog datePickerDialog;

    String planting_date;

    public TrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tracker, container, false);

        sessionManager = new SessionManager(getContext());

        start_date = (TextView) rootView.findViewById(R.id.start_date);
        fab = rootView.findViewById(R.id.fab);

        HashMap<String, String> user = sessionManager.getUserDetails();

        planting_date = user.get(sessionManager.KEY_PLANTING_DATE);

        start_date.setText(planting_date);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String new_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                //clear current recycler view
                                clearData();

                                //make new request
                                makeNewRequest(new_date);
                                start_date.setText(new_date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        newList = new ArrayList<HashMap<String, String>>();

        plant_tracker = (RecyclerView) rootView.findViewById(R.id.plant_tracker);

        mItems = new ArrayList<TrackerItem>();

        trackerAdapter = new TrackerItemAdapter(mItems);

        plant_tracker.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        plant_tracker.setLayoutManager(mLayoutManager);
        plant_tracker.setItemAnimator(new DefaultItemAnimator());
        plant_tracker.setAdapter(trackerAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(tracker);
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
            makeNewRequest(planting_date);
        }

        return rootView;
    }

    private void makeNewRequest(String date) {
        pDialog = new ProgressDialog(this.getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Please wait...");
        pDialog.show();

        // making fresh volley request and getting json
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                tracker + date, null, new Response.Listener<JSONObject>() {

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


    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray newsArray = response.getJSONArray("tracker");
            hidePDialog();

            sessionManager.addPlantingDate(response.getString("planting_date"));

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject feedObj = (JSONObject) newsArray.get(i);

                TrackerItem tracker = new TrackerItem();

                tracker.setDate(feedObj.getString("date"));
                tracker.setDays(feedObj.getString("days") + " days");
                tracker.setDescription(feedObj.getString("description"));
                tracker.setStage(feedObj.getString("stage"));
                mItems.add(tracker);
            }

            // notify data changes to list adapater
            trackerAdapter.notifyDataSetChanged();

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

    public void clearData() {
        mItems.clear();
        trackerAdapter.notifyDataSetChanged();
    }

}
