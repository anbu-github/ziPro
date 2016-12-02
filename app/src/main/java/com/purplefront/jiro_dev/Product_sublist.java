package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.ProductPageResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 04/10/16.
 */

public class Product_sublist extends ActionBarActivity implements Product_SubList_Adaptor.DataFromAdapterToActivity,Product_SubList1_Adaptor.DataFromAdapterToActivity{

    String tag_string_req_recieve2 = "string_req_recieve2";
    ProgressDialog progress;
    String category_id = "", from = "";
    String category_name = "";
    String subcategory_id = "";
    String subcategory_name = "",business_id = "", product_name = "";
    String category = "",products = "";

    RecyclerView mRecyclerView,mRecyclerView1;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager1;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter1;

    //ExpandableListView mRecyclerView,mRecyclerView1;


    ArrayList<ProductPageResponse> pageResponse;
    //List<Product_List_Model1> feedlist1;

    LinearLayout linlayout,catlayout;
    TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_sublist);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        progress = new ProgressDialog(Product_sublist.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(getResources().getString(R.string.please_wait));
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        category_id = getIntent().getExtras().getString("category_id");

        Log.d("CAID","CAID"+category_id);
        Log.d("subcategory_id","CAID"+subcategory_id);

        category_name = getIntent().getExtras().getString("category_name");
        subcategory_id = getIntent().getExtras().getString("subcategory_id");
        subcategory_name = getIntent().getExtras().getString("subcategory_name");
        business_id = getIntent().getExtras().getString("business_id");
        product_name = getIntent().getExtras().getString("product_name");

        try {
            getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + product_name + "</font>")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.pf);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        linlayout = (LinearLayout)findViewById(R.id.productslayout);

        catlayout = (LinearLayout)findViewById(R.id.categorylayout);

        nodata = (TextView)findViewById(R.id.nodata);

        //mRecyclerView = (ExpandableListView)findViewById(R.id.recycler_view);



        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView1.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager1 = new GridLayoutManager(Product_sublist.this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView1.setLayoutManager(mLayoutManager1);

        if (isonline()) {
            progress.show();
            getdata(getResources().getString(R.string.url_reference) + "home/product_subcategory_list.php");
        } else {
            Toast.makeText(Product_sublist.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }
    }

    private void display_data() {
       // progress.show();
        mAdapter = new Product_SubList_Adaptor(pageResponse.get(0), Product_sublist.this);
        if(null != mRecyclerView){
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("PRORES","PRORES"+s);
                final Type type = new TypeToken<ArrayList<ProductPageResponse>>(){}.getType();

                Gson gson = AppController.getGsonInstance();
                Product_sublist.this.pageResponse= gson.fromJson(s, type);


                //Product_sublist.this.pageResponse =  getMockResponse(type);


                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(Product_sublist.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
                //Toast.makeText(Product_list.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.d("id",subcategory_id);
                Log.d("id_d",category_id);
                //Log.d("business_id",business_id);
                params.put("id", category_id);
                params.put("business_id",business_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Product_sublist.this.finish();
                Product_sublist.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) Product_sublist.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Product_sublist.this.finish();
        Product_sublist.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void productId(String product_name, String id,String from) {

        if (from.equals("products")) {
            Intent intent = new Intent(Product_sublist.this, Product_Description.class);
            intent.putExtra("category_id", category_id);
            intent.putExtra("category_name", category_name);
            intent.putExtra("subcategory_id", subcategory_id);
            intent.putExtra("subcategory_name", subcategory_name);
            intent.putExtra("product_id", id);
            intent.putExtra("product_name", product_name);
            intent.putExtra("business_id", business_id);
            startActivity(intent);
            //finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else if(from.equals("categoires"))
        {
            Intent intent = new Intent(Product_sublist.this,Product_sublist.class);
            intent.putExtra("category_id",id);
            intent.putExtra("category_name", category_name);
            intent.putExtra("subcategory_id", subcategory_id);
            intent.putExtra("subcategory_name", subcategory_name);
            intent.putExtra("product_id", id);
            intent.putExtra("product_name", product_name);
            intent.putExtra("business_id", business_id);
            startActivity(intent);
        }
    }


    private ProductPageResponse getMockResponse(Type type) {
        Reader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.response)));
        Gson gson = new Gson();
        return gson.fromJson(reader,type);
    }
}
