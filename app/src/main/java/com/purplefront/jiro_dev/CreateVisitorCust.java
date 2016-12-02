package com.purplefront.jiro_dev;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.model.Create_Address_Model1;
import com.purplefront.jiro_dev.parsers.Create_Address_JSONParser;
import com.purplefront.jiro_dev.parsers.Create_Address_JSONParser1;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class CreateVisitorCust extends ActionBarActivity{

    String id = "",name = "",business_id = "",redirection = "";
    String cidlist,compidlist,customerName;

    EditText customernotesetx,customerattributeetx;


    int position,pos1;
    String cus_notes="", cus_attr="";

    String current_date="",current_time="";

    String upcoming_date = "",upcoming_time="";

    Double latitude,longitude;

    ArrayList<String> customerIdList = new ArrayList<>();
    ArrayList<String> customerNameList = new ArrayList<>();
    private String tag_string_req = "string_req";
    ArrayList<String> contactIdlist  = new ArrayList<>();
    ArrayList<String> contactNameList  = new ArrayList<>();
    ArrayAdapter<String> dataAdapter, dataAdapter2;
    Intent intent;
    ProgressDialog progress;
    SearchableSpinner customer;
    Spinner contact;
    RecyclerView mRecyclerView1;
    DatePickerDialog.OnDateSetListener date, pickup_date;

    RecyclerView.LayoutManager mLayoutManager1;
    RecyclerView.Adapter mAdapter2;

    List<Create_Address_Model1> feedlist;
    TimePickerDialog.OnTimeSetListener time;
    String selectedTime,intent_from="";

    RadioGroup rad;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar = Calendar.getInstance();

    LinearLayout customernotes,customerattribute,latervisitlayout,addresslayout;
    TextView expectedVisitorDate,expectedVistorTime;

    String myFormat = "dd-MM-yyyy";
    String myFormat1 = "yyyy-MM-dd HH:mm:ss";

    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1);




    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcut_visit);

        progress = new ProgressDialog(CreateVisitorCust.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Create Visit" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);


        customer = (SearchableSpinner)findViewById(R.id.cus_list);
        contact = (Spinner)findViewById(R.id.cust_company_list);

        rad=(RadioGroup) findViewById(R.id.rg5);

        customernotes = (LinearLayout) findViewById(R.id.custnotelayout);

        customerattribute = (LinearLayout)findViewById(R.id.custattrlayout);

        latervisitlayout = (LinearLayout)findViewById(R.id.latervisitlayout);

        addresslayout = (LinearLayout)findViewById(R.id.addlayout);

        customernotesetx = (EditText)findViewById(R.id.enternotes);
        customerattributeetx = (EditText)findViewById(R.id.customattribute);

//        cus_notes = customernotesetx.getText().toString();
//        cus_attr = customerattributeetx.getText().toString();


        expectedVisitorDate = (TextView) findViewById(R.id.expect_pickup_date);

        expectedVistorTime = (TextView)findViewById(R.id.expect_pickup_time);


        myCalendar.set(Calendar.HOUR,9);
        myCalendar1.set(Calendar.HOUR,9);
        myCalendar.set(Calendar.AM_PM,0);
        myCalendar1.set(Calendar.AM_PM,0);
        myCalendar.set(Calendar.MINUTE,0);
        myCalendar1.set(Calendar.MINUTE,0);
        expectedVistorTime.setText("09:00");





        pickup_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.v("date", monthOfYear + "");


                updatePickupDate();

            }

        };





        expectedVisitorDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(CreateVisitorCust.this, pickup_date, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();


                updatePickupDate();
            }
        });



        time = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                if (selectedTime.contains("pickedTime")) {
                    myCalendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar1.set(Calendar.MINUTE, minute);
                    updateTime();
                }
                else {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    updateTime();
                }
            }
        };




        expectedVistorTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new TimePickerDialog(CreateVisitorCust.this, time,
                        myCalendar1.get(myCalendar1.HOUR_OF_DAY),
                        myCalendar1.get(myCalendar1.MINUTE),
                        true).show();
                selectedTime = "pickedTime";

            }
        });

        rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                position= rad.indexOfChild(findViewById(i));
                int id=rad.getCheckedRadioButtonId();

                pos1=rad.indexOfChild(findViewById(rad.getCheckedRadioButtonId()));

                Log.d("CLK","CLK"+pos1);


                View radioButton = rad.findViewById(id);
                if(radioButton.getId()==R.id.rg5r1)
                {
                    Log.v("CLICK1","CLICK1"+radioButton.getId());
                    customernotes.setVisibility(View.VISIBLE);
                    customerattribute.setVisibility(View.GONE);
                    latervisitlayout.setVisibility(View.GONE);
                }
                else if(radioButton.getId() == R.id.rg5r2)
                {
                    Log.v("CLICK2","CLICK2"+radioButton.getId());
                    latervisitlayout.setVisibility(View.VISIBLE);
                    customernotes.setVisibility(View.GONE);
                    customerattribute.setVisibility(View.GONE);
                }
                else
                {
                    customernotes.setVisibility(View.GONE);
                    customerattribute.setVisibility(View.GONE);
                    latervisitlayout.setVisibility(View.GONE);

                }
            }
        });


        mRecyclerView1 = (RecyclerView) findViewById(R.id.cust_address);
        mRecyclerView1.setHasFixedSize(true);


        mLayoutManager1 = new GridLayoutManager(CreateVisitorCust.this, 1);
        mRecyclerView1.setLayoutManager(mLayoutManager1);



        progress.show();
        if (isonline()) {
            getcustmembers(getResources().getString(R.string.url_reference) + "home/customers_list.php");

        } else {
            Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }



        dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, customerNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_layout, contactNameList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {

                    StaticVariable.customerid = customerIdList.get(i).toString();
                    Log.v("customer_id", StaticVariable.customerid );

                    StaticVariable.dispcustomername1 = customerNameList.get(i).toString();
                    Log.d("CNAM","CNAM"+StaticVariable.dispcustomername1);




                } catch (Exception e) {

                    e.printStackTrace();
                }

                getcontacts(getResources().getString(R.string.url_reference) + "home/customer_contacts_list.php");

                getSelectAddress(getResources().getString(R.string.url_reference) + "home/customer_address_list.php");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    StaticVariable.customerContactid = contactIdlist.get(i).toString();
                    Log.v("contact_id", StaticVariable.customerContactid);


                    StaticVariable.dispContactName1 = contactNameList.get(i).toString();



                } catch (Exception e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        current_date = df.format(dateobj);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        Date dateobj1 = new Date();
        current_time = timeFormatter.format(dateobj1);

//          Calendar calobj = Calendar.getInstance();
//          current_time = df.format(calobj.getTime());


        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) { // gps enabled

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d("latitude", "" + latitude);
            Log.d("longitude", "" + longitude);
        } // return boolean true/false

        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }



    public void save()

    {
        cus_notes = customernotesetx.getText().toString();
        cus_attr = customerattributeetx.getText().toString();

        if(!customer.getSelectedItem().toString().equals("Select") && !contact.getSelectedItem().toString().equals("Select"))
                 {
                     submitcustomervisit(getResources().getString(R.string.url_reference) + "home/visit_quick_create_checkin.php");

                 }
                    else
                    {
                        Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.selectcuscont), Toast.LENGTH_LONG).show();

                    }



       // submitcustomervisit(getResources().getString(R.string.url_reference) + "home/visit_quick_create_checkin.php");

