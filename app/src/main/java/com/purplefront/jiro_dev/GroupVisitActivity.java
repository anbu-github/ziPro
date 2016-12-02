package com.purplefront.jiro_dev;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 11/25/2016.
 */

public class GroupVisitActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String user_id = "";
    Intent intent;
    //String redirection = "";
    String id="",name="",redirection="";



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupvisit);

        intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        redirection =  intent.getStringExtra("redirection");


        Log.d("S23","S23"+id);
        Log.d("S24","S24"+name);

        StaticVariable.catalognamedisp = name;

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Visitors" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setSubtitle("View Members");
        getSupportActionBar().setIcon(R.drawable.pf);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        Bundle bundle = new Bundle();
//        bundle.putString("id",id);
//        bundle.putString("name",name);
        adapter.addFragment(new SheduledVisits(), "Sheduled");
        adapter.addFragment(new CompletedVisits(), "Completed");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                //  onBack();
                Intent intent = new Intent(GroupVisitActivity.this, SubCategory.class);
                intent.putExtra("redirection","Catalog");
                Log.d("BAKID","BAKID"+StaticVariable.groupid);
                intent.putExtra("id",StaticVariable.groupid);
                intent.putExtra("name",name);
                startActivity(intent);
                GroupVisitActivity.this.finish();
                GroupVisitActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                return true;
            }


            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GroupVisitActivity.this, SubCategory.class);
        intent.putExtra("redirection","Catalog");
        Log.d("BAKID","BAKID"+StaticVariable.groupid);
        intent.putExtra("id",StaticVariable.groupid);
        intent.putExtra("name",name);
        startActivity(intent);
        GroupVisitActivity.this.finish();
        GroupVisitActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
