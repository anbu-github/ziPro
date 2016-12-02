package com.purplefront.jiro_dev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.Create_Address_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class Address_Section_Adaptor1 extends BaseAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    private int count;
    private ArrayAdapter<String> adapter;
    List<Create_Address_Model> person;
    ArrayList<String> editQuantityList = new ArrayList<>();
    ArrayList<String> editQuantityList1 = new ArrayList<>();
    public  Address_Section_Adaptor1 (Context context,List<Create_Address_Model> person) {
        this.context = context;
        this.person = person;
        this.layoutInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return count;
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
        final ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.select_address_custvist, null);
            /*ViewHolder viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
*/
        holder = new ViewHolder();

        try {
            holder.cv = (CardView) convertView.findViewById(R.id.select_address);
            holder.address1 = (TextView) convertView.findViewById(R.id.add1);
            holder.address2 = (TextView) convertView.findViewById(R.id.add3);
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox);

            holder.address1.setText(person.get(position).getAddressline1() + person.get(position).getAddressline2()+ person.get(position).getAddressline3());
            holder.address2.setText(person.get(position).getCity()+person.get(position).getState()+person.get(position).getPincode());


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder) {
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
        public TextView address1,address2;
        public CheckBox cb;
    }


}
