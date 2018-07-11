package com.agritech.lea.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agritech.lea.MainActivity;
import com.agritech.lea.R;
import com.agritech.lea.models.NewsItem;
import com.agritech.lea.models.NewsItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.agritech.lea.AppController;
import com.agritech.lea.AppInterface;
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


public class ForumFragment extends Fragment implements AppInterface{

    private static final String TAG = MainActivity.class.getCanonicalName();
    private RecyclerView news_list;
    private ProgressDialog pDialog;
    private NewsItemAdapter newsAdapter;
    private List<NewsItem> mItems;

    ArrayList<HashMap<String, String>> newList;

    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forum, container, false);

        newList = new ArrayList<HashMap<String, String>>();

        news_list = (RecyclerView) rootView.findViewById(R.id.news_list);

        mItems = new ArrayList<NewsItem>();

        newsAdapter = new NewsItemAdapter(mItems);

        news_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        news_list.setLayoutManager(mLayoutManager);
        news_list.setItemAnimator(new DefaultItemAnimator());
        news_list.setAdapter(newsAdapter);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(news);
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
                    news, null, new Response.Listener<JSONObject>() {

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
            JSONArray newsArray = response.getJSONArray("news");
            hidePDialog();

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject feedObj = (JSONObject) newsArray.get(i);

                NewsItem news = new NewsItem();

                news.setId(feedObj.getString("id"));
                news.setTitle(feedObj.getString("title"));
                news.setDate("Posted on " + feedObj.getString("created_at"));
                news.setBrief(feedObj.getString("brief"));

                //news.setDate(feedObj.getString("mdate"));
//                String rd = feedObj.getString("mdate");
//                news.setDate("Release date: " + rd);

                mItems.add(news);
            }

            // notify data changes to list adapater
            newsAdapter.notifyDataSetChanged();

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
