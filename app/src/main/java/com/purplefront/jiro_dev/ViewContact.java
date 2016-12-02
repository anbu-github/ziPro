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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerContact;
import com.purplefront.jiro_dev.parsers.CustContact_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 19/10/16.
 */

public class ViewContact extends ActionBarActivity {

    String contid="", name="", redirection="", cust_segid="";
    Intent intent;
    List<CustomerContact> feedlist;
    ProgressDialog progress;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String tag_string_req = "string_req";
    TextView nodata1;
    ImageButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_category);
        progress = new ProgressDialog(ViewContact.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        try
        {
            intent = getIntent();
            contid = intent.getStringExtra("cust_id");
            name = intent.getStringExtra("name");
            cust_segid = intent.getStringExtra("id");
            redirection =  intent.getStringExtra("redirection");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        Log.d("PRID1","PRID1"+contid);


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Customer Contact Details" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        nodata1 = (TextView)findViewById(R.id.nodata1);


        mLayoutManager = new GridLayoutManager(ViewContact.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progress.show();
        if (isonline()) {
            getcontacts(getResources().getString(R.string.url_reference) + "home/group_customer_contacts_list.php");
        } else {
            Toast.makeText(ViewContact.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }



        addButton = (ImageButton)findViewById(R.id.btn_add_contact);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewContact.this,CreateCustomerContact.class);
                intent.putExtra("id",StaticVariable.user_id);
                intent.putExtra("email",StaticVariable.email);
                intent.putExtra("cust_id",StaticVariable.customer_segment_cust_id);
                intent.putExtra("cust_segid",StaticVariable.customer_segment_id);
                intent.putExtra("name",name);
                intent.putExtra("redirection",redirection);
                startActivity(intent);
                ViewContact.this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });
    }

    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            if(!feedlist.get(0).getName().equals("No Data"))
            {
                nodata1.setVisibility(View.GONE);
                mAdapter = new Cust_Contact_Adapter(feedlist, ViewContact.this);
                mRecyclerView.setAdapter(mAdapter);
            }
            else {
                nodata1.setVisibility(View.VISIBLE);
            }



        }
        progress.hide();
    }

    private void getcontacts(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CUSTDET","CUSTDET"+s);
                feedlist = CustContact_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(ViewContact.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(ViewContact.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Log.d("VCKID","VCKID"+StaticVariable.user_id);
                Log.d("VCKEMAIL","VCKEMAIL"+StaticVariable.email);
                Log.d("VCKEBID","VCKEBID"+StaticVariable.business_id);
                Log.d("VCKECID","VCKECID"+StaticVariable.contact);
                Log.d("VCKEGID","VCKEGID"+StaticVariable.groupid);
                Log.d("VCTID","VCTID"+contid);




                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_id",StaticVariable.customer_segment_cust_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) ViewContact.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ViewContact.this, CustMembersProfileView.class);
                intent.putExtra("redirection","Catalog");
                intent.putExtra("cust_segid",cust_segid);
                intent.putExtra("name",name);
                intent.putExtra("cust_id",contid);
                startActivity(intent);
                ViewContact.this.finish();
                ViewContact.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewContact.this, CustMembersProfileView.class);
        //intent.putExtra("cust_id",cust_id);
        intent.putExtra("name",name);
        intent.putExtra("cust_segid",cust_segid);
        intent.putExtra("cust_id",contid);
        startActivity(intent);
        ViewContact.this.finish();

        ViewContact.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }




}
