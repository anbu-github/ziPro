package com.purplefront.jiro_dev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.CustomerModel;

import java.util.List;

/**
 * Created by pf-05 on 12/1/2016.
 */

public class ApprovedCustomerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int count,pos;
    private ArrayAdapter<String> adapter;
    List<CustomerModel> person;


    public ApprovedCustomerAdapter(Activity context, List<CustomerModel> person) {
        this.context = context;
        this.person=person;
        this.layoutInflater = LayoutInflater.from(context);



    }
    @Override
    public int getCount() {
        return person.size();
    }

    @Override
    public String getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // if (convertView == null) {
        final ApprovedCustomerAdapter.ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.customer_approved_adpator, null);

        pos=position;

        holder = new ApprovedCustomerAdapter.ViewHolder();
        holder.customer_name = (TextView) convertView.findViewById(R.id.name);
        holder.customer_contact = (TextView) convertView.findViewById(R.id.contact);
        holder.cv = (CardView)convertView.findViewById(R.id.customercardvw);


        holder.customer_name.setText(person.get(position).getName());
        holder.customer_contact.setText(person.get(position).getContact());

//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent in = new Intent(context, SchedulelistDisplay.class);
//                StaticVariable.customer_visit_id = person.get(position).getId();
//                context.startActivity(in);
//                Activity activity = (Activity) context;
//                activity.finish();
//
//            }
//        });


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent(context,CustMembersProfileView.class);
//                in.putExtra("cust_segid",StaticVariable.customer_segment_id);
//                startActivity(intent);
//                Customermemlist.this.finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                Intent in = new Intent(context, CustMembersProfileView1.class);
                StaticVariable.customer_segment_cust_id = person.get(position).getId();
                context.startActivity(in);
                Activity activity = (Activity) context;
                activity.finish();

            }
        });


        return convertView;
    }



    private void initializeViews(String object, ApprovedCustomerAdapter.ViewHolder holder) {
        //TODO implement
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'inflate_assignmentlist.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    private class ViewHolder {

        CardView cv;
        TextView customer_name,customer_contact;

    }

}
