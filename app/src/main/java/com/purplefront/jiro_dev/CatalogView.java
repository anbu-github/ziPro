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
import com.purplefront.jiro_dev.model.Sub_Category_Model;
import com.purplefront.jiro_dev.parsers.SubCategory_JSONParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 04/11/16.
 */

public class CatalogView extends ActionBarActivity implements Sub_Category_Adaptor.DataFromAdapterToActivity {

    String id="", name="", redirection="";
    Intent intent;
    List<Sub_Category_Model> feedlist;
    ProgressDialog progress;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    TextView nodata;
    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_category);


        progress = new ProgressDialog(CatalogView.this);
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

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Catalog View" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.cata_recyview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(CatalogView.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);


        nodata = (TextView)findViewById(R.id.nodataitems);

        progress.show();
        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/product_catalogs.php");
        } else {
            Toast.makeText(CatalogView.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

    }



    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("PROCAT","PROCAT"+s);


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String jsonArray = jsonObject.getString("product_catalogs");
                    feedlist = SubCategory_JSONParser.parserFeed(jsonArray);
                    Log.d("FLI","FLI"+feedlist.size());

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CatalogView.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CatalogView.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("business_id",StaticVariable.business_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            if(!feedlist.get(0).getName().equals("No Data"))
            {

                Log.d("GEIDD","GEIDD");
                mAdapter = new Sub_Category_Adaptor(feedlist, CatalogView.this);
                mRecyclerView.setAdapter(mAdapter);
            }
            else {
                nodata.setVisibility(View.VISIBLE);
            }



        }
        progress.hide();
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) CatalogView.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CatalogView.this, SubCategory.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                CatalogView.this.finish();
                this.finish();
                startActivity(intent);
                CatalogView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CatalogView.this, SubCategory.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);
        CatalogView.this.finish();
        this.finish();
        startActivity(intent);
        CatalogView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public void subCategoryId(String subCategory_name, String sub_id) {
        Log.d("SCATID","SCATID"+sub_id);
        Intent intent = new Intent(CatalogView.this, Product_list.class);
        intent.putExtra("category_id", id);

        intent.putExtra("category_name", name);
        intent.putExtra("subcategory_id", sub_id);

        Log.d("SUBCAT","SUBCAT"+subCategory_name);
        intent.putExtra("subcategory_name", subCategory_name);
        intent.putExtra("business_id", "1");
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

}
