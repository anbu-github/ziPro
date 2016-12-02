package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerVisit;
import com.purplefront.jiro_dev.parsers.AttributeView_JSONParser;
import com.purplefront.jiro_dev.parsers.Customer_Visit_Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manishagarwal on 25/11/16.
 */

public class SheduledVisits extends Fragment {

    String tag_string_req_recieve2 = "string_req_recieve2";
    ProgressDialog progress;
    String id = "";
    String name = "";
    String redirection = "";
    ImageButton customervisit;
    ListView listView1;
    List<CustomerVisit> feedlist;


    public SheduledVisits() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.sheduledvisits, container, false);
        //return inflater.inflate(R.layout.sheduledvisits, container, false);

//        name = getArguments().getString("name");
//        id = getArguments().getString("id");


        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        customervisit = (ImageButton)view.findViewById(R.id.btn_add_cusvisit);
        listView1=(ListView)view.findViewById(R.id.shedulelview);


        customervisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StaticVariable.customerid="";
                StaticVariable.customerContactid="";
                StaticVariable.cbPosition=null;

                Intent intent = new Intent(getActivity(),CreateVisitorCust.class);
                intent.putExtra("id",id);
                intent.putExtra("name",StaticVariable.catalognamedisp);
                startActivity(intent);

            }
        });

        sheduledvisits1(getResources().getString(R.string.url_reference) + "home/visit_home.php");

        return view;

    }

    private void display_data() {
        progress.show();
        if(feedlist != null) {

            listView1.setAdapter(new ScheduledAdapter(getActivity(),feedlist));
        }
        progress.hide();
    }




    private void sheduledvisits1(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("SHEVIST","SHEVIST"+s);

                feedlist = Customer_Visit_Parser.parserFeed(s);

                display_data();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
//                Toast.makeText(SheduledVisits.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
//                Toast.makeText(Attrbute_View.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", StaticVariable.user_id);
                params.put("email", StaticVariable.email);
                params.put("visit_option", "1");


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }





















//    public void sheduledvisits() {
//        //PDialog.show(getActivity());
//        StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.url_reference) + "home/visit_home.php.php?",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progress.hide();
////                        Toast.makeText(thisActivity,"address saved",Toast.LENGTH_SHORT).show();
//
//
//                        Log.v("response", response + "");
//                        try {
//                            progress.hide();
//
//                            JSONObject jobj=new JSONObject(response);
//                            String orders=jobj.getString("delivered");
//
//
//                            JSONArray ar = new JSONArray(orders);
//                            List<CustomerVisit> respnse = new ArrayList<>();
//
//                            for (int i = 0; i < ar.length(); i++) {
//                                JSONObject parentObject = ar.getJSONObject(i);
//
//                                totQuantity=totQuantity+Integer.parseInt(parentObject.getString("quantity"));
//
//                            }
//                            total_quantity.setText(String.valueOf(totQuantity));
//
//
//                            feedlist= Customer_Visit_Parser.parserFeed(orders);
//
//
//                            listView1.setAdapter(new ScheduledAdapter(getActivity(),feedlist));
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            progress.hide();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            progress.hide();
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError arg0) {
//                        progress.hide();
//
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<>();
//
//                params.put("id", StaticVariable.user_id);
//                params.put("email", StaticVariable.email);
//                params.put("visit_option", "3");
//
//                //params.put("email", StaticVariables.database.get(0).getEmail());
//
//                return params;
//            }
//        };
//        AppController.getInstance().addToRequestQueue(request, "data_receive");
//        Log.v("request", request + "");
//    }
}
