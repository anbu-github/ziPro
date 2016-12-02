package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.MemberModel;
import com.purplefront.jiro_dev.parsers.MemProfileCategory_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 13/10/16.
 */

public class MembersProfileView extends ActionBarActivity{

    String id="", name="", mem_name= "", mem_contact = "";
    Intent intent;

    List<MemberModel> feedslist;


    ProgressDialog progress;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membersprofile_view);
        progress = new ProgressDialog(MembersProfileView.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        mem_contact = intent.getStringExtra("mem_contact");
        mem_name = intent.getStringExtra("mem_name");





        Log.d("PRID1","PRID1"+id);


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Member Details" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);



        progress.show();
        if (isonline()) {
            ViewMembers(getResources().getString(R.string.url_reference) + "home/group_members_details.php");
        } else {
            Toast.makeText(MembersProfileView.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }
    }

    private void display_data()
    {
        progress.show();
        if(feedslist != null) for (final MemberModel flower : feedslist) {

//            mAdapter = new MemberSelectionAdapter(pageResponse, MembersProfileView.this);
//            mRecyclerView.setAdapter(mAdapter);

            TextView tv3 = (TextView) findViewById(R.id.mem_name);
            tv3.setText(flower.getName());

            TextView trole = (TextView)findViewById(R.id.mem_role);
            trole.setText(flower.getRole());

            TextView tv4 = (TextView) findViewById(R.id.mem_email);
            tv4.setText(flower.getEmail());

            TextView tv5 = (TextView) findViewById(R.id.phone);
            tv5.setText(flower.getContact());


            TextView tv6 = (TextView) findViewById(R.id.profile);
            tv6.setText(flower.getProfile_id());

            TextView tv7 = (TextView) findViewById(R.id.status);
            tv7.setText(flower.getActive());


        }
        progress.hide();
    }

    private void ViewMembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("MEMPRO","MEMPRO"+s);
                feedslist = MemProfileCategory_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(MembersProfileView.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(MembersProfileView.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Log.d("CKID","CKID"+StaticVariable.user_id);
                Log.d("CKEMAIL","CKEMAIL"+StaticVariable.email);
                Log.d("CKEBID","CKEBID"+StaticVariable.business_id);
                Log.d("CKECID","CKECID"+StaticVariable.contact);
                Log.d("CKEGID","CKEGID"+StaticVariable.groupid);

                Log.d("MEM_ID","MEM_ID"+id);




                params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("member_id",id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) MembersProfileView.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(MembersProfileView.this, Memberslist.class);
                intent.putExtra("redirection","Catalog");
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                MembersProfileView.this.finish();
                this.finish();
                startActivity(intent);
                MembersProfileView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MembersProfileView.this, Memberslist.class);
        intent.putExtra("id",id);
        intent.putExtra("name",name);

        MembersProfileView.this.finish();
        this.finish();
        startActivity(intent);


        MembersProfileView.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }
}
