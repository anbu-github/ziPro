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
import com.google.gson.Gson;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.events.ValidateEvent;
import com.purplefront.jiro_dev.model.AttributeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by apple on 10/11/16.
 */

public class CustomAttr_Edit extends ActionBarActivity {

    String id="", name="", redirection="", order_id="",assign_username="",assign_userid="";
    Intent intent;
    ProgressDialog progress;
    private String tag_string_req = "string_req";
    String tag_string_req_recieve2 = "string_req_recieve2";
    ArrayList<AttributeModel> attlist;

    ArrayList<AttributeModel> attributes=new ArrayList<>();

    String obj_attrid,obj_master_id;

    ArrayList<String> objvaluelist = new ArrayList<>();

    ArrayList<String> objattidlist = new ArrayList<>();

    ArrayList<String> objmasidlist = new ArrayList<>();

    ArrayList<String> assignUserIdList = new ArrayList<>();

    ArrayList<String> assignUserNameList = new ArrayList<>();

    String useridlist,usernamelist;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Attr_List_Adaptor mAdapter;
    Spinner user_selection;

    ArrayAdapter<String>  dataAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.att1_view);
        processExtras();

        progress = new ProgressDialog(CustomAttr_Edit.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        order_id = intent.getStringExtra("order_id");
        redirection =  intent.getStringExtra("redirection");
        attributes =  intent.getParcelableArrayListExtra("attributes");

        assign_username = intent.getStringExtra("assign_to");
        assign_userid = intent.getStringExtra("assign_userid");




        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Edit Attributes" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);

        getUser(getResources().getString(R.string.url_reference) + "home/users_list.php");


        user_selection = (Spinner)findViewById(R.id.user_sel_spinner);


        dataAdapter2 = new ArrayAdapter<>(this,
                R.layout.spinner_layout, assignUserNameList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        user_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try
                {
                    StaticVariable.assignuser_id = assignUserIdList.get(position).toString();

                    StaticVariable.dispUserName = assignUserNameList.get(position).toString();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        mRecyclerView = (RecyclerView) findViewById(R.id.attr_recyview1);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(CustomAttr_Edit.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);






        mAdapter = new Attr_List_Adaptor(attributes, CustomAttr_Edit.this);
        mRecyclerView.setAdapter(mAdapter);


        for(int i =0 ; i< attributes.size();i++)
        {
             obj_attrid = attributes.get(i).getId();
             obj_master_id = attributes.get(i).getObj_master_id();

             objattidlist.add(obj_attrid);
              objmasidlist.add(obj_master_id);

        }





    }



    private void getUser(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("ASTOUSR","ASTOUSR"+s);

                assignUserIdList.clear();
                assignUserNameList.clear();

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        useridlist = obj.getString("id");
                        usernamelist = obj.getString("name");

                        assignUserIdList.add(useridlist);
                        assignUserNameList.add(usernamelist);
                        //user_selection.setEnabled(true);

                    }
                    user_selection.setAdapter(dataAdapter2);

                    for (int i = 0; i < assignUserIdList.size();i++) {

                        if (StaticVariable.assignuser_id.equals(assignUserIdList.get(i))) {
                            user_selection.setSelection(i);
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
                Toast.makeText(CustomAttr_Edit.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CustomAttr_Edit.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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
                params.put("customer_id",StaticVariable.companyContact);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


    private void processExtras() {
        if(null != getIntent()) {
            attributes =  getIntent().getExtras().getParcelableArrayList("attributes");
        }
    }



   public void display()
   {
       EventBus.getDefault().post(new ValidateEvent());

       String value = "";

       attributes = mAdapter.getAllAttributes();

       for(int i =0 ; i< attributes.size();i++)
       {
           value = attributes.get(i).getValue();
           objvaluelist.add(value);
           Log.v("AVAL1","AVAL1"+value);
       }



       editAttribute(getResources().getString(R.string.url_reference) + "home/save_custom_attributes.php");
   }


    private void editAttribute(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CUSDIS","CUSDIS"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CustomAttr_Edit.this);
                builder.setMessage(getResources().getString(R.string.attributesave))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CustomAttr_Edit.this, OrderDetails.class);
                                intent.putExtra("id",StaticVariable.groupid);
                                intent.putExtra("name",name);
                                CustomAttr_Edit.this.finish();
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
                Toast.makeText(CustomAttr_Edit.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CustomAttr_Edit.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Gson gson = new Gson();

                String obj_value = gson.toJson(objvaluelist);

                String obj_attrbid = gson.toJson(objattidlist);

                String obj_attmasid = gson.toJson(objmasidlist);

                Log.d("OAID","OAID"+obj_attrbid);

                params.put("obj_attrid",obj_attrbid);

                Log.d("OID","OID"+order_id);
                params.put("order_id",order_id);

                Log.d("OMID","OMID"+obj_attmasid);
                params.put("obj_master_id",obj_attmasid);

                Log.d("OBVAL","OBVAL"+obj_value);
                params.put("obj_values",obj_value);


                Log.d("IIID","IIID"+StaticVariable.user_id);
                params.put("user_id",StaticVariable.user_id);

                Log.d("OBID","OBID"+StaticVariable.business_id);
                params.put("business_id",StaticVariable.business_id);


                Log.d("CHGEUSR","CHGEUSR"+StaticVariable.assignuser_id);
                params.put("assigned_to",StaticVariable.assignuser_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }




//    private void editAttribute(String url)
//    {
//        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                progress.hide();
//                Log.d("ATTRES","ATTRES"+s);
//
//                attlist = AttributeView_JSONParser.parserFeed(s);
//
//                //display_data();
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                progress.hide();
//                Toast.makeText(CustomAttr_Edit.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
//                Toast.makeText(CustomAttr_Edit.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("id",StaticVariable.user_id);
//                params.put("object_id",StaticVariable.object_id);
//                params.put("email",StaticVariable.email);
//                params.put("business_id",StaticVariable.business_id);
//                params.put("contact",StaticVariable.contact);
//                params.put("group_id",StaticVariable.groupid);
//
//
//                return params;
//            }
//
//        };
//        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
//    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) CustomAttr_Edit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CustomAttr_Edit.this, OrderView.class);
                intent.putExtra("redirection","Catalog");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                intent.putExtra("order_id",order_id);
                startActivity(intent);
                CustomAttr_Edit.this.finish();
                CustomAttr_Edit.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;

            case R.id.save_button:
                display();
            default:
                return true;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomAttr_Edit.this, OrderView.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra("order_id",order_id);
        this.finish();
        startActivity(intent);
        CustomAttr_Edit.this.finish();
        CustomAttr_Edit.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
