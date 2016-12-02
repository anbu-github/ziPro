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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.purplefront.jiro_dev.adapter.CustomAttrDisplayAdapter;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.model.Product_List_Model;
import com.purplefront.jiro_dev.model.TaxTypeModel;
import com.purplefront.jiro_dev.parsers.CatProdList_JSONParser;
import com.purplefront.jiro_dev.parsers.TaxList_JSONParser;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.purplefront.jiro_dev.model.Create_Address_Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 28/10/16.
 */

public class ReviwOrder extends ActionBarActivity implements Tax_Section_Adaptor.DataFromAdapterToActivity {

    String id = "",name = "",business_id = "",redirection = "";
    Intent intent;
    ProgressDialog progress;
    Integer totalamt = 0;
    List<Product_List_Model> feedlist;

    List<TaxTypeModel> taxlist;

    Float subto = 0.00F;


    private String tag_string_req = "string_req";
    ArrayList<String> idprodlist = new ArrayList<>();
    ArrayList<String> priceprodlist = new ArrayList<>();
    ArrayList<String> qtyprodlist = new ArrayList<>();
    ArrayList<String> finalpriceplist = new ArrayList<>();
    private RelativeLayout mBillinglayout, mShippinglayout;
    private Create_Address_Model mBilling,mShipping;

    ArrayList<AttributeModel> attributes,all_attributes;

    String taxgetidlist,taxgetnamelist;

    ArrayAdapter<String> dataAdapter;

    ArrayList<String> attrid = new ArrayList<>();
    ArrayList<String> attrname = new ArrayList<>();
    ArrayList<String> attrvalue = new ArrayList<>();
    Spinner tax_sel_spinner;

    ArrayList<String> taxIdList = new ArrayList<>();

    ArrayList<String> taxNameList = new ArrayList<>();


    TextView tlamt, cusname, tlamtwithtax, cuscontact,assignto,shipadd1,shipadd2,shipadd3,shipcity,shipstate,shippin,catname,billadd1,billadd2,billadd3,billcity,billstate,billpin;

    Button sub_order;

    private RecyclerView recyclerView,recycler_view2;
    private ExpandableListView taxrecycler_view;
    StaggeredGridLayoutManager mLayoutManager,mLayoutManager2, mLayoutManager3;
    CatPro_List_Adaptor1 mAdapter;

    Tax_Section_Adaptor taxAdapter;

    CustomAttrDisplayAdapter mAdapter1;

    private Attr_List_Adaptor mAdapter3;


    String tag_string_req_recieve2 = "string_req_recieve2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_order1);
        processExtras();


        cusname = (TextView)findViewById(R.id.disp_cusname);

        cuscontact = (TextView)findViewById(R.id.disp_cuscontact);

        assignto = (TextView)findViewById(R.id.disp_assignto);

        mBillinglayout = (RelativeLayout)findViewById(R.id.billingadd_layout);

        mShippinglayout = (RelativeLayout)findViewById(R.id.shipaddress_layout);

        updateAddressView(mBillinglayout , mBilling);
        updateAddressView(mShippinglayout,mShipping);

        catname = (TextView)findViewById(R.id.disp_cat);

        tlamt = (TextView)findViewById(R.id.tlamt);

        tlamtwithtax = (TextView)findViewById(R.id.tlamtwithtax);

        tax_sel_spinner = (Spinner)findViewById(R.id.tax_sel_spinner);

