package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.Create_Address_Model;

import java.util.List;

/**
 * Created by apple on 18/10/16.
 */

public class Cust_Add_Adaptor extends RecyclerView.Adapter<Cust_Add_Adaptor.ViewHolder>{

    String from;
    private Context mContext;
    List<Create_Address_Model> mItems;


    public Cust_Add_Adaptor(List<Create_Address_Model> mItems, Context c, String from){
        this.mContext = c;
        this.mItems = mItems;
        this.from = from;

    }

    @Override
    public Cust_Add_Adaptor.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_member_adaptor, viewGroup, false);
        Cust_Add_Adaptor.ViewHolder viewHolder = new Cust_Add_Adaptor.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(Cust_Add_Adaptor.ViewHolder viewHolder, int i) {
        final Create_Address_Model nature = mItems.get(i);

        if(nature.getAddressline1().equals(""))
        {
            //viewHolder.address1.setText(" ");
            viewHolder.address1.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.address1.setText(nature.getAddressline1());
            viewHolder.address1.setVisibility(View.VISIBLE);
        }

        if(nature.getAddressline2().equals(""))
        {
            //viewHolder.address2.setText(" ");
            viewHolder.address2.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.address2.setText(nature.getAddressline2());
            viewHolder.address2.setVisibility(View.VISIBLE);
        }

        if(nature.getAddressline3().equals(""))
        {
            //viewHolder.address3.setText(" ");
            viewHolder.address3.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.address3.setText(nature.getAddressline3());
            viewHolder.address3.setVisibility(View.VISIBLE);
        }

        if(nature.getCity().equals(""))
        {
            viewHolder.address4.setText(" ");
        }
        else
        {
            viewHolder.address4.setText(nature.getCity());
        }
        if(nature.getState().equals(""))
        {
            viewHolder.address5.setText(" ");
        }
        else {
            viewHolder.address5.setText(nature.getState());
        }
        if(nature.getPincode().equals(""))
        {
            viewHolder.address6.setText(" ");
        }
        else {
            viewHolder.address6.setText(nature.getPincode());
        }
        //viewHolder.address1.setText(nature.getAddressline1());
        //viewHolder.address2.setText(nature.getAddressline2());
        //viewHolder.address3.setText(nature.getAddressline3());
        //viewHolder.address4.setText(nature.getCity());
        //viewHolder.address5.setText(nature.getState());
        //viewHolder.address6.setText(nature.getPincode());

        //dataFromAdapterToActivity = (Cust_Add_Adaptor.DataFromAdapterToActivity) mContext;


        viewHolder.checkBox.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public TextView address1,address2,address3,address4,address5,address6;
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            address1 = (TextView)itemView.findViewById(R.id.add1);
            address2 = (TextView)itemView.findViewById(R.id.add3);
            address3 = (TextView)itemView.findViewById(R.id.address_line3);
            address4 = (TextView)itemView.findViewById(R.id.add2);
            address5 = (TextView)itemView.findViewById(R.id.state);
            address6 = (TextView)itemView.findViewById(R.id.zipcode);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox44);
        }
    }
}
