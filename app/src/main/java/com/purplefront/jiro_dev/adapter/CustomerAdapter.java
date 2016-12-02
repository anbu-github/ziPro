package com.purplefront.jiro_dev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.StaticVariable;
import com.purplefront.jiro_dev.listener.CustomerVisitListener;
import com.purplefront.jiro_dev.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class CustomerAdapter extends ArrayAdapter<String> {
    private List<Customer> mCustomers = new ArrayList<>();
    private final int mResource;
    private final CustomerVisitListener mActivityListener;
    private ViewHolder viewHolder;

    public CustomerAdapter(Context context, int resource, List<String> customersVal,
                           List<Customer> customers, CustomerVisitListener mActivityListener) {
        super(context, resource, customersVal);
        this.mCustomers = customers;
        this.mResource = resource;
        this.mActivityListener = mActivityListener;
    }

    public Customer getCustomerByPosition(int position) {
        return mCustomers.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.spinnerTarget);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Customer item = getCustomerByPosition(position);
        if (item != null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getCustomerName());
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemView;
    }
}
