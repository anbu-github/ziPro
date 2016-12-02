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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.VisitCustomerDisplayModal;
import com.purplefront.jiro_dev.parsers.CustomerVisitDisplay_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 11/29/2016.
 */

public class CompletedVisitDisplay extends ActionBarActivity {
    String id="", name="", redirection="", order_id = "";

    Intent intent;

    List<VisitCustomerDisplayModal> feedslist;

    private String tag_string_req = "string_req";

    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disp_completed_visit);



//        intent = getIntent();
//        id = intent.getStringExtra("id");
//        StaticVariable.groupid = id;
//        name = intent.getStringExtra("name");

        progress = new ProgressDialog(CompletedVisitDisplay.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();





        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Completed Visit Display" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        progress.show();

        if (isonline()) {
            visitdiplay(getResources().getString(R.string.url_reference) + "home/visit_details.php");
        } else {
            Toast.makeText(CompletedVisitDisplay.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }

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

            TextView notevw = (TextView)findViewById(R.id.notesview);

            if(flower.getNotes() == "" || flower.getNotes().equals(null))
            {
                tv6.setVisibility(View.GONE);
                notevw.setVisibility(View.GONE);
            }
            else
            {
                tv6.setVisibility(View.VISIBLE);
                notevw.setVisibility(View.VISIBLE);
                tv6.setText(flower.getNotes());
            }



            TextView tv7 = (TextView) findViewById(R.id.cust_visitime);
            tv7.setText(flower.getVisit_date());

            TextView tv8 = (TextView)findViewById(R.id.visi_checkingtime);
            tv8.setText(flower.getCheckin_date());



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
                Toast.makeText(CompletedVisitDisplay.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CompletedVisitDisplay.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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
        ConnectivityManager cm = (ConnectivityManager) CompletedVisitDisplay.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    public void goback()

    {
        Intent intent = new Intent(CompletedVisitDisplay.this,GroupVisitActivity.class);
        intent.putExtra("name",StaticVariable.catalognamedisp);
        startActivity(intent);
        CompletedVisitDisplay.this.finish();
        CompletedVisitDisplay.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

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
