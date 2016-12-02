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
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerContact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 18/11/16.
 */

public class CreateCust_Address extends ActionBarActivity {

    String contid="", name="", redirection="", cust_segid="";
    EditText address1, address2, address3, city, pincode;
    String cu_add1="", cu_add2="", cu_add3="", cu_city="", cu_state, cu_pincode, cu_country;
    Intent intent;
    List<CustomerContact> feedlist;
    ProgressDialog progress;
    Spinner stateSpinner,countrySpinner;
    String countryidlist,countrynamelist,customerName;
    ArrayAdapter<String> dataAdapter, dataAdapter2;
    ArrayList<String> countryIdList = new ArrayList<>();
    ArrayList<String> countryNameList = new ArrayList<>();
    ArrayList<String> stateIdList = new ArrayList<>();
    ArrayList<String> stateNameList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_address);

        intent = getIntent();
        contid = intent.getStringExtra("cust_id");
        name = intent.getStringExtra("name");
        cust_segid = intent.getStringExtra("id");
        redirection =  intent.getStringExtra("redirection");

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Create Address" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);


        progress = new ProgressDialog(CreateCust_Address.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();


        address1 = (EditText)findViewById(R.id.address1);
        address2 = (EditText)findViewById(R.id.address2);
        address3 = (EditText)findViewById(R.id.address3);
        city = (EditText)findViewById(R.id.add_city);
        pincode = (EditText)findViewById(R.id.add_pincode);
        stateSpinner = (Spinner)findViewById(R.id.state_spin);
        countrySpinner = (Spinner)findViewById(R.id.country_spin);


        progress.show();
        if (isonline()) {
            getCountry(getResources().getString(R.string.url_reference) + "home/country_list.php");

        } else {
            Toast.makeText(CreateCust_Address.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }



        dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, countryNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        dataAdapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_layout, stateNameList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    StaticVariable.countryid = countryIdList.get(position).toString();
                    Log.v("customer_id", StaticVariable.companyContact);


                    StaticVariable.countryname = countryNameList.get(position).toString();

                    Log.d("CNAM","CNAM"+StaticVariable.dispcustomername);




                } catch (Exception e) {

                    e.printStackTrace();
                }


                getstate(getResources().getString(R.string.url_reference) + "home/state_list.php");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    StaticVariable.stateid = stateIdList.get(position).toString();
                    Log.v("contact_id", StaticVariable.customerContactName);


                    StaticVariable.statename = stateNameList.get(position).toString();



                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getCountry(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CTYRES","CTYRES"+s);

                countryIdList.clear();
                countryNameList.clear();

                countryIdList.add("Select");
                countryNameList.add("Select");

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        countryidlist = obj.getString("id");
                        countrynamelist = obj.getString("name");

                        countryIdList.add(countryidlist);
                        countryNameList.add(countrynamelist);
                        countrySpinner.setEnabled(true);

                    }

                    countrySpinner.setAdapter(dataAdapter);


                    for (int i = 0; i < countryIdList.size();i++) {

                        if (StaticVariable.countryid.equals(countryIdList.get(i))) {
                            countrySpinner.setSelection(i);
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
                Toast.makeText(CreateCust_Address.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCust_Address.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }




    private void getstate(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LCONT","LCONT"+s);


                stateIdList.clear();
                stateNameList.clear();


                stateIdList.add("Select");
                stateNameList.add("Select");


                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String contactnamelist = obj.getString("id");
                        String contactidlist = obj.getString("name");

                        stateIdList.add(contactnamelist);
                        stateNameList.add(contactidlist);
                    }

                    stateSpinner.setAdapter(dataAdapter2);

                    for (int i = 0; i < stateIdList.size();i++) {

                        if (StaticVariable.stateid.equals(stateIdList.get(i))) {
                            stateSpinner.setSelection(i);
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
                Toast.makeText(CreateCust_Address.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCust_Address.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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




    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) CreateCust_Address.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    public void save() {
        cu_add1 = address1.getText().toString();
        cu_add2 = address2.getText().toString();
        cu_add3 = address3.getText().toString();
        cu_city = city.getText().toString();
        cu_pincode = pincode.getText().toString();


        if (cu_add1.equals("") || cu_add1.isEmpty()) {

            Toast.makeText(CreateCust_Address.this, "Enter Customer Address", Toast.LENGTH_SHORT).show();
        } else if (cu_city.equals("") || cu_city.isEmpty()) {
            Toast.makeText(CreateCust_Address.this, "Enter Customer City", Toast.LENGTH_SHORT).show();
        }
        else if (cu_pincode.equals("") || cu_pincode.isEmpty()) {
            Toast.makeText(CreateCust_Address.this, "Enter Customer Pincode", Toast.LENGTH_SHORT).show();

        }
        else {

            ordersubmitdata(getResources().getString(R.string.url_reference) + "home/company_address_create.php");

        }

    }


    private void ordersubmitdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("ADDRSUBRES","ADDRSUBRES"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCust_Address.this);
                builder.setMessage(getResources().getString(R.string.ordercustsuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CreateCust_Address.this, CustMembersProfileView.class);
                                intent.putExtra("name",name);
                                intent.putExtra("cust_segid",cust_segid);
                                intent.putExtra("cust_id",contid);
                                startActivity(intent);
                                CreateCust_Address.this.finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateCust_Address.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCust_Address.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("C1","C1"+StaticVariable.user_id);

                params.put("id",StaticVariable.user_id);

                Log.d("C2","C2"+StaticVariable.email);

                params.put("email",StaticVariable.email);

                Log.d("C3","C3"+StaticVariable.business_id);

                params.put("business_id",StaticVariable.business_id);

                Log.d("C4","C4"+cu_add1);
                params.put("address_lin1",cu_add1);

                Log.d("C5","C5"+cu_add2);
                params.put("address_lin2",cu_add2);

                Log.d("C6","C6"+cu_add3);
                params.put("address_lin3",cu_add3);

                Log.d("C7","C7"+cu_city);
                params.put("city",cu_city);


                Log.d("C8","C8"+cu_pincode);
                params.put("zip",cu_pincode);


                Log.d("C9","C9"+StaticVariable.stateid);
                params.put("state_id",StaticVariable.stateid);

                Log.d("C10","C10"+StaticVariable.countryid);
                params.put("country_id",StaticVariable.countryid);

                Log.d("C11","C11"+contid);
                params.put("customer_id",contid);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    public void goBack(){
        Intent intent = new Intent(CreateCust_Address.this, CustMembersProfileView.class);
        intent.putExtra("id", StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra("cust_segid",cust_segid);
        intent.putExtra("cust_id",contid);
        startActivity(intent);
        CreateCust_Address.this.finish();
        CreateCust_Address.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
            case R.id.submit:
                save();
            default:
                return true;
        }
    }


    @Override
    public void onBackPressed() {
        goBack();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
