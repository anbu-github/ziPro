package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Sub_Category_Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SubCategory extends ActionBarActivity implements Sub_Category_Adaptor.DataFromAdapterToActivity{

    String id = "",name = "",business_id = "",redirection = "";
    List<Sub_Category_Model> feedlist;
    Intent intent;
    ProgressDialog progress;
    Button catalog, members, customers, orders,visitbtn;
    CardView cardView,cardView2;
    int i;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String tag_string_req = "string_req";
    TextView selectmembers, selectcustomers,objorder,catlognames,nodata;
    ArrayList<String>objidlist = new ArrayList<>();
    ArrayList<String>objmasteridlist = new ArrayList<>();
    ArrayList<String>objnamelist = new ArrayList<>();
    ArrayList<String>objtypeidlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category);

        progress = new ProgressDialog(SubCategory.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();



        intent = getIntent();
        id = intent.getStringExtra("id");
        StaticVariable.groupid = id;
        name = intent.getStringExtra("name");
        business_id = intent.getStringExtra("business_id");
        redirection =  intent.getStringExtra("redirection");


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + name + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        cardView = (CardView)findViewById(R.id.cv_orderobj);
        cardView2 = (CardView)findViewById(R.id.cv_customer);
        catalog = (Button)findViewById(R.id.catbutton);
        members = (Button)findViewById(R.id.membersbtn);
        customers = (Button)findViewById(R.id.customersbtn);
        orders = (Button)findViewById(R.id.vieworders);
        nodata = (TextView)findViewById(R.id.nodata);
        visitbtn = (Button)findViewById(R.id.viewvisit);


        visitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Fragment test;
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                test = new GroupVisitActivity1();
//
//
//
//
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                setTitle("My Visit");

                Intent intent = new Intent(SubCategory.this,GroupVisitActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });


        catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SubCategory.this,CatalogView.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });


        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SubCategory.this,Memberslist.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                //SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });


        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StaticVariable.cusobj_id = "3";
                StaticVariable.cus_seg_objectid = "10";
                Intent intent = new Intent(SubCategory.this,Customerlist.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });


        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StaticVariable.object_id = "1";
                Intent intent = new Intent(SubCategory.this,OrderDetails.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);



            }
        });


        selectmembers = (TextView) findViewById(R.id.selectmembers);

        objorder = (TextView)findViewById(R.id.obj);


        objorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubCategory.this,OrderDetails.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });

        catlognames = (TextView)findViewById(R.id.textView20);

        selectmembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SubCategory.this,Memberslist.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });


        selectcustomers = (TextView)findViewById(R.id.selectcustomers);

        selectcustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubCategory.this,Customerlist.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                SubCategory.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });

        mLayoutManager = new GridLayoutManager(SubCategory.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progress.show();
        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/product_catalogs.php");
        } else {
            Toast.makeText(SubCategory.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }
    }

//    private void display_data()
//    {
//        progress.show();
//        if(feedlist != null) {
//
//            if(!feedlist.get(0).getName().equals("No Data"))
//            {
//
//                Log.d("GEIDD","GEIDD");
//                mAdapter = new Sub_Category_Adaptor(feedlist, SubCategory.this);
//                mRecyclerView.setAdapter(mAdapter);
//            }
//            else {
//                nodata.setVisibility(View.VISIBLE);
//            }
//
//        }
//        progress.hide();
//    }



    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("PROCAT","PROCAT"+s);

                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    String jsonArray1 = jsonObject1.getString("objects_data");

                    JSONArray ar=new JSONArray(jsonArray1);
                    for(int i = 0; i < ar.length();i++)
                    {
                        JSONObject obj = ar.getJSONObject(i);

                        String order_id = obj.getString("object_master_id");
                        String obj_name = obj.getString("name");
                        String obj_id = obj.getString("object_id");
                        String object_type_list_id = obj.getString("object_type_list_id");


                        objidlist.add(obj_id);
                        objmasteridlist.add(order_id);
                        objnamelist.add(obj_name);
                        objtypeidlist.add(object_type_list_id);



                        Log.d("NNNM1","NNNM1"+obj_id);

                        Log.d("NNNM2","NNNM2"+objidlist.size());


                        if(order_id.equals("1"))
                        {
                            orders.setVisibility(View.VISIBLE);
                            orders.setText(obj_name);
                            //objorder.setText(obj_name);
                        }
//                        else if(order_id.equals("2"))
//                        {
//                            mRecyclerView.setVisibility(View.VISIBLE);
//                            catlognames.setText(obj_name);
//
//
//                        }

                        else if(order_id.equals("3") || order_id.equals("10"))
                        {
                            customers.setVisibility(View.VISIBLE);
                            customers.setText(obj_name);

                        }

                        else if(order_id.equals("6"))
                        {
                            visitbtn.setVisibility(View.VISIBLE);
                            visitbtn.setText(obj_name);
                        }



                    }




                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }





                //feedlist = SubCategory_JSONParser.parserFeed(s);
                //display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(SubCategory.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(SubCategory.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",StaticVariable.user_id);
                params.put("business_id",StaticVariable.business_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SubCategory.this, MainActivity.class);
                intent.putExtra("redirection","Catalog");
                SubCategory.this.finish();
                this.finish();
                startActivity(intent);
                SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubCategory.this, MainActivity.class);
        intent.putExtra("redirection","Catalog");
        SubCategory.this.finish();
        this.finish();
        startActivity(intent);
        SubCategory.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) SubCategory.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public void subCategoryId(String subCategory_name, String sub_id) {
        Log.d("SCATID","SCATID"+sub_id);
        Intent intent = new Intent(SubCategory.this, Product_list.class);
        intent.putExtra("category_id", id);
        intent.putExtra("category_name", name);
        intent.putExtra("subcategory_id", sub_id);

        Log.d("SUBCAT","SUBCAT"+subCategory_name);
        intent.putExtra("subcategory_name", subCategory_name);
        intent.putExtra("business_id", StaticVariable.business_id);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


}
