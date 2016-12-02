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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.adapter.CustomAttrDisplayAdapter;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.model.Order_Detail_Model;
import com.purplefront.jiro_dev.parsers.CustomAttribute_JSONParser;
import com.purplefront.jiro_dev.parsers.OrderDetails_JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 08/11/16.
 */

public class OrderView extends ActionBarActivity {

    String id="", name="", redirection="", order_id = "";
    Intent intent;
    ProgressDialog progress;

    private String tag_string_req = "string_req";

    String tag_string_req_recieve2 = "string_req_recieve2";

    String obj_masterid;

    String assign_username, assign_userid;



    private RecyclerView recyclerView,recycler_view2;
    StaggeredGridLayoutManager mLayoutManager,mLayoutManager2;

    List<Order_Detail_Model> feedlist;

    ArrayList<AttributeModel> attlist;

    OrderList_View_Adaptor mAdapter;
    CustomAttrDisplayAdapter mAdapter1;

    private Attr_List_Adaptor mAdapter2;

    ArrayList<AttributeModel> attributes=new ArrayList<>();


    TextView cusname, custcontact, assignto, cataname, shadd1,shadd2,shadd3, shcity,shstate,shpincode, billadd1,billadd2,billadd3,billcity,billstate,billzipcode, pro_totalamt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);


        cusname = (TextView)findViewById(R.id.disp_cusname);
        custcontact = (TextView)findViewById(R.id.disp_contactname);
        assignto = (TextView)findViewById(R.id.disp_assignto);
        cataname = (TextView)findViewById(R.id.disp_catalogname);


        shadd1 = (TextView)findViewById(R.id.add1);
        shadd2 = (TextView)findViewById(R.id.add3);
        shadd3 = (TextView)findViewById(R.id.address_line3);
        shcity = (TextView)findViewById(R.id.add2);
        shstate = (TextView)findViewById(R.id.state);
        shpincode = (TextView)findViewById(R.id.zipcode);
        pro_totalamt = (TextView)findViewById(R.id.pro_totalamt);


        billadd1 = (TextView)findViewById(R.id.badd1);
        billadd2 = (TextView)findViewById(R.id.badd3);
        billadd3 = (TextView)findViewById(R.id.baddress_line3);
        billcity = (TextView)findViewById(R.id.badd2);
        billstate = (TextView)findViewById(R.id.bstate);
        billzipcode = (TextView)findViewById(R.id.bzipcode);


        progress = new ProgressDialog(OrderView.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);


        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        order_id = intent.getStringExtra("order_id");
        redirection =  intent.getStringExtra("redirection");


        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/master_orders_details.php");

        } else {
            Toast.makeText(OrderView.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_oview1);
        recycler_view2 = (RecyclerView) findViewById(R.id.recycler_oview2);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager2.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recycler_view2.setLayoutManager(mLayoutManager2);






        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Order Details" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);


        //recycler_view2.setAdapter(new CustomAttrDisplayAdapter(attributes,OrderView.this));


    }





    private void getdata(String url) {
        progress.show();
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("ORDDETAILS","ORDDETAILS"+s);

                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    String jsonArray1 = jsonObject.getString("basic_details");
                    JSONArray ar= new JSONArray(jsonArray1);
                    for(int i = 0; i < ar.length();i++)
                    {
                        JSONObject obj = ar.getJSONObject(i);


                        String customer_name = obj.getString("customer_name");
                        cusname.setText(customer_name);


//                        if(!obj.getString("address_line1").equals(""))
//                        {
//                            String shipp_add1 = obj.getString("address_line1");
//                            String shipp_add2 = obj.getString("address_line2");
//                            String shipp_add3 = obj.getString("address_line3");
//                            shadd1.setText(shipp_add1 + ","+ shipp_add2 + ","+shipp_add3);
//                        }
//                        else {
//                            shadd1.setVisibility(View.GONE);
//                        }



                        String shipp_add1 = obj.getString("address_line1");
                        String shipp_add2 = obj.getString("address_line2");
                        String shipp_add3 = obj.getString("address_line3");
                        shadd1.setText(shipp_add1 + " , "+ shipp_add2 + " , "+shipp_add3);


                        String shipp_city = obj.getString("city");
                        String shipp_state = obj.getString("state");
                        String shipp_pincode = obj.getString("zipcode");
                        shadd2.setText(shipp_city + " , "+shipp_state + " , "+shipp_pincode);


//                        if(!obj.getString("address_line2").equals(""))
//                        {
//                            String shipp_add2 = obj.getString("address_line2");
//                            shadd2.setText(shipp_add2);
//                        }
//                        else {
//                            shadd2.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("address_line3").equals(""))
//                        {
//                            String shipp_add3 = obj.getString("address_line3");
//                            shadd3.setText(shipp_add3);
//                        }
//
//                        else
//                        {
//                            shadd3.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("city").equals(""))
//                        {
//                            String shipp_city = obj.getString("city");
//                            shcity.setText(shipp_city);
//                        }
//                        else {
//                            shcity.setVisibility(View.GONE);
//                        }
//
//
//                        if(!obj.getString("state").equals(""))
//                        {
//                            String shipp_state = obj.getString("state");
//                            shstate.setText(shipp_state);
//                        }
//                        else {
//                            shstate.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("zipcode").equals(""))
//                        {
//                            String shipp_pincode = obj.getString("zipcode");
//                            shpincode.setText(shipp_pincode);
//                        }
//                        else {
//                            shpincode.setVisibility(View.GONE);
//                        }


                        String billing_add1 = obj.getString("billing_address_line1");
                        String billing_add2 = obj.getString("billing_address_line2");
                        String billing_add3 = obj.getString("billing_address_line3");

                        billadd1.setText(billing_add1 + " , "+billing_add2 + ", "+billing_add3);


                        String bill_city = obj.getString("billing_city");
                        String bill_state = obj.getString("billing_state");
                        String bill_pincode = obj.getString("billing_zipcode");

                        billadd2.setText(bill_city+ " , " +bill_state + " , " +bill_pincode);




//                        if(!obj.getString("billing_address_line1").equals(""))
//                        {
//                            String billing_add1 = obj.getString("billing_address_line1");
//                            billadd1.setText(billing_add1);
//                        }else {
//                            billadd1.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("billing_address_line2").equals(""))
//                        {
//                            String billing_add2 = obj.getString("billing_address_line2");
//                            billadd2.setText(billing_add2);
//                        }
//                        else {
//                            billadd2.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("billing_address_line3").equals(""))
//                        {
//                            String billing_add3 = obj.getString("billing_address_line3");
//                            billadd3.setText(billing_add3);
//                        }
//                        else {
//                            billadd3.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("billing_city").equals(""))
//                        {
//                            String bill_city = obj.getString("billing_city");
//                            billcity.setText(bill_city);
//                        }
//                        else {
//                            billcity.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("billing_state").equals(""))
//                        {
//                            String bill_state = obj.getString("billing_state");
//                            billstate.setText(bill_state);
//                        }
//                        else {
//                            billstate.setVisibility(View.GONE);
//                        }
//
//                        if(!obj.getString("billing_zipcode").equals(""))
//                        {
//                            String bill_pincode = obj.getString("billing_zipcode");
//                            billzipcode.setText(bill_pincode);
//                        }
//                        else {
//                            billzipcode.setVisibility(View.GONE);
//                        }

                        String sub_total = obj.getString("order_total");

                        if(StaticVariable.currencyName.equals("Rupee"))
                        {
                            pro_totalamt.setText("₹"+sub_total);
                        }
                        else if(StaticVariable.currencyName.equals("Dollar"))
                        {
                            pro_totalamt.setText("$"+sub_total);
                        }
                        else {
                            pro_totalamt.setText("₹"+sub_total);
                        }



                        String pro_catname = obj.getString("catalog_name");
                        cataname.setText(pro_catname);

                        String cus_contactname = obj.getString("contact_name");
                        custcontact.setText(cus_contactname);

                        assign_username = obj.getString("assigned_user");
                        assignto.setText(assign_username);

                        assign_userid = obj.getString("assigned_user_id");
                        StaticVariable.assignuser_id = assign_userid;






                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String jsonArray1 = jsonObject.getString("orders");

                    Log.d("ORDLST","ORDLST"+jsonArray1);

                    feedlist = OrderDetails_JSONParser.parserFeed(jsonArray1);

                    Log.d("PRFELIST","PRFELIST"+feedlist.size());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                mAdapter = new OrderList_View_Adaptor(feedlist,OrderView.this);

                recyclerView.setAdapter(mAdapter);


                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    String jsonArray1 = jsonObject.getString("custom_attributes");
                    Log.d("CUSABT","CUSABT"+jsonArray1);

                    attlist = CustomAttribute_JSONParser.parserFeed(jsonArray1);

                    Log.d("CUATLST","CUATLST"+attlist.size());


                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                mAdapter1 = new CustomAttrDisplayAdapter(attlist,OrderView.this);

                recycler_view2.setAdapter(mAdapter1);

                //recycler_view2.setAdapter(new CustomAttrDisplayAdapter(attlist,OrderView.this));

                mAdapter2 = new Attr_List_Adaptor(attlist, OrderView.this);


                progress.hide();
                 //display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(OrderView.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
                //Toast.makeText(Product_list.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("order_id",order_id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) OrderView.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderView.this, OrderDetails.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);

        this.finish();
        startActivity(intent);
        OrderView.this.finish();

        OrderView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(OrderView.this, OrderDetails.class);
                intent.putExtra("redirection","Catalog");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                startActivity(intent);
                OrderView.this.finish();
                OrderView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;

            case R.id.edit:
                Intent intent1 = new Intent(OrderView.this, CustomAttr_Edit.class);
                intent1.putExtra("id",StaticVariable.groupid);
                intent1.putExtra("name",name);
                intent1.putExtra("order_id",order_id);
                intent1.putExtra("assign_to",assign_username);
                intent1.putExtra("assign_userid",assign_userid);
                intent1.putParcelableArrayListExtra("attributes",mAdapter2.getAllAttributes());
                startActivity(intent1);
                OrderView.this.finish();
                OrderView.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return true;

            default:
                return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.viewedit, menu);


        return super.onCreateOptionsMenu(menu);
    }

}
