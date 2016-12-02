package com.purplefront.jiro_dev;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purplefront.jiro_dev.model.Create_Address_Model1;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pf-05 on 11/28/2016.
 */

public class Address_Section_Adaptor3 extends RecyclerView.Adapter<Address_Section_Adaptor3.ViewHolder> {

    private Context context;
    List<Create_Address_Model1> feedslist;
    private LayoutInflater layoutInflater;
//    private static int lastCheckedPos = 0;
//    private static CheckBox lastChecked = null;

    public Address_Section_Adaptor3(Context context, List<Create_Address_Model1> feedslist)
    {
        this.context = context;
        this.feedslist = feedslist;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cust_member_adaptor, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Create_Address_Model1 nature = feedslist.get(i);


        if(nature.getAddressline1().equals(""))
        {
            viewHolder.address1.setVisibility(View.GONE);
        }
        else {
            viewHolder.address1.setText(nature.getAddressline1());
        }

        if(nature.getAddressline2().equals(""))
        {
            viewHolder.address2.setVisibility(View.GONE);
        }
        else {
            viewHolder.address2.setText(nature.getAddressline2());
        }

        if(nature.getAddressline3().equals(""))
        {
            viewHolder.address3.setVisibility(View.GONE);

        }
        else {
            viewHolder.address3.setText(nature.getAddressline3());
        }

        viewHolder.address4.setText(nature.getCity());
        viewHolder.address5.setText(nature.getState());
        viewHolder.address6.setText(nature.getZipcode());


        try
        {
            viewHolder.cb.setChecked(nature.getSelected());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





        try {
            if (i== StaticVariable.cbPosition) {
                viewHolder.cb.setChecked(true);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        viewHolder.cb.setTag(new Integer(i));

        //for default check in first item

        try
        {
            if(i == 0 && feedslist.get(0).getSelected() && viewHolder.cb.isChecked())
            {
                StaticVariable.lastChecked = viewHolder.cb;
                StaticVariable.lastCheckedPos = 0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();
                StaticVariable.cbPosition=clickedPos;


                if (cb.isChecked()) {
                    if (StaticVariable.lastChecked != null) {
                        StaticVariable.lastChecked.setChecked(false);
                        feedslist.get(StaticVariable.lastCheckedPos).setSelected(false);
                    }

                    StaticVariable.lastChecked = cb;
                    StaticVariable.lastCheckedPos = clickedPos;
                }
                else
                    StaticVariable.lastChecked = null;

                feedslist.get(clickedPos).setSelected(cb.isChecked());
            }
        });




         try
         {
             viewHolder.cv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

    }

    @Override
    public int getItemCount() {

        return feedslist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public TextView address1,address2,address3,address4,address5,address6;
        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            address1 = (TextView)itemView.findViewById(R.id.add1);
            address2 = (TextView)itemView.findViewById(R.id.add3);
            address3 = (TextView)itemView.findViewById(R.id.address_line3);
            address4 = (TextView)itemView.findViewById(R.id.add2);
            address5 = (TextView)itemView.findViewById(R.id.state);
            address6 = (TextView)itemView.findViewById(R.id.zipcode);
            cb = (CheckBox)itemView.findViewById(R.id.checkBox44);
        }
    }
}
