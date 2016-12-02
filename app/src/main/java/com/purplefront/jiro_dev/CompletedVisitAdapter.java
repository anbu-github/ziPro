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

import com.purplefront.jiro_dev.model.CustomerVisit;

import java.util.List;

/**
 * Created by pf-05 on 11/29/2016.
 */

public class CompletedVisitAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int count,pos;
    private ArrayAdapter<String> adapter;
    List<CustomerVisit> person;


    public CompletedVisitAdapter(Activity context, List<CustomerVisit> person) {
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
        final CompletedVisitAdapter.ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.cust_visit_complete_adapter, null);

        pos=position;

        holder = new CompletedVisitAdapter.ViewHolder();
        holder.customer_name = (TextView) convertView.findViewById(R.id.name);
        holder.customer_contact = (TextView) convertView.findViewById(R.id.contact);
        holder.visit_date = (TextView) convertView.findViewById(R.id.memrole);
        holder.cv = (CardView)convertView.findViewById(R.id.cardvw);


        holder.customer_name.setText(person.get(position).getCustomer());
        holder.customer_contact.setText(person.get(position).getCustomer_contact());
        holder.visit_date.setText(person.get(position).getVisit_date());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent in = new Intent(context, SchedulelistDisplay.class);
//                StaticVariable.customer_visit_id = person.get(position).getId();
//                context.startActivity(in);
//                Activity activity = (Activity) context;
//                activity.finish();

                Intent in = new Intent(context,CompletedVisitDisplay.class);
                StaticVariable.customer_visit_id = person.get(position).getId();
                context.startActivity(in);
                Activity activity = (Activity) context;
                activity.finish();

            }
        });


        return convertView;
    }



    private void initializeViews(String object, CompletedVisitAdapter.ViewHolder holder) {
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
        TextView customer_name,customer_contact,visit_date;

    }

}