//        if (cusatt_name.equals("") || cusatt_name.isEmpty()) {
//
//            Toast.makeText(Create_CustomAtt.this, "Enter Attribute Name", Toast.LENGTH_SHORT).show();
//        }
//
//        else {
//
//            ordersubmitdata(getResources().getString(R.string.url_reference) + "home/create_custom_attribute.php");
//
//        }

    }



    public void save1()
    {
        if(!customer.getSelectedItem().toString().equals("Select") && !contact.getSelectedItem().toString().equals("Select"))
        {
            submitcustomervisit1(getResources().getString(R.string.url_reference)+"home/visit_create.php");
        }
        else
        {
            Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.selectcuscont), Toast.LENGTH_LONG).show();
        }

        //submitcustomervisit1(getResources().getString(R.string.url_reference)+"home/visit_create.php");
    }




    private void submitcustomervisit1(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CRTCUSRES1","CRTCUSRES1"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateVisitorCust.this);
                builder.setMessage(getResources().getString(R.string.createvisitsuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CreateVisitorCust.this, GroupVisitActivity.class);
                                intent.putExtra("name",StaticVariable.catalognamedisp);
                                startActivity(intent);
                                CreateVisitorCust.this.finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateVisitorCust.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("JJ1","JJ1"+StaticVariable.user_id);
                Log.d("JJ2","JJ2"+StaticVariable.business_id);
                Log.d("JJ3","JJ3"+StaticVariable.customerid);
                Log.d("JJ4","JJ4"+StaticVariable.customerContactid);
                Log.d("JJ5","JJ5"+current_date);
                Log.d("JJ6","JJ6"+current_time);
                Log.d("JJ7","JJ7"+StaticVariable.cbPosition);
                Log.d("JJ8","JJ8"+cus_notes);
                Log.d("JJ9","JJ9"+latitude);
                Log.d("JJ10","JJ10"+longitude);


                params.put("id",StaticVariable.user_id);
                params.put("business_id",StaticVariable.business_id);
                params.put("customer_id",StaticVariable.customerid);
                params.put("customer_contact_id",StaticVariable.customerContactid);
                params.put("contact_address_id",String.valueOf(StaticVariable.cbPosition));
                params.put("visit_date",upcoming_date);
                params.put("visit_time",upcoming_time);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }















    private void submitcustomervisit(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CRTCUSRES","CRTCUSRES"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateVisitorCust.this);
                builder.setMessage(getResources().getString(R.string.createvisitsuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CreateVisitorCust.this, GroupVisitActivity.class);
                                intent.putExtra("name",StaticVariable.catalognamedisp);
                                startActivity(intent);
                                CreateVisitorCust.this.finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateVisitorCust.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("JJ1","JJ1"+StaticVariable.user_id);
                Log.d("JJ2","JJ2"+StaticVariable.business_id);
                Log.d("JJ3","JJ3"+StaticVariable.customerid);
                Log.d("JJ4","JJ4"+StaticVariable.customerContactid);
                Log.d("JJ5","JJ5"+current_date);
                Log.d("JJ6","JJ6"+current_time);
                Log.d("JJ7","JJ7"+StaticVariable.cbPosition);
                Log.d("JJ8","JJ8"+cus_notes);
                Log.d("JJ9","JJ9"+latitude);
                Log.d("JJ10","JJ10"+longitude);


                params.put("id",StaticVariable.user_id);
                params.put("business_id",StaticVariable.business_id);
                params.put("customer_id",StaticVariable.customerid);
                params.put("customer_contact_id",StaticVariable.customerContactid);
                params.put("visit_date",current_date);
                params.put("visit_time",current_time);
                params.put("contact_address_id",String.valueOf(StaticVariable.cbPosition));
                params.put("notes",cus_notes);
                params.put("checkin_lat",String.valueOf(latitude));
                params.put("checkin_long",String.valueOf(longitude));

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }




    public void updatePickupDate() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
        if (myCalendar1.getTime().before(cal.getTime())) {
            Toast.makeText(CreateVisitorCust.this, "Please select valid date", Toast.LENGTH_SHORT).show();
        } else {


            expectedVisitorDate.setText(sdf.format(myCalendar1.getTime()));

            String pick_date = sdf.format(myCalendar1.getTime()) + "";

            StaticVariable.pickupDate = pick_date;
            StaticVariable.pickedDateTIme = sdf1.format(myCalendar1.getTime());
            Log.v("currentTime", StaticVariable.pickupDate);


            //String formattedDate = dateFormat.format(someDate.getTime());


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateobj = new Date();
            upcoming_date = df.format(myCalendar1.getTime());
            Log.v("LTDATE","LTDATE"+upcoming_date);

        }

    }


    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if (selectedTime.contains("pickedTime")) {
            expectedVistorTime.setText(sdf.format(myCalendar1.getTime()));
            //StaticVariables.pickedDateTIme = sdf1.format(myCalendar1.getTime());



            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
            Date dateobj1 = new Date();
            upcoming_time = timeFormatter.format(myCalendar1.getTime());
            Log.d("LTIME","LTIME"+upcoming_time);

        }

    }





    private void display_data()
    {
        progress.show();
        if(feedlist != null) {

            Log.d("GEIDD","GEIDD");
            mRecyclerView1.setAdapter(new Address_Section_Adaptor3(CreateVisitorCust.this,feedlist));
            //addresslayout.setVisibility(View.VISIBLE);


//            mAdapter2 = new Address_Section_Adaptor2(feedlist, CreateVisitorCust.this);
//            mRecyclerView1.setAdapter(mAdapter2);
        }
        progress.hide();
    }






    private void getSelectAddress(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("CUSVSTADDRES","CUSVSTADDRES"+s);

                addresslayout.setVisibility(View.VISIBLE);

                feedlist = Create_Address_JSONParser1.parserFeed(s);

                Log.d("CTAFEEDLIST","CTAFEEDLIST"+feedlist.size());


                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
                //Toast.makeText(Product_list.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                Log.d("SAM1","SAM1"+StaticVariable.user_id);
                Log.d("SAM2","SAM2"+StaticVariable.email);
                Log.d("SAM3","SAM3"+StaticVariable.business_id);
                Log.d("SAM4","SAM4"+StaticVariable.contact);
                Log.d("SAM5","SAM5"+StaticVariable.groupid);
                Log.d("SAM6","SAM6"+StaticVariable.customerid);
                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_id",StaticVariable.customerid);


                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "CreateAddress");
    }




    private void getcontacts(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LCONT","LCONT"+s);


                contactIdlist.clear();
                contactNameList.clear();


                contactIdlist.add("Select");
                contactNameList.add("Select");


                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String contactnamelist = obj.getString("contact_name");
                        String contactidlist = obj.getString("contact_id");

                        contactNameList.add(contactnamelist);
                        contactIdlist.add(contactidlist);

                    }

                    contact.setAdapter(dataAdapter2);

                    for (int i = 0; i < contactIdlist.size();i++) {

                        if (StaticVariable.customerContactid.equals(contactIdlist.get(i))) {
                            contact.setSelection(i);
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
                Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateVisitorCust.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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

                Log.d("CONID","CONID"+StaticVariable.customerid);



                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_id",StaticVariable.customerid);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) CreateVisitorCust.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    private void getcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);

                customerIdList.clear();
                customerNameList.clear();

                customerNameList.add("Select");
                customerIdList.add("Select");

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for(int i = 0; i < jsonArray.length();i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        cidlist = obj.getString("customer_id");
                        compidlist = obj.getString("customer_name");

                        customerIdList.add(cidlist);
                        customerNameList.add(compidlist);
                        customer.setEnabled(true);

                    }
                    customer.setTitle("Select customer");

                    customer.setAdapter(dataAdapter);


                    for (int i = 0; i < customerIdList.size();i++) {

                        if (StaticVariable.customerid.equals(customerIdList.get(i))) {
                            customer.setSelection(i);
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
                Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateVisitorCust.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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



    public void goBack(){
        Intent intent = new Intent(CreateVisitorCust.this, GroupVisitActivity.class);
        intent.putExtra("name",StaticVariable.catalognamedisp);
        startActivity(intent);
        CreateVisitorCust.this.finish();
        CreateVisitorCust.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;

            case R.id.submit:

                if(pos1 ==1)
                {
                    save1();
                }
                else
                {
                    save();
                }




//                if(pos1 !=1)
//                {
//                    if(!customer.getSelectedItem().toString().equals("Select") && !contact.getSelectedItem().toString().equals("Select"))
//                    {
//                        save();
//                    }
//                    else
//                    {
//                        Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.selectcuscont), Toast.LENGTH_LONG).show();
//                    }
//                }
//                else
//                {
//                    save1();
//                }
//
//
////
////                if(pos1 == 1)
////                {
////                    if(!customer.getSelectedItem().toString().equals("Select") && !contact.getSelectedItem().toString().equals("Select"))
////                    {
////                        save1();
////                    }
////                    else
////                    {
////                        Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.selectcuscont), Toast.LENGTH_LONG).show();
////
////                    }
////
////                }
////                if(pos1 == 0) {
////                    if(!!customer.getSelectedItem().toString().equals("Select") && !contact.getSelectedItem().toString().equals("Select"))
////                    {
////                        save();
////                    }
////                    else
////                    {
////                        Toast.makeText(CreateVisitorCust.this, getResources().getString(R.string.selectcuscont), Toast.LENGTH_LONG).show();
////                    }
////
////                }






            default:
                return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }





}
