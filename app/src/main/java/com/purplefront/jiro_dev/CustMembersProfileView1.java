package com.purplefront.jiro_dev;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.parsers.AddressList_JSONParser;
import com.purplefront.jiro_dev.parsers.CustMemProfileCategory_JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 12/1/2016.
 */

public class CustMembersProfileView1 extends ActionBarActivity {

    String id="", cont_id = "" ,name="", mem_name= "", mem_contact = "", cust_id = "", cust_segid = "";
    Intent intent;
    List<CustomerModel> feedslist;
    List<Create_Address_Model> addslist;
    ProgressDialog progress;
    RecyclerView mRecyclerView1;
    Button viewContacts;
    LinearLayout addresslay,approverejectlay;
    ImageView btn_add_address;


    RecyclerView.LayoutManager mLayoutManager1;
    RecyclerView.Adapter mAdapter1;


    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.custmembersprofile1_view);

        progress = new ProgressDialog(CustMembersProfileView1.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        intent = getIntent();
        cust_id = intent.getStringExtra("cust_id");
        cust_segid = intent.getStringExtra("cust_segid");
        name = intent.getStringExtra("name");
        mem_contact = intent.getStringExtra("mem_contact");
        mem_name = intent.getStringExtra("mem_name");
        //cont_id = intent.getStringExtra("cont_id");



        mRecyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView1.setHasFixedSize(true);


        addresslay = (LinearLayout)findViewById(R.id.addresslay);

        approverejectlay = (LinearLayout)findViewById(R.id.approverejlayout);

        viewContacts = (Button) findViewById(R.id.viewcontacts);


        btn_add_address = (ImageView)findViewById(R.id.btn_add_address);

        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustMembersProfileView1.this,CreateCust_Address.class);
                intent.putExtra("cust_id",StaticVariable.customer_segment_cust_id);
                intent.putExtra("name",name);
                intent.putExtra("id",StaticVariable.customer_segment_id);
                CustMembersProfileView1.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        viewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustMembersProfileView1.this,ViewContact1.class);
                intent.putExtra("cust_id",StaticVariable.customer_segment_cust_id);
                intent.putExtra("name",name);
                intent.putExtra("id",StaticVariable.customer_segment_id);
                CustMembersProfileView1.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });



        mLayoutManager1 = new GridLayoutManager(CustMembersProfileView1.this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);


        Log.d("PRID1","PRID1"+cust_id);


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Customer Details" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        progress.show();
        if (isonline()) {
            ViewMembers(getResources().getString(R.string.url_reference) + "home/group_customers_details.php");
        } else {
            Toast.makeText(CustMembersProfileView1.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }


    }


    private void ApprovedCustomer(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("AppCusRes","AppCusRes"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CustMembersProfileView1.this);
                builder.setMessage(getResources().getString(R.string.customerapprove))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CustMembersProfileView1.this, CustomerFragActivity.class);
                                intent.putExtra("id",StaticVariable.groupid);
                                intent.putExtra("name",name);
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CustMembersProfileView1.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CustMembersProfileView1.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("RR1","RR1"+StaticVariable.customer_segment_cust_id);
                Log.d("RR2","RR2"+StaticVariable.business_id);
                Log.d("RR3","RR3"+StaticVariable.user_id);

                params.put("id",StaticVariable.user_id);
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










    private void display_data()
    {
        progress.show();
        if(feedslist != null) for (final CustomerModel flower : feedslist) {

//            mAdapter = new MemberSelectionAdapter(pageResponse, MembersProfileView.this);
//            mRecyclerView.setAdapter(mAdapter);

            TextView tv3 = (TextView) findViewById(R.id.mem_name);
            tv3.setText(flower.getName());


            TextView tv4 = (TextView) findViewById(R.id.mem_email);
            tv4.setText(flower.getEmail());

            TextView tv5 = (TextView) findViewById(R.id.phone);
            tv5.setText(flower.getContact());


            TextView tv6 = (TextView) findViewById(R.id.profile);
            tv6.setText(flower.getProfile_id());

            TextView tv7 = (TextView) findViewById(R.id.status);
            tv7.setText(flower.getActive());


        }

        if(!addslist.get(0).getAddressline1().equals("No Data"))
        {
            mAdapter1 = new Cust_Add_Adaptor(addslist, CustMembersProfileView1.this,"categoires");
            mRecyclerView1.setAdapter(mAdapter1);
            addresslay.setVisibility(View.VISIBLE);
        }

        else {
            addresslay.setVisibility(View.INVISIBLE);
        }



        progress.hide();
    }













    private void ViewMembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("VWCUST","VWCUST"+s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String basic=jsonObject.getString("basic_details");
                    feedslist = CustMemProfileCategory_JSONParser.parserFeed(basic);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String address = jsonObject.getString("address");



                    addslist = AddressList_JSONParser.parserFeed(address);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }



                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CustMembersProfileView1.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CustMembersProfileView1.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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

                Log.d("MEM_ID","MEM_ID"+StaticVariable.customer_segment_cust_id);




                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                //params.put("member_id",id);
                params.put("customer_id",StaticVariable.customer_segment_cust_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) CustMembersProfileView1.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }



    public void goback()
    {
        Intent intent = new Intent(CustMembersProfileView1.this,CustomerFragActivity.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BKCID1","BKCID1"+cust_id);
        Log.d("BKNAME1","BKNAME1"+name);
        Log.d("BKCSEG1","BKCSEG1"+cust_segid);
        intent.putExtra("cust_id",StaticVariable.customer_segment_cust_id);
        intent.putExtra("name",StaticVariable.grpdisplay_backcustbtn);
        intent.putExtra("id",StaticVariable.customer_segment_id);
        CustMembersProfileView1.this.finish();
        this.finish();
        startActivity(intent);
        CustMembersProfileView1.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goback();
                return true;
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
