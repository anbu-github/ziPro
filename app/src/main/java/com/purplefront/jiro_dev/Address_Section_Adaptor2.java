package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.purplefront.jiro_dev.model.AddressSelection;
import com.purplefront.jiro_dev.model.Create_Address_Model;
import com.purplefront.jiro_dev.model.Create_Address_Model1;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pf-05 on 11/28/2016.
 */

public class Address_Section_Adaptor2 extends RecyclerView.Adapter<Address_Section_Adaptor2.ViewHolder> {

    private Context mContext;
    List<Create_Address_Model1> mItems;
    Integer cbPos=0,selctedPos=0;
    String address1,address2,address3,city,state,zipcode;


    public Address_Section_Adaptor2(List<Create_Address_Model1> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
        Collections.reverse(this.mItems);
        cbPos = StaticVariable.cbpos;

        for (int i=0;i<mItems.size();i++){
            StaticVariable.addressIdList.add(i,"id");
        }
        Log.v("idsize",StaticVariable.addressIdList.size()+"personsize"+mItems.size());

    }

    @Override
    public Address_Section_Adaptor2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_member_adaptor, viewGroup, false);
        Address_Section_Adaptor2.ViewHolder viewHolder = new Address_Section_Adaptor2.ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final Address_Section_Adaptor2.ViewHolder viewHolder, final int i) {

        Create_Address_Model1 nature = mItems.get(i);


        selctedPos=i;
        address1=mItems.get(i).getAddressline1();
        address2=mItems.get(i).getAddressline2();
        address3=mItems.get(i).getAddressline3();
        city = mItems.get(i).getCity();
        state= mItems.get(i).getState();
        zipcode=mItems.get(i).getZipcode();


        Log.v("checked","here");



        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.isSelected=true;
            }
        });

        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  Log.v("ischecked", isChecked + "");
                //  Toast.makeText(mContext, isChecked + "", Toast.LENGTH_SHORT).show();
                mItems.get(i).setIsChecked(isChecked);

            }
        });

        try {
            viewHolder.cb.setChecked(mItems.get(i).getIsChecked());

        }catch (Exception e){
            e.printStackTrace();
        }


        if (address1.equals("")||address1.isEmpty()||address1.equals(null)){
            viewHolder.address1.setVisibility(View.GONE);
        }else {
            viewHolder.address1.setVisibility(View.VISIBLE);
            viewHolder.address1.setText(address1);
        }

        if (address2.equals("")||address2.isEmpty()||address2.equals(null)){
            viewHolder.address2.setVisibility(View.GONE);
        }else {
            viewHolder.address2.setVisibility(View.VISIBLE);

            viewHolder.address2.setText(address2);
        }

        if (address3.equals("")||address3.isEmpty()||address3.equals(null)){
            viewHolder.address3.setVisibility(View.GONE);
        }else {
            viewHolder.address3.setVisibility(View.VISIBLE);

            viewHolder.address3.setText(address3);
        }

        viewHolder.address2.setText(address2);
        viewHolder.address3.setText(address3);
        viewHolder.address4.setText(city);
        viewHolder.address5.setText(zipcode);
        viewHolder.address6.setText(state);





//        if(nature.getAddressline1().equals(""))
//        {
//            //viewHolder.address1.setText(" ");
//            viewHolder.address1.setVisibility(View.GONE);
//        }
//        else
//        {
//            viewHolder.address1.setText(nature.getAddressline1());
//            viewHolder.address1.setVisibility(View.VISIBLE);
//        }
//
//        if(nature.getAddressline2().equals(""))
//        {
//            //viewHolder.address2.setText(" ");
//            viewHolder.address2.setVisibility(View.GONE);
//        }
//        else
//        {
//            viewHolder.address2.setText(nature.getAddressline2());
//            viewHolder.address2.setVisibility(View.VISIBLE);
//        }
//
//        if(nature.getAddressline3().equals(""))
//        {
//            //viewHolder.address3.setText(" ");
//            viewHolder.address3.setVisibility(View.GONE);
//        }
//        else
//        {
//            viewHolder.address3.setText(nature.getAddressline3());
//            viewHolder.address3.setVisibility(View.VISIBLE);
//        }
//
//        if(nature.getCity().equals(""))
//        {
//            viewHolder.address4.setText(" ");
//        }
//        else
//        {
//            viewHolder.address4.setText(nature.getCity());
//        }
//        if(nature.getState().equals(""))
//        {
//            viewHolder.address5.setText(" ");
//        }
//        else {
//            viewHolder.address5.setText(nature.getState());
//        }
//        if(nature.getZipcode().equals(""))
//        {
//            viewHolder.address6.setText(" ");
//        }
//        else {
//            viewHolder.address6.setText(nature.getZipcode());
//        }

    }
    @Override
    public int getItemCount() {

        Log.d("S45","S45"+mItems.size());

        return mItems.size();


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
            cb = (CheckBox)itemView.findViewById(R.id.checkBox);
        }
    }
}
