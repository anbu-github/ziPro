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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.model.Product_List_Model;
import com.purplefront.jiro_dev.parsers.CatProdList_JSONParser;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.purplefront.jiro_dev.model.Create_Address_Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 26/10/16.
 */

public class DisplayCat extends ActionBarActivity implements CatPro_List_Adaptor.DataFromAdapterToActivity{


    String id = "",name = "",business_id = "",redirection = "";

    ArrayList<Product_List_Model>selpro;

    String tag_string_req_recieve2 = "string_req_recieve2";

    List<Product_List_Model> feedlist;

    Spinner Catloglist;


    ArrayList<String> catlogIdList = new ArrayList<>();

    ArrayList<String> catNameIdList = new ArrayList<>();

    ArrayList<String> currencyNameList = new ArrayList<>();

    ArrayList<String> currencyIdList = new ArrayList<>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CatPro_List_Adaptor mAdapter;
    Intent intent;
    private String tag_string_req = "string_req";
    ProgressDialog progress;
    String catidlist,catnamelist,currencyname,currencyid;
    ArrayAdapter<String> dataAdapter;
    ImageView add_product;
    private Create_Address_Model mBilling,mShipping;
    ArrayList<AttributeModel> attributes,all_attributes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catdisplay_order);
        processExtras();

        progress = new ProgressDialog(DisplayCat.this);
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



        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Select Product" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);


        Catloglist = (Spinner)findViewById(R.id.cat_sel_spinner);

        add_product = (ImageView)findViewById(R.id.add_product);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(DisplayCat.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new CatPro_List_Adaptor();
        mRecyclerView.setAdapter(mAdapter);

        progress.show();

       // pro_total_amtlist.clear();

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(DisplayCat.this, Select_Product.class);
                in.putExtra(AddressViewHolder.Address.ShippingAddres.name(), mShipping);
                in.putExtra(AddressViewHolder.Address.BillingAddress.name(), mBilling);
                in.putExtra("id",StaticVariable.groupid);
                in.putExtra("name",name);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

        if (isonline()) {
            getcatlogdisp(getResources().getString(R.string.url_reference) + "home/product_catalogs.php");




        } else {
            Toast.makeText(DisplayCat.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, catNameIdList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Catloglist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    StaticVariable.catalog_id = catlogIdList.get(position).toString();
                    Log.v("cata_id","cata_id"+StaticVariable.catalog_id);

                    StaticVariable.dispCataName = catNameIdList.get(position).toString();

                    Log.d("CANAME","CANAME"+StaticVariable.dispCataName);

                    StaticVariable.currency_id = currencyIdList.get(position).toString();
                    Log.d("CCURID","CCURID"+StaticVariable.currency_id);

                    StaticVariable.currencyName = currencyNameList.get(position).toString();
                    Log.d("CCUNAME","CCUNAME"+StaticVariable.currencyName);


                } catch (Exception e) {

                    e.printStackTrace();
                }

                getdata(getResources().getString(R.string.url_reference) + "home/products_list.php");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }





    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("PRORES","PRORES"+s);
                feedlist = CatProdList_JSONParser.parserFeed(s);

                Log.d("DDDIS","DDDIS"+feedlist.size());


                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(DisplayCat.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
                //Toast.makeText(Product_list.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("catalog_id",StaticVariable.catalog_id);
                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);

//                params.put("id", subcategory_id);
//                params.put("business_id",business_id);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


    private void display_data() {
        progress.show();
        if (feedlist != null) {
            mAdapter.setData(feedlist);
        }
        progress.hide();
    }




    private void getcatlogdisp(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);

                catlogIdList.clear();
                catNameIdList.clear();
                currencyNameList.clear();
                currencyIdList.clear();


                catlogIdList.add("Select");
                catNameIdList.add("Select");
                currencyNameList.add("Select");
                currencyIdList.add("Select");

                try {

                    JSONObject jsonObject = new JSONObject(s);

                    String jsonArray1 = jsonObject.getString("product_catalogs");

                    JSONArray ar = new JSONArray(jsonArray1);

                    for(int i = 0; i < ar.length();i++)
                    {
                        JSONObject obj = ar.getJSONObject(i);

                        catidlist = obj.getString("id");
                        catnamelist = obj.getString("name");
                        currencyname = obj.getString("currency_name");
                        currencyid = obj.getString("currency_id");

                        catlogIdList.add(catidlist);
                        catNameIdList.add(catnamelist);
                        currencyNameList.add(currencyname);
                        currencyIdList.add(currencyid);

                    }

                    Catloglist.setAdapter(dataAdapter);

                    for (int i = 0; i < catlogIdList.size();i++) {

                        if (StaticVariable.catalog_id.equals(catlogIdList.get(i))) {
                            Catloglist.setSelection(i);
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(DisplayCat.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(DisplayCat.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

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


    private void processExtras() {
        if(null != getIntent()) {
            mShipping =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.ShippingAddres.name());
            mBilling =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.BillingAddress.name());
            attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
            all_attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DisplayCat.this, OrderAddress.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        this.finish();
        startActivity(intent);
        DisplayCat.this.finish();
        DisplayCat.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DisplayCat.this, OrderAddress.class);
                intent.putExtra("redirection","reviworder");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
                intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
                intent.putParcelableArrayListExtra("attributes",all_attributes);
                startActivity(intent);
                DisplayCat.this.finish();

                DisplayCat.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;


            case R.id.next_button:

                if(!Catloglist.getSelectedItem().toString().equals("Select"))
                {
                    nextPage();
                    return true;
                }
                else {
                    Toast.makeText(DisplayCat.this, getResources().getString(R.string.selectcatalogname), Toast.LENGTH_LONG).show();
                }




            default:
                return true;
        }
    }

    public void nextPage() {

        for(int i=0;i < StaticVariable.pro_total_amtlist.size();i++) {
            Log.v("testtotl", StaticVariable.pro_total_amtlist.get(i));
        }

        Intent in = new Intent(DisplayCat.this, Attrbute_View.class);
        in.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        in.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        in.putExtra("id",StaticVariable.groupid);
        in.putExtra("name",name);
        //in.putParcelableArrayListExtra("attributes",all_attributes);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


    }



    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) DisplayCat.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public void productId(String subCategory_name, String sub_id) {
        Log.d("SCATID","SCATID"+sub_id);
        Intent intent = new Intent(DisplayCat.this, Product_Disp.class);
        intent.putExtra("category_id", id);
        intent.putExtra("category_name", name);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_next, menu);


        return super.onCreateOptionsMenu(menu);
    }
}
