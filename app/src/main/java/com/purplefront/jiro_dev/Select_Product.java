package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Product_List_Model;
import com.purplefront.jiro_dev.parsers.CatProdList_JSONParser;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.purplefront.jiro_dev.model.Create_Address_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 27/10/16.
 */

public class Select_Product extends ActionBarActivity {

    String id = "",name = "",business_id = "",redirection = "";
    ArrayList<Product_List_Model> feedlist;

    List<Product_List_Model> sort_database_existing=new ArrayList<>();

    Intent intent;
    private String tag_string_req = "string_req";
    ProgressDialog progress;
    private String inputText;
    EditText editText;
    ExpandableListView listview;
    SearchView search_view;
    private DisplayAdapter2 listAdapter ;
    private Create_Address_Model mBilling,mShipping;

    ArrayList<String>selectedContactList = new ArrayList<>();


    private void processExtras() {
        if(null != getIntent()) {
            mShipping =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.ShippingAddres.name());
            mBilling =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.BillingAddress.name());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_product);
        processExtras();

        progress = new ProgressDialog(Select_Product.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();


        listview = (ExpandableListView) findViewById(R.id.lv);

        editText = (EditText)findViewById(R.id.eText);

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        redirection =  intent.getStringExtra("redirection");


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Choose Product" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("Catalog View");
        getSupportActionBar().setIcon(R.drawable.pf);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                editChange();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });














        progress.show();

        if (isonline()) {
            getproductdisp(getResources().getString(R.string.url_reference) + "home/products_list.php");




        } else {
            Toast.makeText(Select_Product.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

    }


//    public void selectedProd()
//    {
//        selectedContactList.clear();
//        for (int i = 0; i < feedlist.size(); i++) {
//
////                        updatedSelectIdList.add(groupFeedlist.get(i).getChecked() + "");
//
//            if (feedlist.get(i).getChecked() == true) {
//                selectedContactList.add(feedlist.get(i).getProduct_id());
//            }
//        }
//
//        Log.v("SLISt","SLISt"+selectedContactList.toString());
//
//
//    }



    private void editChange()
    {

        inputText = editText.getText().toString();
        sort_database_existing.clear();
        for(int i = 0 ; i < feedlist.size(); i++)
        {
            if(feedlist.get(i).getProduct_name().contains(inputText))
            {
                Product_List_Model model= new Product_List_Model();

                model.setProduct_name(feedlist.get(i).getProduct_name());
                model.setChecked(feedlist.get(i).getChecked());
                model.setProduct_id(feedlist.get(i).getProduct_id());
                sort_database_existing.add(model);
            }
        }

        listAdapter = new DisplayAdapter2(Select_Product.this, sort_database_existing,"crtegrp");
        listview.setAdapter(listAdapter);



    }




    private void display_data() {
        progress.show();
        if (feedlist != null) {

            listAdapter = new DisplayAdapter2(Select_Product.this, feedlist);
            listview.setAdapter(listAdapter);
        }
        progress.hide();
    }

    private void getproductdisp(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);

                feedlist = CatProdList_JSONParser.parserFeed(s);

                Log.d("PRODIS","PRODIS"+feedlist.size());


                display_data();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(Select_Product.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Select_Product.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("catalog_id",StaticVariable.catalog_id);
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
        ConnectivityManager cm = (ConnectivityManager) Select_Product.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.add_button:

                nextPage();
                return true;


            default:
                return true;
        }
    }

    public void nextPage() {
        //selectedProd();
       Intent in = new Intent(Select_Product.this, DisplayCat.class);
        in.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        in.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);

       in.putExtra("id",StaticVariable.groupid);
        in.putExtra("name",name);
        //in.putExtra("selpro",feedlist);
        in.putExtra("redirection",redirection);
        startActivity(in);
       overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }



}
