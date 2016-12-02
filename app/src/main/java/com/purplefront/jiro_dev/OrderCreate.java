package com.purplefront.jiro_dev;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 25/10/16.
 */

public class OrderCreate extends ActionBarActivity {

    String id="", name="", redirection="";

    ArrayList<String> companyIdList = new ArrayList<>();
    ArrayList<String> customerNameList = new ArrayList<>();

    ArrayList<String> contactnameIdlist  = new ArrayList<>();

    ArrayList<String> ctnameIdlist  = new ArrayList<>();
    ArrayList<AttributeModel> attributes,all_attributes;
    private Create_Address_Model mBilling,mShipping;





    ArrayAdapter<String> dataAdapter, dataAdapter2;

    SearchableSpinner Companylist;

    Spinner customerList;

    List<CustomerModel> feedlist;
    Intent intent;
    ProgressDialog progress;
    String cidlist,compidlist,customerName;

    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_creation);
        processExtras();

        Companylist = (SearchableSpinner) findViewById(R.id.cus_list);
        customerList = (Spinner) findViewById(R.id.cust_company_list);


        progress = new ProgressDialog(OrderCreate.this);
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
//        name = intent.getStringExtra("name");
//        redirection =  intent.getStringExtra("redirection");

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Create Order" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);




        progress.show();
        if (isonline()) {
            getcustmembers(getResources().getString(R.string.url_reference) + "home/customers_list.php");

        } else {
            Toast.makeText(OrderCreate.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

        companyIdList.add("Select");
        customerNameList.add("Select");
        contactnameIdlist.add("Select");

        dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, customerNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_layout, contactnameIdlist);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Companylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    StaticVariable.companyContact = companyIdList.get(position).toString();
                    Log.v("customer_id", StaticVariable.companyContact);

                    StaticVariable.dispcustomername = customerNameList.get(position).toString();
                    Log.d("CNAM","CNAM"+StaticVariable.dispcustomername);




                } catch (Exception e) {

                    e.printStackTrace();
                }

                getcontacts(getResources().getString(R.string.url_reference) + "home/customer_contacts_list.php");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        customerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                try {

                    StaticVariable.customerContactName = ctnameIdlist.get(position).toString();
                    Log.v("contact_id", StaticVariable.customerContactName);


                    StaticVariable.dispContactName = contactnameIdlist.get(position).toString();



                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










    }

    private void processExtras() {
        if(null != getIntent()) {
            mShipping =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.ShippingAddres.name());
            mBilling =  getIntent().getExtras().getParcelable(AddressViewHolder.Address.BillingAddress.name());
            attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
            all_attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
        }
    }


    private void getcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);

                companyIdList.clear();
                customerNameList.clear();

                customerNameList.add("Select");
                companyIdList.add("Select");

                try {

                    JSONArray jsonArray = new JSONArray(s);

                        for(int i = 0; i < jsonArray.length();i++)
                        {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            cidlist = obj.getString("customer_id");
                            compidlist = obj.getString("customer_name");

                            companyIdList.add(cidlist);
                            customerNameList.add(compidlist);
                            Companylist.setEnabled(true);

                        }
                    Companylist.setTitle("Select customer");

                    Companylist.setAdapter(dataAdapter);


                    for (int i = 0; i < companyIdList.size();i++) {

                        if (StaticVariable.companyContact.equals(companyIdList.get(i))) {
                            Companylist.setSelection(i);
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
                Toast.makeText(OrderCreate.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(OrderCreate.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

//                Log.d("CKID","CKID"+StaticVariable.user_id);
//                Log.d("CKEMAIL","CKEMAIL"+StaticVariable.email);
//                Log.d("CKEBID","CKEBID"+StaticVariable.business_id);
//                Log.d("CKECID","CKECID"+StaticVariable.contact);
//                Log.d("CKEGID","CKEGID"+StaticVariable.groupid);





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



    private void getcontacts(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LCONT","LCONT"+s);


                contactnameIdlist.clear();
                ctnameIdlist.clear();


                contactnameIdlist.add("Select");
                ctnameIdlist.add("Select");


                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String contactnamelist = obj.getString("contact_name");
                        String contactidlist = obj.getString("contact_id");

                        contactnameIdlist.add(contactnamelist);
                        ctnameIdlist.add(contactidlist);
                    }

                    customerList.setAdapter(dataAdapter2);

                    for (int i = 0; i < contactnameIdlist.size();i++) {

                        if (StaticVariable.customerContactName.equals(ctnameIdlist.get(i))) {
                            customerList.setSelection(i);
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
                Toast.makeText(OrderCreate.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(OrderCreate.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

//                Log.d("CKID","CKID"+StaticVariable.user_id);
//                Log.d("CKEMAIL","CKEMAIL"+StaticVariable.email);
//                Log.d("CKEBID","CKEBID"+StaticVariable.business_id);
//                Log.d("CKECID","CKECID"+StaticVariable.contact);
//                Log.d("CKEGID","CKEGID"+StaticVariable.groupid);

                  Log.d("CONID","CONID"+StaticVariable.companyContact);



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
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) OrderCreate.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(OrderCreate.this, OrderDetails.class);
                intent.putExtra("redirection","Catalog");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                startActivity(intent);
                OrderCreate.this.finish();

                OrderCreate.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;


            case R.id.next_button:

                if(!Companylist.getSelectedItem().toString().equals("Select") && !customerList.getSelectedItem().toString().equals("Select"))
                {
                    nextPage();
                    return true;
                }
                else {
                    Toast.makeText(OrderCreate.this, getResources().getString(R.string.selectcusandcontact), Toast.LENGTH_LONG).show();
                }

            default:
                return true;
        }
    }

    public void nextPage() {


            Intent in = new Intent(OrderCreate.this, OrderAddress.class);
            in.putExtra("id",StaticVariable.groupid);
            in.putExtra("name",name);
            in.putExtra("redirection",redirection);
            in.putParcelableArrayListExtra("attributes",all_attributes);
            startActivity(in);
            OrderCreate.this.finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderCreate.this, OrderDetails.class);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        this.finish();
        startActivity(intent);
        OrderCreate.this.finish();

        OrderCreate.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return super.onCreateOptionsMenu(menu);
    }




}
