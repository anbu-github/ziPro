package com.purplefront.jiro_dev;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 18/11/16.
 */

public class CreateCustomerContact extends ActionBarActivity{

    String id="", name="", redirection="",userid="",cust_id = "",email="",cust_segid="";
    String cus_cont_name="", cu_contact_email="", cu_contact_phone="", cu_contact_title;
    EditText et1,et2,et3,et4;
    Intent intent;
    Spinner spinner2;
    private String tag_string_req = "string_req";
    String titleidlist,titleNamelist;

    ArrayAdapter<String> dataAdapter;

    ArrayList<String> titleIdList = new ArrayList<>();
    ArrayList<String> titleNameList = new ArrayList<>();

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_cust_contact);

        intent = getIntent();


        userid = intent.getStringExtra("id");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        cust_segid = intent.getStringExtra("cust_segid");
        redirection = intent.getStringExtra("redirection");
        cust_id =  intent.getStringExtra("cust_id");


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Add Contact" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);


        gettitle(getResources().getString(R.string.url_reference) + "home/title_list.php");


        progress = new ProgressDialog(CreateCustomerContact.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();


        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, titleNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        et1 = (EditText) findViewById(R.id.create_cus_contactname);
        et2 = (EditText) findViewById(R.id.create_customer_phone);
        et3 = (EditText) findViewById(R.id.create_customer_email);

        spinner2 = (Spinner) findViewById(R.id.spinner2);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    StaticVariable.titleid = titleIdList.get(position).toString();
                    Log.v("customer_id", StaticVariable.companyContact);


                    StaticVariable.titlename = titleNameList.get(position).toString();

                    Log.d("CNAM","CNAM"+StaticVariable.dispcustomername);


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void gettitle(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);

                titleIdList.clear();
                titleNameList.clear();

                titleIdList.add("Select");
                titleNameList.add("Select");

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        titleidlist = obj.getString("id");
                        titleNamelist = obj.getString("name");

                        titleIdList.add(titleidlist);
                        titleNameList.add(titleNamelist);
                        spinner2.setEnabled(true);

                    }

                    spinner2.setAdapter(dataAdapter);


                    for (int i = 0; i < titleIdList.size();i++) {

                        if (StaticVariable.titleid.equals(titleIdList.get(i))) {
                            spinner2.setSelection(i);
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
                Toast.makeText(CreateCustomerContact.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCustomerContact.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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


    public void save() {
        cus_cont_name = et1.getText().toString();
        cu_contact_email = et3.getText().toString();
        cu_contact_phone = et2.getText().toString();


        if (cus_cont_name.equals("") || cus_cont_name.isEmpty()) {

            Toast.makeText(CreateCustomerContact.this, "Enter Customer Contact Name", Toast.LENGTH_SHORT).show();
        } else if (cu_contact_email.equals("") || cu_contact_email.isEmpty()) {
            Toast.makeText(CreateCustomerContact.this, "Enter Customer Contact Email", Toast.LENGTH_SHORT).show();
        }

        else if (cu_contact_phone.equals("") || cu_contact_phone.isEmpty()) {
            Toast.makeText(CreateCustomerContact.this, "Enter Customer Contact Phone", Toast.LENGTH_SHORT).show();

        }
        else {

            ordersubmitdata(getResources().getString(R.string.url_reference) + "home/customer_contact_create.php");

        }

    }


    private void ordersubmitdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CUCTRESP","CUCTRESP"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCustomerContact.this);
                builder.setMessage(getResources().getString(R.string.ordercustsuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                Intent intent = new Intent(CreateCustomerContact.this, ViewContact.class);
//                                intent.putExtra("name",name);
//                                intent.putExtra("email",email);
//                                intent.putExtra("cust_segid",cust_segid);
//                                intent.putExtra("id",userid);
//                                startActivity(intent);
//                                CreateCustomerContact.this.finish();
//                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                                 goBack();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateCustomerContact.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCustomerContact.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("CC1","CC1"+StaticVariable.user_id);
                Log.d("CC2","CC2"+StaticVariable.email);
                Log.d("CC3","CC3"+StaticVariable.contact);
                Log.d("CC4","CC4"+StaticVariable.business_id);
                Log.d("CC5","CC5"+cus_cont_name);
                Log.d("CC6","CC6"+cu_contact_phone);
                Log.d("CC7","CC7"+cu_contact_email);
                Log.d("CC8","CC8"+cust_id);
                Log.d("CC9","CC9"+StaticVariable.titleid);

                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("phone",StaticVariable.contact);
                params.put("business_id",StaticVariable.business_id);
                params.put("name",cus_cont_name);
                params.put("customer_contact",cu_contact_phone);
                params.put("customer_email",cu_contact_email);
                params.put("customer_id",StaticVariable.customer_segment_cust_id);
                params.put("title_id",StaticVariable.titleid);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    public void goBack(){
        Intent intent = new Intent(CreateCustomerContact.this, ViewContact.class);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("redirection",redirection);
        intent.putExtra("id", StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra("cust_segid",StaticVariable.customer_segment_id);
        intent.putExtra("cust_id",StaticVariable.customer_segment_cust_id);
        startActivity(intent);


        startActivity(intent);
        CreateCustomerContact.this.finish();
        CreateCustomerContact.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

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
