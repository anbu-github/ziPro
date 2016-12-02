package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.parsers.SegCustCategory_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 17/10/16.
 */

public class Customerlist extends ActionBarActivity implements CustSegmentSelectionAdapter.DataFromAdapterToActivity {

    String id="", name="", redirection="";
    Intent intent;


    List<CustomerModel> feedlist;

    ProgressDialog progress;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    TextView nodata1;

    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custmembers_category);


        progress = new ProgressDialog(Customerlist.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        redirection =  intent.getStringExtra("redirection");

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Customers Segments" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);


        nodata1 = (TextView)findViewById(R.id.noseg);


        mLayoutManager = new GridLayoutManager(Customerlist.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progress.show();
        if (isonline()) {
            getsegcustmembers(getResources().getString(R.string.url_reference) + "home/group_customer_segments_list.php");
        } else {
            Toast.makeText(Customerlist.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }





    }

    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            if(!feedlist.get(0).getName().equals("No Data"))
            {

                mAdapter = new CustSegmentSelectionAdapter(feedlist, Customerlist.this);
                mRecyclerView.setAdapter(mAdapter);

            }
            else
            {
                mRecyclerView.setVisibility(View.GONE);
                nodata1.setVisibility(View.VISIBLE);
            }

        }
        progress.hide();
    }

    private void getsegcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("MEMBERSEC","MEMBERSEC"+s);
                feedlist = SegCustCategory_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(Customerlist.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Customerlist.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Log.d("CKID","CKID"+StaticVariable.user_id);
                Log.d("CKEMAIL","CKEMAIL"+StaticVariable.email);
                Log.d("CKEBID","CKEBID"+StaticVariable.business_id);
                Log.d("CKECID","CKECID"+StaticVariable.contact);
                Log.d("CKEGID","CKEGID"+StaticVariable.groupid);




                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) Customerlist.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public void memberID(String mem_name, String mem_contact,String id) {


        Intent intent = new Intent(Customerlist.this, CustomerFragActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("mem_name",mem_name);
        intent.putExtra("mem_contact",mem_contact);
        startActivity(intent);
        Customerlist.this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


    }

    public void goback()
    {
        Intent intent = new Intent(Customerlist.this, SubCategory.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        startActivity(intent);
        Customerlist.this.finish();

        Customerlist.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goback();
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
          goback();

    }


}
