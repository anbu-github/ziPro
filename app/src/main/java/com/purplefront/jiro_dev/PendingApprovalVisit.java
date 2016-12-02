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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.CustomerModel;
import com.purplefront.jiro_dev.model.CustomerVisit;
import com.purplefront.jiro_dev.parsers.SegCustMembers_JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * Created by pf-05 on 11/30/2016.
 */

public class PendingApprovalVisit extends Fragment {

    String tag_string_req_recieve2 = "string_req_recieve2";
    ProgressDialog progress;
    String id = "";
    String name = "";
    String redirection = "";
    ImageButton customercreate;
    ListView listView1;
    List<CustomerModel> feedlist;

//    ArrayList<String> idList = new ArrayList<>();
//    ArrayList<String> actionidList = new ArrayList<>();
//    ArrayList<String> permissionidList = new ArrayList<>();

    public PendingApprovalVisit() {
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

        View view = inflater.inflate(R.layout.pending_approval, container, false);
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

        customercreate = (ImageButton)view.findViewById(R.id.btn_add_creatcust);
        listView1=(ListView)view.findViewById(R.id.cuspending_approvalview);




//                if(StaticVariable.actionidList.get(i).equals("1"))
//                {
//                    customercreate.setVisibility(View.VISIBLE);
//                    break;
//                }
//                else
//                {
//                    customercreate.setVisibility(View.GONE);
//                }




//            int act_id = Integer.parseInt(StaticVariable.actionidList.get(i));
//
//            if(act_id == 1)
//            {
//                customercreate.setVisibility(View.VISIBLE);
//                break;
//            }
//            else {
//                customercreate.setVisibility(View.GONE);
//            }
        //}







        customercreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CreateCustomer.class);
                intent.putExtra("id",StaticVariable.user_id);
                intent.putExtra("email",StaticVariable.email);
                intent.putExtra("name",name);
                startActivity(intent);

            }
        });



        getcustmembers(getResources().getString(R.string.url_reference) + "home/group_customers_list.php");




        for(int i=0; i<StaticVariable.actionidList.size();i++) {
            if(StaticVariable.actionidList.get(i).equals("1")) {
                int permit = Integer.parseInt(StaticVariable.permissionidList.get(i));
                if(StaticVariable.per_id >= permit) {
                    customercreate.setVisibility(View.GONE);
                    break;
                } else {
                    customercreate.setVisibility(View.VISIBLE);
                    break;
                }
                //break;
            } else {
                customercreate.setVisibility(View.GONE);
            }
        }





        return view;

    }



    private void display_data() {
        progress.show();
        if(feedlist != null) {

            listView1.setAdapter(new PendingApprovalAdapter(getActivity(),feedlist));
        }
        progress.hide();
    }






    private void getcustmembers(String url)
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("PENDINGAPPRES","PENDINGAPPRES"+s);


                try
                {
                    JSONObject jsonObject1 = new JSONObject(s);
                    String jsonArray1 = jsonObject1.getString("permission");
                    JSONArray ar=new JSONArray(jsonArray1);

                    Log.d("PERLE","PERLE"+ar);
                    for(int i = 0; i < ar.length();i++)
                    {
                        JSONObject obj = ar.getJSONObject(i);

                        String id = obj.getString("id");
                        String action_id = obj.getString("action_id");
                        String permission_id = obj.getString("permission_id");

                        StaticVariable.idList.add(id);
                        StaticVariable.actionidList.add(action_id);
                        StaticVariable.permissionidList.add(permission_id);

                        Log.d("ASIZE","ASIZE"+StaticVariable.actionidList.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }




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
                Log.d("CCF1","CCF1"+StaticVariable.cusobj_id);
                Log.d("CCF2","CCF2"+StaticVariable.cus_seg_objectid);




                params.put("id",StaticVariable.user_id);
                params.put("email",StaticVariable.email);
                params.put("business_id",StaticVariable.business_id);
                params.put("contact",StaticVariable.contact);
                params.put("group_id",StaticVariable.groupid);
                params.put("customer_segment_id",StaticVariable.customer_segment_id);
                params.put("customer_object_id",StaticVariable.cusobj_id);
                params.put("customer_segment_id2",StaticVariable.customer_segment_id);
                params.put("customer_segment_object_id",StaticVariable.cus_seg_objectid);

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
    }



}
