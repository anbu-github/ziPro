package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.model.CustomerVisit;
import com.purplefront.jiro_dev.parsers.SegCustMembers_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 11/30/2016.
 */

public class CustomerApprovalVisit extends Fragment {

    String tag_string_req_recieve2 = "string_req_recieve2";
    ProgressDialog progress;
    String id = "";
    String name = "";
    String redirection = "";
    ImageButton customervisit;
    ListView listView1;
    List<CustomerModel> feedlist;

    public CustomerApprovalVisit() {
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

        View view = inflater.inflate(R.layout.customer_approval, container, false);
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




        approvedcustmembers(getResources().getString(R.string.url_reference) + "home/approved_customer_list.php");

        return view;

    }

    private void display_data() {
        progress.show();
        if(feedlist != null) {

            listView1.setAdapter(new ApprovedCustomerAdapter(getActivity(),feedlist));
        }
        progress.hide();
    }



    private void approvedcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("APCUSTOMER","APCUSTOMER"+s);
                feedlist = SegCustMembers_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
//                Toast.makeText(Customermemlist.this, getResources().getString(R.string.nointernetconnection1), Toast.LENGTH_LONG).show();
//                Toast.makeText(Customermemlist.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();
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
                Log.d("SEGID","SEGID"+StaticVariable.customer_segment_id);




               // params.put("id",StaticVariable.user_id);
                //params.put("name",StaticVariable.name);
                //params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
               // params.put("contact",StaticVariable.contact);
                //params.put("group_id",StaticVariable.groupid);
                //params.put("customer_segment_id",StaticVariable.customer_segment_id);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }



}
