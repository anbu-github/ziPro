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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 16/11/16.
 */

public class CreateCustomer extends ActionBarActivity {

    String id="", name="", redirection="",userid="",email="",cust_segid="";

    String cu_name="", cu_contact="", et_email="";

    EditText et1,et2,et3;

    Intent intent;

    private String tag_string_req = "string_req";

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_customer);

        intent = getIntent();
        userid = intent.getStringExtra("id");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        cust_segid = intent.getStringExtra("cust_segid");


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Create Customer" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);


        progress = new ProgressDialog(CreateCustomer.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        et1 = (EditText) findViewById(R.id.cust_name);
        et2 = (EditText) findViewById(R.id.cust_contact);
        et3 = (EditText) findViewById(R.id.cust_email);




    }


    public void save() {
        cu_name = et1.getText().toString();
        cu_contact = et2.getText().toString();
        et_email = et3.getText().toString();


        if (cu_name.equals("") || cu_name.isEmpty()) {

            Toast.makeText(CreateCustomer.this, "Enter Customer Name", Toast.LENGTH_SHORT).show();
        } else if (et_email.equals("") || et_email.isEmpty()) {
            Toast.makeText(CreateCustomer.this, "Enter Customer Email", Toast.LENGTH_SHORT).show();
        }

        else if (cu_contact.equals("") || cu_contact.isEmpty()) {
            Toast.makeText(CreateCustomer.this, "Enter Customer Contact", Toast.LENGTH_SHORT).show();

        }
        else {

            ordersubmitdata(getResources().getString(R.string.url_reference) + "home/create_customer.php");

        }

    }




    private void ordersubmitdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("LSTSEC","LSTSEC"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCustomer.this);
                builder.setMessage(getResources().getString(R.string.ordercustsuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(CreateCustomer.this, CustomerFragActivity.class);
                                intent.putExtra("id",StaticVariable.groupid);
                                startActivity(intent);
                                CreateCustomer.this.finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(CreateCustomer.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateCustomer.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id",StaticVariable.user_id);
                params.put("name",cu_name);
                params.put("phone",cu_contact);
                params.put("email",et_email);
                params.put("business_id",StaticVariable.business_id);
                params.put("customer_segment_id",StaticVariable.customer_segment_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    public void goBack(){
        Intent intent = new Intent(CreateCustomer.this, CustomerFragActivity.class);
        intent.putExtra("id", StaticVariable.groupid);
        intent.putExtra("name", name);
        startActivity(intent);
        CreateCustomer.this.finish();
        CreateCustomer.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

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
