package com.purplefront.jiro_dev;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple on 04/10/16.
 */

public class Editprofile extends Fragment {

    protected String tag_string_req_send = "string_req_send";

    String name = "", email = "", phone = "", profile_name = "", user_id = "";
    EditText nameet, phoneet, emailet;
    Button b;
    ProgressDialog progress;
    View view;
    private String TAG = Editprofile.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.edit_profile1, container, false);
        setHasOptionsMenu(true);



        nameet = (EditText)view.findViewById(R.id.viewedit_name_edit);
        phoneet = (EditText) view.findViewById(R.id.viewedit_phone_edit);
        emailet = (EditText) view.findViewById(R.id.viewedit_website_edit);

        b = (Button)view.findViewById(R.id.viewedit_update);


        Bundle args = getArguments();
        try {

            user_id = args.getString("user_id");
            profile_name = args.getString("profile_name");
            email = args.getString("email");
            phone = args.getString("phone");



        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("EPID","EPID"+user_id);

        nameet.setText(profile_name);
        phoneet.setText(phone);
        emailet.setText(email);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name  = nameet.getText().toString();
                if(isonline())
                {
                    StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.url_reference)+"home/edit_profile.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    Log.d("password response", s);
                                    updatedislay();
                                    //progress.hide();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                                    //progress.hide();
                                    Toast.makeText(getActivity(), R.string.nointernetaccess, Toast.LENGTH_LONG).show();
                                    //          Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    getActivity().finish();
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
                                }
                            }){

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            //params.put("user_id", user_id);
                            params.put("user_id",StaticVariable.user_id);

                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(request, tag_string_req_send);


                }

            }
        });


        return view;
    }


    public void updatedislay() {
        //progress.show();
        dbhelp entry = new dbhelp(this.getActivity());
        entry.open();
        entry.updateeuser(user_id, name);
        entry.close();
        //progress.hide();

            Intent intent = new Intent(getActivity(), MainActivity.class);

            intent.putExtra("user_id", user_id);


            intent.putExtra("redirection", "View/Edit Profile");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Editprofile.this.getActivity().finish();
            getActivity().finish();
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }



    }


