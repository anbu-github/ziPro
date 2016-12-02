package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.parsers.Create_Address_JSONParser;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

import static com.purplefront.jiro_dev.viewholder.AddressViewHolder.Address.BillingAddress;
import static com.purplefront.jiro_dev.viewholder.AddressViewHolder.Address.ShippingAddres;

/**
 * Created by apple on 25/10/16.
 */

public class OrderAddress extends ActionBarActivity {

    Intent intent;
    ProgressDialog progress;
    String id="", name="", redirection="";
    private RecyclerView recyclerView;
    StaggeredGridLayoutManager mLayoutManager;
    private Address_Section_Adaptor mAdapter;
    List<Create_Address_Model> feedlist;
    String tag_string_req_recieve2 = "string_req_recieve2";
    private Create_Address_Model mBilling,mShipping;
    ArrayList<AttributeModel> attributes,all_attributes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_select_address);
        processExtras();


        progress = new ProgressDialog(OrderAddress.this);
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
        attributes = intent.getParcelableArrayListExtra("attributes");

        try
        {
            Log.d("BACLG","BACLG"+attributes.size());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Select Address" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        progress.show();


        if (isonline()) {
            getSelectAddress(getResources().getString(R.string.url_reference) + "home/customer_address_list.php");

        } else {
            Toast.makeText(OrderAddress.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

    }

    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            Log.d("GEIDD","GEIDD");
                 mAdapter = new Address_Section_Adaptor(feedlist, OrderAddress.this);
                recyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }

    private void getSelectAddress(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("ADDRES","ADDRES"+s);

                feedlist = Create_Address_JSONParser.parserFeed(s);


                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(OrderAddress.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
                //Toast.makeText(Product_list.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                Log.d("id",subcategory_id);
//                Log.d("id_d",category_id);
//                //Log.d("business_id",business_id);
//                params.put("id", category_id);
//                params.put("business_id",business_id);
                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_id",StaticVariable.companyContact);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) OrderAddress.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderAddress.this, OrderCreate.class);
        intent.putExtra("redirection","reviworder");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        intent.putParcelableArrayListExtra("attributes",all_attributes);
        startActivity(intent);
        OrderAddress.this.finish();
        OrderAddress.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_next, menu);


        return super.onCreateOptionsMenu(menu);
    }



    private void processExtras() {
        if(null != getIntent()) {
            mShipping =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.ShippingAddres.name());
            mBilling =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.BillingAddress.name());
            attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
            all_attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(OrderAddress.this, OrderCreate.class);
                intent.putExtra("redirection","reviworder");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
                intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
                intent.putParcelableArrayListExtra("attributes",all_attributes);
                startActivity(intent);
                OrderAddress.this.finish();
                OrderAddress.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;




            case R.id.next_button:

                nextPage();
                return true;

            default:
                return true;
        }
    }

    public void nextPage() {
        StaticVariable.pro_total_amtlist.clear();
        StaticVariable.uom_pos_list.clear();
        StaticVariable.qtylist.clear();

        EventBus.getDefault().unregister(OrderAddress.this);

         if (!StaticVariable.shipaddselection.isChecked()){
            Toast.makeText(OrderAddress.this, "Please Select Shipping Address", Toast.LENGTH_SHORT).show();


        } else if (!StaticVariable.billaddselection.isChecked()){
            Toast.makeText(OrderAddress.this,"Please Select Billing Address",Toast.LENGTH_SHORT).show();

        }

        else{


            Intent in = new Intent(OrderAddress.this, DisplayCat.class);

            Log.d("BLPOS","BLPOS"+StaticVariable.billposId);
            Log.d("SHPOS","SHPOS"+StaticVariable.shipposId);

            in.putExtra(ShippingAddres.name() , feedlist.get(StaticVariable.billaddselection.getmPosition()));
            in.putExtra(BillingAddress.name() , feedlist.get(StaticVariable.shipaddselection.getmPosition()));
            in.putExtra("id", StaticVariable.groupid);
            in.putExtra("name", name);
            in.putExtra("redirection",redirection);
            in.putParcelableArrayListExtra("attributes",all_attributes);
            startActivity(in);
            finish();

            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


        }
/*

        if(null != mAdapter)

        if (null == mAdapter.getShippingAddress() || !mAdapter.getShippingAddress().isChecked()){

            Toast.makeText(OrderAddress.this, "Please Select Shipping Address", Toast.LENGTH_SHORT).show();
        }
        else if(null == mAdapter.getBillingAddress() || !mAdapter.getBillingAddress().isChecked())
        {
            Toast.makeText(OrderAddress.this,"Please Select Billing Address",Toast.LENGTH_SHORT).show();
        }
        else {


            Intent in = new Intent(OrderAddress.this, DisplayCat.class);

            Log.d("BLPOS","BLPOS"+StaticVariable.billposId);
            Log.d("SHPOS","SHPOS"+StaticVariable.shipposId);

            in.putExtra(ShippingAddres.name() , feedlist.get(mAdapter.getShippingAddress().getmPosition()));
            in.putExtra(BillingAddress.name() , feedlist.get(mAdapter.getBillingAddress().getmPosition()));
            in.putExtra("id", StaticVariable.groupid);
            in.putExtra("name", name);
            in.putExtra("redirection",redirection);
            in.putParcelableArrayListExtra("attributes",all_attributes);
            startActivity(in);
            finish();

            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
*/



    }

}
