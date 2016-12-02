package com.purplefront.jiro_dev;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.model.ExistingUser_Model;
import com.purplefront.jiro_dev.parsers.ExistingUser_JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class LoginActivity extends Activity {

    EditText et1,et2;
    Button login;
    TextView forgotpassword;
    String username = "", password = "", email = "";
    CheckBox ch;
    ProgressDialog progress;
    List<ExistingUser_Model> feedslist;
    private String tag_string_req_recieve2 = "string_req_recieve2";
    String user_id = "", user_email = "", business_id = "",status = "";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        progress = new ProgressDialog(LoginActivity.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        et1 = (EditText)findViewById(R.id.phoneno);
        et2 = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        forgotpassword = (TextView)findViewById(R.id.forpass);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.

            if (name.contains("0")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user_id", "1");
                intent.putExtra("email", email);
                intent.putExtra("redirection","redirection");
                LoginActivity.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = et1.getText().toString();
                email = username;
                password = et2.getText().toString();
                validationfunction();



            }
        });



        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Reset_password.class);
                LoginActivity.this.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });




        ch = (CheckBox) findViewById(R.id.existinguser_checkbox);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("lee", "checkbok");
                if (isChecked) {
                    et2.setTransformationMethod(null);
                    ch.setText(R.string.hide_password);
                } else {
                    et2.setTransformationMethod(new PasswordTransformationMethod());
                    ch.setText(R.string.show_password);
                }
            }
        });


    }

    protected boolean isonline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }

    public void validationfunction() {
        boolean invalid = false;
        if (username.equals("") || username.isEmpty() || username.trim().isEmpty()) {
            invalid = true;
            et1.setError(getResources().getString(R.string.correct_user_name));
            Toast.makeText(LoginActivity.this, R.string.correct_user_name, Toast.LENGTH_LONG).show();
        } else if (username.length() < 6) {
            invalid = true;
            Toast.makeText(LoginActivity.this, R.string.username_minimum_limit, Toast.LENGTH_LONG).show();
        } else if (password.equals("") || password.isEmpty() || password.trim().isEmpty()) {
            invalid = true;
            et2.setError(getResources().getString(R.string.correct_password));
            Toast.makeText(LoginActivity.this, R.string.correct_password, Toast.LENGTH_LONG).show();
        } else if (password.length() < 6) {
            invalid = true;
            et2.setError(getResources().getString(R.string.password_limit_correct));
            Toast.makeText(LoginActivity.this, R.string.password_limit_correct, Toast.LENGTH_LONG).show();
        } else if (password.trim().length() < 6) {
            invalid = true;
            et2.setError(getResources().getString(R.string.password_limit_correct));
            Toast.makeText(LoginActivity.this, R.string.password_limit_correct, Toast.LENGTH_LONG).show();
        } else {
            if (invalid == false) {
                if (password.trim().equals(password)) {
                    if (username.trim().equals(username)) {
                        if (isonline()) {
                            progress.show();
                            final String alertchoose = "2";
                            try {
                                MCrypt mcrypt = new MCrypt();
                                password = MCrypt.bytesToHex(mcrypt.encrypt(password));
                                Log.d("password",password);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            StringRequest request = new StringRequest(Request.Method.POST, getResources().getString(R.string.url_reference) + "home/login.php",

                                    new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String arg0) {


                                            try {
                                                Log.d("JSONRES","JSONRES"+ arg0);
                                                Log.d("here in sucess", "sucess");
                                                feedslist = ExistingUser_JSONParser.parserFeed(arg0);
                                                Log.v("FLIST","FLIST"+feedslist.size());
                                                progress.hide();
                                                updatedisplay();
                                            }catch (NoClassDefFoundError e){
                                                e.printStackTrace();
                                            }
                                        }
                                    },


                                    new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError arg0) {
                                            progress.hide();
                                            Log.d(TAG, "Error: " + arg0.getMessage());
                                            Toast.makeText(LoginActivity.this, R.string.nointernetaccess, Toast.LENGTH_LONG).show();
                                            Toast.makeText(LoginActivity.this, arg0.getMessage(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            LoginActivity.this.finish();
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                        }
                                    }) {

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("email", username);
                                    params.put("password", password);
                                    return params;
                                }

                                ;
                            };
                            AppController.getInstance().addToRequestQueue(request, tag_string_req_recieve2);
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.nointernetconnection, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, R.string.correct_username_without_space, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.password_space, Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(LoginActivity.this, R.string.password_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updatedisplay() {
        progress.hide();
        //	Toast.makeText(Existinguser.this,"update display",Toast.LENGTH_LONG).show();
        if (feedslist != null) {
            for (final ExistingUser_Model flower : feedslist) {
                String success = flower.getMessage();
                user_email = email;
                user_id = flower.getId();
                business_id = flower.getBusiness_id();
                status = flower.getActive();
                //  phone= flower.get();

                Log.v("SUCRES","SUCRES"+success);
                switch (success) {
                    case "Existing Profile":



                        if (flower.getActive().contains("0"))

                        {

//                            try {
//                                if (checkPlayServices()) {
//                                    gcm = GoogleCloudMessaging.getInstance(Existinguser.this);
//                                    regid = getRegistrationId(Existinguser.this);
//                                    StaticVariable.regid=regid;
//                                    //    Log.v("reg_id",regid);
//                                    //     Toast.makeText(Existinguser.this,regid,Toast.LENGTH_SHORT).show();
//                                    if (regid.isEmpty()) {
//                                        registerInBackground();
//                                    } else {
//                                        sendRegistrationIdToBackend();
//                                    }
//
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                            dbhelp entry = new dbhelp(LoginActivity.this);
                            entry.open();
                            entry.createuser(flower.getId(), flower.getBusiness_id(), email, password, flower.getName(), flower.getContact());
                            entry.close();
                            if (flower.getProfile_id().equals("1")) {

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, "0");
                                editor.commit();

                                Log.d("LBID","LBID"+flower.getBusiness_id());
                                Log.d("LUID","LUID"+flower.getId());
                                Log.d("LEMAIL","LEMAIL"+email);
                                Log.d("LRID","LRID"+flower.getProfile_id());
                                Log.d("LNAME","LNAME"+flower.getName());
                                Log.d("LCon","LCon"+flower.getContact());




                                StaticVariable.business_id=flower.getBusiness_id();
                                StaticVariable.user_id=flower.getId();
                                StaticVariable.email=email;
                                StaticVariable.role_id=flower.getProfile_id();
                                StaticVariable.name = flower.getName();
                                StaticVariable.contact = flower.getContact();


                                Log.d("SLBID","SLBID"+StaticVariable.business_id);
                                Log.d("SLUID","SLUID"+StaticVariable.user_id);
                                Log.d("SLEMAIL","SLEMAIL"+StaticVariable.email);
                                Log.d("SLRID","SLRID"+StaticVariable.role_id);
                                Log.d("SLNAME","SLNAME"+StaticVariable.name);
                                Log.d("SLCon","SLCon"+StaticVariable.contact);



                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", flower.getId());
                                intent.putExtra("business_id", flower.getBusiness_id());
                                intent.putExtra("email", email);
                                intent.putExtra("redirection","redirection");
                                //intent.putExtra("roles", flower.getProfile_id());

                                LoginActivity.this.finish();
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                            } else if (flower.getProfile_id().equals("3")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                                Log.d("LBID1","LBID1"+flower.getBusiness_id());
                                Log.d("LUID1","LUID1"+flower.getId());
                                Log.d("LEMAIL1","LEMAIL1"+email);
                                Log.d("LRID1","LRID1"+flower.getProfile_id());
                                Log.d("LNAME1","LNAME1"+flower.getName());
                                Log.d("LCon1","LCon1"+flower.getContact());

                                StaticVariable.business_id=flower.getBusiness_id();
                                StaticVariable.user_id=flower.getId();
                                StaticVariable.email=email;
                                StaticVariable.role_id=flower.getProfile_id();
                                StaticVariable.name = flower.getName();
                                StaticVariable.contact = flower.getContact();


                                intent.putExtra("user_id", flower.getId());
                                intent.putExtra("business_id", flower.getBusiness_id());
                                intent.putExtra("email", email);
                                intent.putExtra("redirection","redirection");
                                LoginActivity.this.finish();
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                            }
                        }
                        else {

                            Toast.makeText(LoginActivity.this,R.string.account_inactivate,Toast.LENGTH_LONG).show();
                        }


                        break;
                    case "Wrong Password":
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle(R.string.dialog_password_wrong_title)
                                .setMessage(R.string.dialog_password_wrong_message)
                                .setPositiveButton(R.string.dialog_password_wrong_button, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(LoginActivity.this, Reset_password.class);
                                        LoginActivity.this.finish();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                    }
                                })
                                .setNegativeButton(R.string.dialog_password_wrong_button2, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        Toast.makeText(LoginActivity.this, R.string.email_password, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                        builder1.setTitle(R.string.dialog_password_wrong_title)
                                .setMessage(R.string.dialog_password_wrong_message)
                                .setPositiveButton(R.string.dialog_password_wrong_button, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(LoginActivity.this, Reset_password.class);
                                        LoginActivity.this.finish();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                    }
                                })
                                .setNegativeButton(R.string.dialog_password_wrong_button2, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();

                        break;
                }
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle(R.string.dialog_password_wrong_title)
                    .setMessage(R.string.dialog_password_wrong_message)
                    .setPositiveButton(R.string.dialog_password_wrong_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LoginActivity.this, Reset_password.class);
                            LoginActivity.this.finish();
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        }
                    })
                    .setNegativeButton(R.string.dialog_password_wrong_button2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
