package com.purplefront.jiro_dev;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.model.Home_Model;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.parsers.MainPage_JSONParser;

import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    String business_id = "";
    String user_id = "";
    String user_email = "";
    Stack<String> mFragPositionTitleDisplayed;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mPreviousBackStackCount = 0;
    ProgressDialog progress;
    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    List<Home_Model> feedlist;
    String tag_string_req = "string_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragPositionTitleDisplayed = new Stack<>();
        progress = new ProgressDialog(MainActivity.this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setTitle(R.string.please_wait);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setIndeterminate(true);
        progress.hide();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            user_id = getIntent().getExtras().getString("user_id");
            business_id = getIntent().getExtras().getString("business_id");
            user_email = getIntent().getExtras().getString("email");

            Log.v("UID","UID"+user_id);
            Log.v("BID","BID"+business_id);
            Log.v("UEM","UEM"+user_email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        try {
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];


        drawerItem[0] = new ObjectDrawerItem(R.drawable.catalog, "Catalog");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.profile_icon1, "View/Edit Profile");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.password_icon2, "Change Password");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.logout_icon1, "Logout");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.exit_icon1, "Exit");


        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_list_item, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setHomeButtonEnabled(true);



        mDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        selectItem(0);

        String redirection = "";

        try {
            redirection = getIntent().getExtras().getString("redirection");
            switch (redirection) {

                    case "View/Edit Profile": {
                    Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new ViewEditprofile();
                    Bundle args = new Bundle();
                    args.putString("user_id", StaticVariable.user_id);
                    args.putString("email", user_email);
                    args.putString("business_id", business_id);
                    test.setArguments(args);

                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("View/Edit Profile");
                    break;
                }

                case "Catalog": {
                    Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new HomeCategory();
                    Bundle args = new Bundle();
                    args.putString("user_id", user_id);
                    args.putString("email", user_email);
                    args.putString("business_id", business_id);
                    test.setArguments(args);

                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("My Groups");
                    break;
                }
            }
        }
        catch(Exception ignored)
        {
            ignored.printStackTrace();
            if (savedInstanceState == null) {
                selectItem(0);
            }
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Internet_Access ac = new Internet_Access();
        if (ac.isonline(MainActivity.this)) {
            progress.show();
            getdata(getResources().getString(R.string.url_reference) + "home/home_data.php");
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.nointernetconnection), Toast.LENGTH_LONG).show();
        }
    } //On-Create Finished

    private void selectItem(int position) {

        // update the main content by replacing fragments
        Fragment test;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        test = new HomeCategory();
        Bundle args = new Bundle();
        args.putString("user_id", user_id);
        args.putString("email", user_email);
        args.putString("business_id", business_id);
        test.setArguments(args);

        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.replace(R.id.content_frame, test);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setTitle("My Groups");

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
       // setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        /*case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void display_data()
    {
        progress.show();
        if (feedlist != null) {

            mAdapter = new GridAdapter(feedlist, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        }
        progress.hide();
    }

    private void getdata(String url) {
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progress.hide();
                Log.d("RESO","RESO"+ s);
                feedlist = MainPage_JSONParser.parserFeed(s);
                display_data();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progress.hide();
                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request, tag_string_req);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String planet = getResources().getStringArray(R.array.planets_array)[position];
            Log.d("PL","PL"+planet);
            //        selectItem(position);
            mDrawerLayout.closeDrawer(mDrawerList);
            switch (planet) {

                case "Catalog": {
                    Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new HomeCategory();
                    Bundle args = new Bundle();

                    Log.d("CAUID","CAUID"+user_id);
                    Log.d("CAUEMAIL","CAUEMAIL"+user_email);
                    Log.d("CABID","CABID"+business_id);


                    args.putString("user_id", user_id);
                    args.putString("email", user_email);
                    args.putString("business_id", business_id);
                    test.setArguments(args);

                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("My Groups");
                    break;
                }
                case "View/Edit Profile": {
                    Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new ViewEditprofile();
                    Bundle args = new Bundle();


//                    Log.d("VEPwd","VEPwd"+user_id);
//                    Log.d("VEEEMAIL","VEEEMAIL"+user_email);
//                    Log.d("VEBID","VEBID"+business_id);


                    args.putString("user_id", StaticVariable.user_id);
                    args.putString("email", user_email);
                    args.putString("business_id", business_id);
                    test.setArguments(args);

                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("View/Edit Profile");

                    //Toast.makeText(MainActivity.this,"Clicked View/Edit Profile",Toast.LENGTH_LONG).show();
                    break;
                }
                case "Change Password": {
                   Fragment test;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    test = new ChangePassword();
                    Bundle args = new Bundle();

                    Log.d("CPwd","CPwd"+StaticVariable.user_id);
                    Log.d("CPEMAIL","CPEMAIL"+user_email);
                    Log.d("CPBID","CPBID"+business_id);

                    args.putString("user_id", StaticVariable.user_id);
                    args.putString("email", user_email);
                    args.putString("business_id", business_id);
                    test.setArguments(args);
                    fragmentTransaction.setCustomAnimations(
                            R.animator.fragment_slide_left_enter,
                            R.animator.fragment_slide_left_exit,
                            R.animator.fragment_slide_right_enter,
                            R.animator.fragment_slide_right_exit);
                    fragmentTransaction.replace(R.id.content_frame, test);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    setTitle("Change Password");
                  break;
                }
                case "Logout": {

                    dbhelp entry= new dbhelp(MainActivity.this);
                    entry.open();
                    entry.logout_user();
                    entry.close();
                    Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                    MainActivity.this.finish();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

               /*     dbhelp entry= new dbhelp(MainActivity.this);
                    entry.open();
                    entry.logout_user();
                    entry.close();
                    Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                    MainActivity.this.finish();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
      */          }
                case "Exit": {
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                }
            }
        }
    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    public void onBackStackChanged() {
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        if(mPreviousBackStackCount >= backStackEntryCount) {
            mFragPositionTitleDisplayed.pop();
            if (backStackEntryCount == 0)
                getSupportActionBar().setTitle(R.string.app_name);
            else if (backStackEntryCount > 0) {
                getSupportActionBar().setTitle(mFragPositionTitleDisplayed.peek());
            }
            mPreviousBackStackCount--;
        }
        else{
            mFragPositionTitleDisplayed.push("test");
            mPreviousBackStackCount++;
        }
    }

}