        gettax(getResources().getString(R.string.url_reference) + "home/tax_group_list.php");


        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, taxNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tax_sel_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try
                {
                    StaticVariable.tax_id = taxIdList.get(position).toString();
                    Log.v("cata_id","cata_id"+StaticVariable.catalog_id);

                    StaticVariable.taxName = taxNameList.get(position).toString();

                    Log.d("CANAME","CANAME"+StaticVariable.dispCataName);

                    if(!tax_sel_spinner.getSelectedItem().toString().equals("Select"))
                    {
                        gettaxtype(getResources().getString(R.string.url_reference) + "home/tax_type_list.php");

                        taxrecycler_view.setVisibility(View.VISIBLE);

                    }
                    else {
                        if(StaticVariable.currencyName.equals("Rupee"))
                        {
                            tlamtwithtax.setText(String.valueOf("₹"+totalamt));
                        }
                        else if(StaticVariable.currencyName.equals("Dollar"))
                        {
                            tlamtwithtax.setText(String.valueOf("$"+totalamt));
                        }
                        else {
                            tlamtwithtax.setText(String.valueOf("₹"+totalamt));
                        }

                        taxrecycler_view.setVisibility(View.GONE);
                    }


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



        cusname.setText(StaticVariable.dispcustomername);
        cuscontact.setText(StaticVariable.dispContactName);
        assignto.setText(StaticVariable.dispUserName);


        catname.setText(StaticVariable.dispCataName);

        progress = new ProgressDialog(ReviwOrder.this);
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
        attributes =  intent.getParcelableArrayListExtra("attributes");


        if (isonline()) {
            getdata(getResources().getString(R.string.url_reference) + "home/products_list.php");




        } else {
            Toast.makeText(ReviwOrder.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recycler_view2 = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager2.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recycler_view2.setLayoutManager(mLayoutManager2);



        taxrecycler_view = (ExpandableListView) findViewById(R.id.recycler_tax);



        mAdapter = new CatPro_List_Adaptor1();

        recyclerView.setAdapter(mAdapter);

        progress.show();

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Review Order" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);


        Log.d("ASIZE","ASIZE"+attributes.size());

//        Integer pos=0;
//        for (AttributeModel value:attributes){
//
//            pos++;
//            String svalue=value.getValue();
//
//
//            if (svalue.equals("")){
//                attributes.remove(pos);
//            }
//
//        }


//        for (Iterator<AttributeModel> iterator = attributes.iterator(); iterator.hasNext();) {
//            AttributeModel model = iterator.next();
//
//
//            if (model.getValue().isEmpty()) {
//                // Remove the current element from the iterator and the list.
//
//                iterator.remove();
//            }
//        }




        for(int i = 0 ; i < attributes.size();i++) {
            String id = attributes.get(i).getId();
            String name = attributes.get(i).getName();
            String value = attributes.get(i).getValue();


            attrid.add(id);
            attrname.add(name);
            attrvalue.add(value);

        }
//
//
//             Log.d("AID","AID"+id);
//             Log.d("ANAME","ANAME"+name);
//             Log.d("AVAL","AVAL"+value);
//
//
//             if (value.length() < 1){
//                 attributes.remove(i);
//
//             }
//            else if (TextUtils.isEmpty(value)){
//                 attributes.remove(i);
//
//
//             }
//             else if (value.equals("")){
//                 attributes.remove(i);
//
//             }
//
//         }

        recycler_view2.setAdapter(new CustomAttrDisplayAdapter(attributes,ReviwOrder.this));

    }


    private void display_data1()
    {
        progress.show();
        if(taxlist != null) {

            Log.d("GEIDD","GEIDD");
            taxAdapter = new Tax_Section_Adaptor( ReviwOrder.this,taxlist);
            taxrecycler_view.setAdapter(taxAdapter);
            Float tot=StaticVariable.net_amount+ StaticVariable.total_amt;
            //tlamtwithtax.setText(tot+"");
        }
        progress.hide();
    }


    private void gettaxtype(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("TAXRESTYPE","TAXRESTYPE"+s);

                taxlist = TaxList_JSONParser.parserFeed(s);


                //Log.d("TAXSIZE","TAXSIZE"+taxlist.size());

                display_data1();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(ReviwOrder.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(ReviwOrder.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("TAXUID","TAXUID"+StaticVariable.user_id);
                Log.d("TAXEMAIL","TAXEMAIL"+StaticVariable.email);
                Log.d("TAXBID","TAXBID"+StaticVariable.business_id);
                Log.d("TAXCT","TAXCT"+StaticVariable.contact);
                Log.d("TAXGID","TAXGID"+StaticVariable.groupid);
                Log.d("TAXRID","TAXRID"+StaticVariable.role_id);

                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("role_id",StaticVariable.role_id);
                params.put("tax_header_id",StaticVariable.tax_id);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }











    private void gettax(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("TAXRES","TAXRES"+s);

                taxIdList.clear();
                taxNameList.clear();


                taxIdList.add("Select");
                taxNameList.add("Select");

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        taxgetidlist = obj.getString("tax_group_id");
                        taxgetnamelist = obj.getString("tax_name");

                        taxIdList.add(taxgetidlist);
                        taxNameList.add(taxgetnamelist);

                    }

                    tax_sel_spinner.setAdapter(dataAdapter);

                    for (int i = 0; i < taxIdList.size();i++) {

                        if (StaticVariable.tax_id.equals(taxIdList.get(i))) {
                            tax_sel_spinner.setSelection(i);
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
                Toast.makeText(ReviwOrder.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(ReviwOrder.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("TAXUID","TAXUID"+StaticVariable.user_id);
                Log.d("TAXEMAIL","TAXEMAIL"+StaticVariable.email);
                Log.d("TAXBID","TAXBID"+StaticVariable.business_id);
                Log.d("TAXCT","TAXCT"+StaticVariable.contact);
                Log.d("TAXGID","TAXGID"+StaticVariable.groupid);
                Log.d("TAXRID","TAXRID"+StaticVariable.role_id);

                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("role_id",StaticVariable.role_id);


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


    private void updateAddressView(ViewGroup viewGroup, Create_Address_Model address)
    {
        View v = LayoutInflater.from(ReviwOrder.this).inflate(R.layout.select_address_adpter, viewGroup, false);
        AddressViewHolder addressViewHolder = new AddressViewHolder(v);
        addressViewHolder.bindData(address,0,false);
        viewGroup
                .addView(addressViewHolder.itemView);

    }


    private void ordersubmitdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(ReviwOrder.this);
                builder.setMessage(getResources().getString(R.string.ordercreatesuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(ReviwOrder.this, OrderDetails.class);
                                intent.putExtra("id",StaticVariable.groupid);
                                intent.putExtra("name",name);
                                ReviwOrder.this.finish();
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
                Toast.makeText(ReviwOrder.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(ReviwOrder.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Gson gson = new Gson();


                String prodid = gson.toJson(idprodlist);
                String proprice = gson.toJson(finalpriceplist);
                String proqty = gson.toJson(StaticVariable.qtylist);

                String attbid = gson.toJson(attrid);
                String attbname = gson.toJson(attrname);
                String attbvalue = gson.toJson(attrvalue);


                Log.d("RV1","RV1"+StaticVariable.companyContact);
                params.put("customer_id", StaticVariable.companyContact);

                Log.d("RV2","RV2"+StaticVariable.dispcustomername);
                params.put("customer_name",StaticVariable.dispcustomername);

                Log.d("RV3","RV3"+StaticVariable.customerContactName);
                params.put("contact_id", StaticVariable.customerContactName);


                Log.d("RV4","RV4"+StaticVariable.dispContactName);
                params.put("contact_name",StaticVariable.dispContactName);


                Log.d("RV5","RV5"+StaticVariable.catalog_id);
                params.put("catalog_id",StaticVariable.catalog_id);


                Log.d("RV6","RV6"+StaticVariable.dispCataName);
                params.put("catalog_name",StaticVariable.dispCataName);

                Log.d("RV7","RV7"+StaticVariable.shipId);
                params.put("address_id",mShipping.getId());

                params.put("shipadd1",mShipping.getAddressline1());
                params.put("shipadd2",mShipping.getAddressline2());
                params.put("shipadd3",mShipping.getAddressline3());
                params.put("shipcity",mShipping.getCity());
                params.put("shipstate",mShipping.getState());
                params.put("shipstateid",mShipping.getStateid());
                params.put("shippincode",mShipping.getPincode());

                params.put("billing_addressid",mBilling.getId());

                params.put("billadd1",mBilling.getAddressline1());
                params.put("billadd2",mBilling.getAddressline2());
                params.put("billadd3",mBilling.getAddressline3());
                params.put("billcity",mBilling.getCity());
                params.put("billstate",mBilling.getState());
                params.put("billpincode",mBilling.getPincode());
                params.put("billstateid",mBilling.getStateid());


                Log.d("CURIDDD","CURIDDD"+StaticVariable.currency_id);

                params.put("currency_id",StaticVariable.currency_id);

                Log.d("RV8","RV8"+prodid);
                params.put("product_id",prodid);

                Log.d("RV9","RV9"+proprice);
                params.put("product_price",proprice);


                Log.d("RV10","RV10"+StaticVariable.qtylist);
                params.put("product_qty",proqty);


                params.put("id",StaticVariable.user_id);

                Log.d("ATTID","ATTID"+attbid);

                params.put("attr_id",attbid);

                Log.d("ATTNAME","ATTNAME"+attbname);
                params.put("attr_name",attbname);

                Log.d("ATTVAL","ATTVAL"+attbvalue);
                params.put("attr_value",attbvalue);

                Log.d("OBID","OBID"+StaticVariable.object_id);
                params.put("object_id", StaticVariable.object_id);

               // Log.d("TAMT","TAMT"+String.valueOf(round(subto,2)));

               // Log.d("TAMT","TAMT"+String.valueOf(Math.round("%.2f",subto)));

                //params.put("total_amount",String.valueOf(Math.round(subto)));

                params.put("total_amount",String.valueOf(subto));


                params.put("business_id",StaticVariable.business_id);



                Log.d("RGID","RGID"+StaticVariable.groupid);
                params.put("group_id",StaticVariable.groupid);


                Log.d("AUID","AUID"+StaticVariable.assignuser_id);
                params.put("assign_userid",StaticVariable.assignuser_id);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
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
                Toast.makeText(ReviwOrder.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
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

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


    private void display_data() {
        progress.show();
        if (feedlist != null) {

            for (Product_List_Model product:feedlist){
                if (JiroUtil.isAdded(product.getProduct_id()))
                {
                    idprodlist.add(product.getProduct_id());
                    priceprodlist.add(product.getProduct_price());
                    qtyprodlist.add(product.getQty());
                }
            }

            mAdapter.setData(feedlist);

            for(int i=0;i<priceprodlist.size();i++)
            {

                try
                {
                    int fprice = Integer.parseInt(StaticVariable.pro_total_amtlist.get(i));
                    Log.d("UPRICE","oos"+StaticVariable.uom_pos_list.get(i));
                    Log.d("UPRICE","UPRICE"+StaticVariable.uom_price_list.get(i));
                    finalpriceplist.add(StaticVariable.uom_price_list.get(StaticVariable.uom_pos_list.get(i)));
                    totalamt = totalamt + fprice;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            StaticVariable.total_amt = totalamt;

            if(StaticVariable.currencyName.equals("Rupee"))
            {
                tlamt.setText(String.valueOf("₹"+totalamt));
            }
            else if(StaticVariable.currencyName.equals("Dollar"))
            {
                tlamt.setText(String.valueOf("$"+totalamt));
            }
            else {
                tlamt.setText(String.valueOf("₹"+totalamt));
            }


        }
        progress.hide();
    }



    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) ReviwOrder.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    public void goBack(){
        Intent intent = new Intent(ReviwOrder.this, Attrbute_View.class);
        intent.putExtra("redirection","reviworder");

        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        intent.putExtra(AddressViewHolder.Address.ShippingAddres.name() , mShipping);
        intent.putExtra(AddressViewHolder.Address.BillingAddress.name() , mBilling);
        intent.putParcelableArrayListExtra("attributes",all_attributes);
        startActivity(intent);
        ReviwOrder.this.finish();
        ReviwOrder.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }

    @Override
    public void onBackPressed() {
        goBack();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                      return true;

            case R.id.submit:

                if(!tax_sel_spinner.getSelectedItem().toString().equals("Select"))
                {
                    ordersubmitdata(getResources().getString(R.string.url_reference) + "home/order_submit.php");

                }
                else {
                    Toast.makeText(ReviwOrder.this, getResources().getString(R.string.selecttax), Toast.LENGTH_LONG).show();
                }




            default:
                return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_submit, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void Subtotal(Float totalamt) {

        subto=(totalamt/2)+ StaticVariable.total_amt;

        if(StaticVariable.currencyName.equals("Rupee"))
        {
            tlamtwithtax.setText(String.valueOf("₹"+subto));
        }
        else if(StaticVariable.currencyName.equals("Dollar"))
        {
            tlamtwithtax.setText(String.valueOf("$"+subto));
        }
        else {
            tlamtwithtax.setText(String.valueOf("₹"+subto));
        }



    }
}
