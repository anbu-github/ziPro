package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.parsers.GroupPage_JSONParser;
import com.purplefront.jiro_dev.model.Databaseaccess;
import com.purplefront.jiro_dev.model.Group_Category_Model;

import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class GroupCategory extends ActionBarActivity {

    String id = "",name = "",business_id = "";
    List<Group_Category_Model> feedlist;
    Intent intent;
    ProgressDialog progress;
    int i;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String tag_string_req = "string_req";

    List<Databaseaccess> database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_category);

        progress = new ProgressDialog(GroupCategory.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        getActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "MarkTech" + "</font>")));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setSubtitle("Group Category");
        getActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(GroupCategory.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            dbhelp.DatabaseHelper2 db = new dbhelp.DatabaseHelper2(GroupCategory.this);
            database = db.getTodo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Internet_Access ac = new Internet_Access();
        if (ac.isonline(GroupCategory.this)) {
            progress.show();
            getdata(getResources().getString(R.string.url_reference1) + "home/home_data.php");
        } else {
            Toast.makeText(GroupCategory.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

    }

    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("RESO","RESO"+ s);
                feedlist = GroupPage_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(GroupCategory.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            //mAdapter = new Group_Category_Adaptor(pageResponse, GroupCategory.this);
            mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }
}
