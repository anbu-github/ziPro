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
 * Created by pf-05 on 11/30/2016.
 */

public class CustomerFragActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String user_id = "";
    Intent intent;
    //String redirection = "";
    String cust_segid="", name="", redirection="",cust_id= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_fragment);


        intent = getIntent();
        cust_segid = intent.getStringExtra("id");
        name = intent.getStringExtra("name");


        StaticVariable.grpdisplay_backcustbtn = name;

        StaticVariable.customer_segment_id = cust_segid;

        Log.v("STRAFD1","STRAFD1"+StaticVariable.customer_segment_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + "Customers" + "</font>")));
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

        adapter.addFragment(new PendingApprovalVisit(), "Pending Approval");
        adapter.addFragment(new CustomerApprovalVisit(), "Approved Customer");

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

    public void goback()
    {
        Intent intent = new Intent(CustomerFragActivity.this, Customerlist.class);
        intent.putExtra("redirection","Catalog");
        intent.putExtra("id",StaticVariable.customer_segment_id);
        intent.putExtra("name",name);
        startActivity(intent);
        CustomerFragActivity.this.finish();

        CustomerFragActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                goback();
                return true;
            }


            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goback();
    }
}
