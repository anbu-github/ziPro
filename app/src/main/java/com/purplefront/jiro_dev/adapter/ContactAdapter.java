package com.purplefront.jiro_dev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.model.Contact;

import java.util.List;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    private List<Contact> mContacts;
    private final int mResource;
    private ViewHolder viewHolder;

    public ContactAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.mContacts = contacts;
        this.mResource = resource;
    }

    public void updateData(List<Contact> contacts) {
        this.mContacts = contacts;
        notifyDataSetChanged();
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

        Contact item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getContactName());
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemView;
    }
}
