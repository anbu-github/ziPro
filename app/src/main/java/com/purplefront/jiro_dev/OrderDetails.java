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
import com.purplefront.jiro_dev.model.AddressSelection;
import com.purplefront.jiro_dev.model.Purchase_Order_Model;
import com.purplefront.jiro_dev.parsers.OrderDisp_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 25/10/16.
 */

public class OrderDetails extends ActionBarActivity implements PurchaseOrderListAdapter.DataFromAdapterToActivity {

    String id="", name="", redirection="";

    ImageButton addButton;

    Intent intent;
    ProgressDialog progress;

    private String tag_string_req = "string_req";


    List<Purchase_Order_Model> feedlist;


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordersdetail_form);


        progress = new ProgressDialog(OrderDetails.this);
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


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Orders" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);


        mRecyclerView = (RecyclerView) findViewById(R.id.rview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(OrderDetails.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);


        progress.show();
        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/master_orders.php");
        } else {
            Toast.makeText(OrderDetails.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }




        addButton = (ImageButton)findViewById(R.id.btn_add_address);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.pro_total_amtlist.clear();
                StaticVariable.companyContact="";
                StaticVariable.customerContactName="";
                StaticVariable.catalog_id="";
                StaticVariable.assignuser_id="";
                StaticVariable.tax_id="";
                AddressSelection addressSelection = new AddressSelection();
                StaticVariable.billaddselection = addressSelection;
                StaticVariable.shipaddselection = addressSelection;

                Intent intent = new Intent(OrderDetails.this,OrderCreate.class);
                JiroUtil.removeAllProduct();
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                OrderDetails.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

    }


    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("ORDDIS","ORDDIS"+s);

                feedlist = OrderDisp_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(OrderDetails.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(OrderDetails.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("group_id",StaticVariable.groupid);
                params.put("business_id",StaticVariable.business_id);
                params.put("role_id",StaticVariable.role_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }



    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

                Log.d("GEIDD","GEIDD");
                mAdapter = new PurchaseOrderListAdapter(feedlist, OrderDetails.this);
                mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) OrderDetails.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                SubCategory.this.finish();
//                SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                Intent intent = new Intent(OrderDetails.this, SubCategory.class);
                intent.putExtra("redirection","Catalog");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                startActivity(intent);
                OrderDetails.this.finish();

                OrderDetails.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderDetails.this, SubCategory.class);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);

        this.finish();
        startActivity(intent);
        OrderDetails.this.finish();

        OrderDetails.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }



    @Override
    public void viewOrders(String id) {
        Log.d("SCATID","SCATID"+id);
        Intent intent = new Intent(OrderDetails.this, OrderView.class);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra("order_id",id);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
