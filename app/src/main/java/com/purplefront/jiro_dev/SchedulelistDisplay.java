package com.purplefront.jiro_dev;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
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
import com.google.gson.Gson;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.MemberModel;
import com.purplefront.jiro_dev.model.VisitCustomerDisplayModal;
import com.purplefront.jiro_dev.parsers.CustomerVisitDisplay_JSONParser;
import com.purplefront.jiro_dev.parsers.MemProfileCategory_JSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 11/29/2016.
 */

public class SchedulelistDisplay extends ActionBarActivity {

    String id="", name="", redirection="", order_id = "";

    Intent intent;

    List<VisitCustomerDisplayModal> feedslist;

    private String tag_string_req = "string_req";

    ProgressDialog progress;
    Button chkbtn;

    Double latitude,longitude;

    String chkincurrent_date="",chkincurrent_time="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disp_schedulelist);



//        intent = getIntent();
//        id = intent.getStringExtra("id");
//        StaticVariable.groupid = id;
//        name = intent.getStringExtra("name");

        progress = new ProgressDialog(SchedulelistDisplay.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();



        chkbtn = (Button)findViewById(R.id.checkinbtn);

        chkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GPSTracker gps = new GPSTracker(SchedulelistDisplay.this);
                if (gps.canGetLocation()) { // gps enabled

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Log.d("chklatitude", "chklatitude" + latitude);
                    Log.d("chklongitute", "chklongitute" + longitude);
                } // return boolean true/false

                else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }




                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateobj = new Date();
                chkincurrent_date = df.format(dateobj);

                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
                Date dateobj1 = new Date();
                chkincurrent_time = timeFormatter.format(dateobj1);


                checkinscehdulevisit(getResources().getString(R.string.url_reference) + "home/visit_checkin.php");

            }
        });


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Schedule Visit Display" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);

        progress.show();

        if (isonline()) {
            visitdiplay(getResources().getString(R.string.url_reference) + "home/visit_details.php");
        } else {
            Toast.makeText(SchedulelistDisplay.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

    }



    private void checkinscehdulevisit(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(SchedulelistDisplay.this);
                builder.setMessage(getResources().getString(R.string.chkinvisit))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(SchedulelistDisplay.this, GroupVisitActivity.class);
                                intent.putExtra("name",StaticVariable.catalognamedisp);
                                SchedulelistDisplay.this.finish();
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
                Toast.makeText(SchedulelistDisplay.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(SchedulelistDisplay.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                Log.d("TT1","TT1"+StaticVariable.customer_visit_id);
                Log.d("TT2","TT2"+chkincurrent_date);
                Log.d("TT3","TT3"+chkincurrent_time);
                Log.d("TT4","TT4"+latitude);
                Log.d("TT5","TT5"+longitude);

                params.put("visit_id",StaticVariable.customer_visit_id);
                params.put("checkin_date",chkincurrent_date);
                params.put("checkin_time",chkincurrent_time);
                params.put("latitude",String.valueOf(latitude));
                params.put("longitude",String.valueOf(longitude));
                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }









    private void display_data()
    {
        progress.show();
        if(feedslist != null) for (final VisitCustomerDisplayModal flower : feedslist) {

//            mAdapter = new MemberSelectionAdapter(pageResponse, MembersProfileView.this);
//            mRecyclerView.setAdapter(mAdapter);

            TextView tv3 = (TextView) findViewById(R.id.vis_contactname);
            tv3.setText(flower.getCustomer_contact());

            TextView trole = (TextView)findViewById(R.id.vis_custname);
            trole.setText(flower.getCustomer());

            TextView tv4 = (TextView) findViewById(R.id.cvadd1);
            tv4.setText(flower.getAddress_line1()+ "," +flower.getAddress_line2() + ","+flower.getAddress_line3());

            TextView tv5 = (TextView) findViewById(R.id.cvadd3);
            tv5.setText(flower.getCity()+ ","+flower.getState()+ ","+flower.getCountry() + ","+flower.getZipcode());


            TextView tv6 = (TextView) findViewById(R.id.visi_customernotes);
            tv6.setText(flower.getNotes());

            TextView tv7 = (TextView) findViewById(R.id.cust_visitime);
            tv7.setText(flower.getVisit_date());



//            if(!flower.getCheckin_date().equals("null"))
//            {
//                tv8.setText(flower.getCheckin_date());
//            }


        }
        progress.hide();
    }




    private void visitdiplay(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("MEMPRO","MEMPRO"+s);
                feedslist = CustomerVisitDisplay_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(SchedulelistDisplay.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(SchedulelistDisplay.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Log.d("VID","VID"+StaticVariable.customer_visit_id);
                params.put("visit_id",StaticVariable.customer_visit_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) SchedulelistDisplay.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    public void goback()

    {
        Intent intent = new Intent(SchedulelistDisplay.this,GroupVisitActivity.class);
        intent.putExtra("name",StaticVariable.catalognamedisp);
        startActivity(intent);
        SchedulelistDisplay.this.finish();
        SchedulelistDisplay.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goback();
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         goback();

    }

}
