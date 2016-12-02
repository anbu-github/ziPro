package com.purplefront.jiro_dev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Create_CustomAtt extends ActionBarActivity {

    String id="", name="";
    Intent intent;
    private String tag_string_req = "string_req";
    EditText et1;
    String cusatt_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_customattr);

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");


        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Create Custom Attributes" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pf);



        et1 = (EditText) findViewById(R.id.cust_attrname);


    }

    public void save() {
        cusatt_name = et1.getText().toString();


        if (cusatt_name.equals("") || cusatt_name.isEmpty()) {

            Toast.makeText(Create_CustomAtt.this, "Enter Attribute Name", Toast.LENGTH_SHORT).show();
        }

        else {

            ordersubmitdata(getResources().getString(R.string.url_reference) + "home/create_custom_attribute.php");

        }

    }


    private void ordersubmitdata(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("CUSCREATTR","CUSCREATTR"+s);


                AlertDialog.Builder builder = new AlertDialog.Builder(Create_CustomAtt.this);
                builder.setMessage(getResources().getString(R.string.attributesuccess))
                        .setCancelable(false)
                        .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(Create_CustomAtt.this, Attrbute_View.class);
                                intent.putExtra("id",StaticVariable.groupid);
                                intent.putExtra("name",name);
                                startActivity(intent);
                                Create_CustomAtt.this.finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);             }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(Create_CustomAtt.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
                Toast.makeText(Create_CustomAtt.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Log.d("CAUID","CAUID"+StaticVariable.user_id);
                params.put("id",StaticVariable.user_id);

                Log.d("CANAME","CANAME"+cusatt_name);
                params.put("attribute_name",cusatt_name);

                Log.d("CABID","CABID"+StaticVariable.business_id);
                params.put("business_id",StaticVariable.business_id);

                Log.d("CAOID","CAOID"+StaticVariable.object_id);
                params.put("object_id",StaticVariable.object_id);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }


    public void goBack(){
        Intent intent = new Intent(Create_CustomAtt.this, Attrbute_View.class);
        intent.putExtra("id", StaticVariable.groupid);
        intent.putExtra("name", name);
        startActivity(intent);
        Create_CustomAtt.this.finish();
        Create_CustomAtt.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

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