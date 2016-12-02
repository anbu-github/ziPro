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
import com.purplefront.jiro_dev.model.CustomerVisit;
import com.purplefront.jiro_dev.parsers.Customer_Visit_Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manishagarwal on 25/11/16.
 */

public class CompletedVisits extends Fragment {

    String tag_string_req_recieve2 = "string_req_recieve2";
    ProgressDialog progress;
    String id = "";
    String name = "";
    String redirection = "";
    ListView listView1;
    List<CustomerVisit> feedlist;

    public CompletedVisits() {
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

        View view = inflater.inflate(R.layout.completedvisits, container, false);

        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        //return inflater.inflate(R.layout.completedvisits, container, false);

        listView1=(ListView)view.findViewById(R.id.completelview);

        completedVisit(getResources().getString(R.string.url_reference) + "home/visit_home.php");

        return view;
    }

    private void display_data() {
        //progress.show();
        if(feedlist != null) {

            listView1.setAdapter(new CompletedVisitAdapter(getActivity(),feedlist));
        }
        progress.hide();
    }


    private void completedVisit(String url)
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
                params.put("visit_option", "2");


                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }


}
