package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.CheckBox;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.purplefront.jiro_dev.model.AddressSelection;
import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
import com.purplefront.jiro_dev.model.Create_Address_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 25/10/16.
 */

public class Address_Section_Adaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static CheckBox lastChecked = null;
    private static CheckBox lastChecked1 = null;
    List<Create_Address_Model> mItems;
    Integer cbPos = 0, cbPos1 = 0, selctedPos = 0;
    ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();
    ArrayList<CheckBox> mCheckBoxes1 = new ArrayList<CheckBox>();
    private Context mContext;
    private Integer selected_position = -1;
    private Integer selected_position1 = -1;
    private AddressSelection mShippingAddress, mBillingAddress;

    //Address_Section_Adaptor.DataFromAdapterToActivity dataFromAdapterToActivity;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

    public Address_Section_Adaptor(List<Create_Address_Model> mItems, Context c) {
        this.mContext = c;
        this.mItems = mItems;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.select_address_adpter, viewGroup, false);
        return new AddressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            updateAddressSelection(StaticVariable.billaddselection, true);
            updateAddressSelection(StaticVariable.shipaddselection, true);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (holder instanceof AddressViewHolder) {
            if (mItems != null && !mItems.isEmpty() && mItems.size() > position) {
                ((AddressViewHolder) holder).bindData(mItems.get(position), position);
                ((AddressViewHolder) holder).setValueCallBack(new ValueCallback<AddressSelection>() {

                    @Override
                    public void onReceiveValue(AddressSelection value) {
                        try {
                            unselectPreviousSelection(StaticVariable.shipaddselection);
                            unselectPreviousSelection(StaticVariable.billaddselection);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        unselectPreviousSelection(value);
                        updateAddressSelection(value, true);

                        notifyDataChanges();
                    }
                });
            }
        }
    }

    private void notifyDataChanges() {
        this.notifyDataSetChanged();
    }

    private void updateAddressSelection(AddressSelection value, boolean isCurrentSelection) {
        if (null != value && null != mItems && mItems.size() > value.getmPosition()) {
            if (value.getAddress().name().equals(AddressViewHolder.Address.BillingAddress.name())) {
                StaticVariable.billaddselection=value;
                mItems.get(value.getmPosition()).setBillCheck(isCurrentSelection ? value.isChecked() : false);
            } else {
                StaticVariable.shipaddselection=value;
                mItems.get(value.getmPosition()).setShipCheck(isCurrentSelection ? value.isChecked() : false);
            }
        }
    }

    private void unselectPreviousSelection(AddressSelection addressSelection) {
        //unselect previous selection of same address
        if (null != addressSelection) {
            switch (addressSelection.getAddress()) {
                case ShippingAddres:
                    if(null != mShippingAddress) {
                        updateAddressSelection(mShippingAddress, false);
                    }
                        mShippingAddress =addressSelection;
                    break;
                case BillingAddress:
                    if(null != mBillingAddress) {
                        updateAddressSelection(mBillingAddress, false);
                    }
                    mBillingAddress =addressSelection;
                    break;
            }
        }

    }


    @Override
    public int getItemCount() {

        return mItems.size();
    }



    public AddressSelection getShippingAddress() {
        return mShippingAddress;
    }

    public AddressSelection getBillingAddress() {
        return mBillingAddress;
    }
}
