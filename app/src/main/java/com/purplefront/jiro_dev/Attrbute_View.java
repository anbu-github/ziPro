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
import com.purplefront.jiro_dev.events.ValidateEvent;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.parsers.AttributeView_JSONParser;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by apple on 04/11/16.
 */

public class Attrbute_View extends ActionBarActivity {

    String id = "",name = "",business_id = "",redirection = "";
    Intent intent;
    ProgressDialog progress;
    ArrayList<AttributeModel> feedlist;
    ArrayList<AttributeModel> all_attributes;
    ImageView add_customattribute;

    ArrayList<AttributeModel> attributes;
    Spinner user_selection;

    ArrayList<String> assignUserIdList = new ArrayList<>();

    ArrayList<String> assignUserNameList = new ArrayList<>();

    ArrayAdapter<String>  dataAdapter2;


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private Attr_List_Adaptor mAdapter;
    String tag_string_req_recieve2 = "string_req_recieve2";
    private Create_Address_Model mBilling,mShipping;
    String useridlist,usernamelist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attr_view);
        processExtras();


        progress = new ProgressDialog(Attrbute_View.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();


        try {


            intent = getIntent();
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            redirection = intent.getStringExtra("redirection");
            attributes = intent.getParcelableArrayListExtra("attributes");
        }catch (Exception e){
            e.printStackTrace();
            id = "";
            name = "";
            redirection = "";
        }

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Custom Attribute View" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("Catalog View");
        getSupportActionBar().setIcon(R.drawable.pf);


        add_customattribute = (ImageView)findViewById(R.id.add_custatt);


        getUser(getResources().getString(R.string.url_reference) + "home/users_list.php");


        assignUserIdList.add("Select");
        assignUserNameList.add("Select");


        user_selection = (Spinner) findViewById(R.id.user_sel_spinner);



        mRecyclerView = (RecyclerView) findViewById(R.id.attr_recyview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(Attrbute_View.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progress.show();


        add_customattribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Attrbute_View.this, Create_CustomAtt.class);
                in.putExtra("id",StaticVariable.groupid);
                in.putExtra("name",name);
                startActivity(in);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });



try {

    if (!redirection.equals("reviworder")) {
        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/custom_objects_list.php");

        } else {
            Toast.makeText(Attrbute_View.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }


    } else {
        progress.hide();

        mAdapter = new Attr_List_Adaptor(attributes, Attrbute_View.this);
        mRecyclerView.setAdapter(mAdapter);
    }

}catch (Exception e){
    getdata(getResources().getString(R.string.url_reference) + "home/custom_objects_list.php");

    e.printStackTrace();
}
        //progress.show();

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

                assignUserIdList.add("Select");
                assignUserNameList.add("Select");

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
                Toast.makeText(Attrbute_View.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Attrbute_View.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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








    private void display_data() {
        progress.show();
        if(feedlist != null) {

            mAdapter = new Attr_List_Adaptor(feedlist, Attrbute_View.this);
            mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }




    private void getdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("ATTRES","ATTRES"+s);

                feedlist = AttributeView_JSONParser.parserFeed(s);

                display_data();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(Attrbute_View.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Attrbute_View.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id",StaticVariable.user_id);
                params.put("object_id",StaticVariable.object_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent intent = new Intent(Attrbute_View.this, DisplayCat.class);
//                intent.putExtra("redirection","reviworder");
//                Log.d("BAKID","BAKID"+StaticVariable.groupid);
//                intent.putExtra("id",StaticVariable.groupid);
//                intent.putExtra("name",name);
//                intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
//                intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
//                intent.putParcelableArrayListExtra("attributes",mAdapter.getAllAttributes());
//                startActivity(intent);
//                Attrbute_View.this.finish();
//                Attrbute_View.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                goBack();
                return true;


            case R.id.next_button:

                if(!user_selection.getSelectedItem().toString().equals("Select"))
                {
                    nextPage();
                    return true;
                }

                else {
                    Toast.makeText(Attrbute_View.this, getResources().getString(R.string.selectassignuser), Toast.LENGTH_LONG).show();
                   }




            default:
                return true;
        }
    }


    public void goBack(){
        Intent intent = new Intent(Attrbute_View.this, DisplayCat.class);
        intent.putExtra("redirection","reviworder");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        intent.putParcelableArrayListExtra("attributes",mAdapter.getAllAttributes());
        startActivity(intent);
        Attrbute_View.this.finish();
        Attrbute_View.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) Attrbute_View.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
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
//        super.onBackPressed();
//        intent.putExtra("redirection","reviworder");
//        Log.d("BAKID","BAKID"+StaticVariable.groupid);
//        intent.putExtra("id",StaticVariable.groupid);
//        intent.putExtra("name",name);
//        intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
//        intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
//        intent.putParcelableArrayListExtra("attributes",all_attributes);
//        startActivity(intent);
//        Attrbute_View.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        goBack();
    }




    public void nextPage() {
        EventBus.getDefault().post(new ValidateEvent());

        Intent in = new Intent(Attrbute_View.this, ReviwOrder.class);
        in.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        in.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        in.putParcelableArrayListExtra("attributes",mAdapter.getAllAttributes());

        Log.v("testatr",mAdapter.getAllAttributes().get(0).getValue());
        in.putExtra("id",StaticVariable.groupid);
        in.putExtra("name",name);
        startActivity(in);
        Attrbute_View.this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_next, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
