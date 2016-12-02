package com.purplefront.jiro_dev;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.purplefront.jiro_dev.model.Databaseaccess;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;

/**
 * Created by apple on 04/10/16.
 */

public class ViewEditprofile extends Fragment {

    String name = "",email = "", phone = "", user_id = "";
    String id = "";
    TextView nametv,emailtv,phonetv;


    View view;
    List<Databaseaccess> database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.view_profile1, container, false);
        setHasOptionsMenu(true);



//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        getActivity().getActionBar().setTitle("Profile");


        nametv = (TextView) view.findViewById(R.id.viewedit_name);
        emailtv = (TextView) view.findViewById(R.id.viewedit_email);
        phonetv = (TextView) view.findViewById(R.id.viewedit_phone);


            Bundle args = getArguments();
            try {
                user_id = args.getString("user_id");

                Log.d("VID","VID"+user_id);

            } catch (Exception e) {
                e.printStackTrace();
            }


        try
        {
            SQLiteDatabase db2 = getActivity().openOrCreateDatabase("jiro", MODE_PRIVATE, null);
            Cursor c = db2.rawQuery("SELECT * FROM user_master", null);
            c.moveToFirst();
             name = c.getString(c.getColumnIndex("name"));
             email = c.getString(c.getColumnIndex("email"));
             phone = c.getString(c.getColumnIndex("phone"));


            Log.v("DID","DID"+id);
            Log.v("Demail","Demail"+email);
            Log.v("Dphone","Dphone"+phone);

            c.close();
            db2.close();
        }

        catch(Exception e)
        {
            Log.d("Exception : ", "" + e);
            Log.d("exception", "user does not exist");
        }

//        if(profile_id.equals("3")) {
//            Bundle args = getArguments();
//            try {
//                school_name = args.getString("school_name");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        nametv.setText(name);
        emailtv.setText(email);
        phonetv.setText(phone);


        return view;

    }

    protected boolean isonline()
    {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        invalidateOptionsMenu(getActivity());
        inflater.inflate(R.menu.viewedit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().getActionBar().setTitle("Profile");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.edit:
                if(isonline())
                {

                    Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new Editprofile();
                    Bundle args = new Bundle();


                    args.putString("user_id",StaticVariable.user_id);
                    args.putString("profile_name",name);
                    args.putString("email",email);
                    args.putString("phone",phone);

                    test.setArguments(args);
                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getActivity().setTitle("Edit Profile");




//                    Intent intent=new Intent(getActivity(),Editprofile.class);
//                    intent.putExtra("user_id",user_id);
//                    intent.putExtra("profile_name",name);
//                    intent.putExtra("email",email);
//                    intent.putExtra("phone",phone);
//                    getActivity().finish();
//                    startActivity(intent);
////                    getActivity().overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.nointernetconnection, Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return true;
        }
    }



}
