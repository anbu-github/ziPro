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
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.parsers.SegCustMembers_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 17/10/16.
 */

public class Customermemlist extends ActionBarActivity implements CustMemberSelectionAdapter.DataFromAdapterToActivity {

    String cust_segid="", name="", redirection="",cust_id= "";
    Intent intent;
    ImageButton addButton;

    List<CustomerModel> feedlist;

    ProgressDialog progress;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_mem_category);


        progress = new ProgressDialog(Customermemlist.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        intent = getIntent();
        cust_segid = intent.getStringExtra("id");
        cust_id = intent.getStringExtra("cust_id");
        name = intent.getStringExtra("name");
        redirection =  intent.getStringExtra("redirection");

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Customers" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new GridLayoutManager(Customermemlist.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progress.show();
        if (isonline()) {
            getcustmembers(getResources().getString(R.string.url_reference) + "home/group_customers_list.php");
        } else {
            Toast.makeText(Customermemlist.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }


        addButton = (ImageButton)findViewById(R.id.btn_add_address);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Customermemlist.this,CreateCustomer.class);
                intent.putExtra("id",StaticVariable.user_id);
                intent.putExtra("email",StaticVariable.email);
                intent.putExtra("cust_segid",cust_segid);
                intent.putExtra("name",name);
                startActivity(intent);
                Customermemlist.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });



    }
    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            mAdapter = new CustMemberSelectionAdapter(feedlist, Customermemlist.this);
            mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }

    private void getcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("MEMBERLSTSEC","MEMBERLSTSEC"+s);
                feedlist = SegCustMembers_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(Customermemlist.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Customermemlist.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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
                Log.d("SEGID","SEGID"+cust_segid);




                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_segment_id",cust_segid);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) Customermemlist.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public void memberID(String mem_name, String mem_contact,String id) {



        Intent intent = new Intent(Customermemlist.this, CustMembersProfileView.class);

        intent.putExtra("cust_id", id);
        intent.putExtra("name", name);
        intent.putExtra("mem_name",mem_name);
        intent.putExtra("mem_contact",mem_contact);
        intent.putExtra("cust_segid",cust_segid);
        startActivity(intent);
        Customermemlist.this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                SubCategory.this.finish();
//                SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                Intent intent = new Intent(Customermemlist.this, Customerlist.class);
                intent.putExtra("redirection","Catalog");

                intent.putExtra("id",cust_segid);
                intent.putExtra("name",name);
                startActivity(intent);
                Customermemlist.this.finish();

                Customermemlist.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Customermemlist.this, Customerlist.class);
        intent.putExtra("id",cust_segid);
        intent.putExtra("name",name);
        this.finish();
        startActivity(intent);
        Customermemlist.this.finish();

        Customermemlist.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }


}
