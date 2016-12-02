package com.purplefront.jiro_dev;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Databaseaccess;
import com.purplefront.jiro_dev.model.Home_Model;
import com.purplefront.jiro_dev.parsers.MainPage_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeCategory extends Fragment{

    List<Home_Model> feedlist;
    String tag_string_req = "string_req";
    ProgressDialog progress;
    String id,name;
    int i;
    Intent intent;
    View rootView;
    List<Databaseaccess> database;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_category, container, false);

        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            dbhelp.DatabaseHelper2 db = new dbhelp.DatabaseHelper2(getActivity());
            database = db.getTodo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Internet_Access ac = new Internet_Access();
        if (ac.isonline(getActivity())) {
            progress.show();
            getgroups(getResources().getString(R.string.url_reference) + "home/home_data.php");

            //getdata(getResources().getString(R.string.url_reference) + "home/home_data.php");
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void display_data()
    {
        progress.show();
        if (feedlist != null) {

            mAdapter = new GridAdapter(feedlist, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }


    private void getgroups(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("GTGRP","GTGRP"+s);

                feedlist = MainPage_JSONParser.parserFeed(s);
                 display_data();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(getActivity(), getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("GTGRPID","GTGRPID"+StaticVariable.user_id);
                Log.d("GTGRPEMAIL","GTGRPEMAIL"+StaticVariable.email);
                Log.d("GTGRPBID","GTGRPBID"+StaticVariable.business_id);


                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                SubCategory.this.finish();
//                SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        getActivity().finish();
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//    }
}
