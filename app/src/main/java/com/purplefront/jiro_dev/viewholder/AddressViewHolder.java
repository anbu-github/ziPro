package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.AddressSelection;
import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.model.Create_Address_Model;

/**
 * Created by apple on 05/11/16.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

    private CardView cv;
    private CheckBox cb, cb1;
    private TextView address1, address2, address3, city, state, zipcode;
    private int mPosition;
    private ValueCallback<AddressSelection> mValueCallBack;
    private boolean isDataBinding = true;

    public AddressViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv_category);

        address1 = (TextView) itemView.findViewById(R.id.add1);

        address2 = (TextView) itemView.findViewById(R.id.add3);

        //address3 = (TextView) itemView.findViewById(R.id.address_line3);

        //city = (TextView) itemView.findViewById(R.id.add2);

        //state = (TextView) itemView.findViewById(R.id.state);

        //zipcode = (TextView) itemView.findViewById(R.id.zipcode);

        cb = (CheckBox) itemView.findViewById(R.id.checkBox);

        cb1 = (CheckBox) itemView.findViewById(R.id.checkBox1);

        cb.setOnCheckedChangeListener(this);
        cb1.setOnCheckedChangeListener(this);

    }

    public void bindData(Create_Address_Model address, int position) {
        mPosition = position;
        if (null != address) {
            isDataBinding = true;
            handleDataandVisibility(address.getAddressline1() + ","+ address.getAddressline2() + " ," + address.getAddressline3(), address1);
            handleDataandVisibility(address.getCity() + " , "+address.getState() + " , "+address.getPincode(), address2);
//            handleDataandVisibility(address.getAddressline3(), address3);
//            handleDataandVisibility(address.getCity(), city);
//            handleDataandVisibility(address.getState(), state);
//            handleDataandVisibility(address.getPincode(), zipcode);


            Log.v("ISBCHECK","ISBCHECK"+address.isBillCheck());

            Log.v("ISCHECK","ISCHECK"+address.isShipCheck());



            cb.setChecked(address.isShipCheck());
            cb1.setChecked(address.isBillCheck());
            isDataBinding = false;
        }

    }

    public void bindData(Create_Address_Model address, int position, boolean checkboxVisibility) {
        if (!checkboxVisibility) {
            cb.setVisibility(View.GONE);
            cb1.setVisibility(View.GONE);
        }
        bindData(address, position);
    }


    private void handleDataandVisibility(String value, TextView view) {
        if (!TextUtils.isEmpty(value)) {
            view.setVisibility(View.VISIBLE);
            view.setText(value);
        } else {
            view.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBox:
                //
                handleAddressSelection(isChecked, Address.ShippingAddres);

                break;
            case R.id.checkBox1:
                handleAddressSelection(isChecked, Address.BillingAddress);
                break;
        }
    }


    private void handleAddressSelection(boolean isChecked, Address address) {
        AddressSelection addressSelection = new AddressSelection();
        addressSelection.setAddress(address);
        addressSelection.setChecked(isChecked);
        addressSelection.setmPosition(mPosition);

        if (null != mValueCallBack && !isDataBinding) {
            mValueCallBack.onReceiveValue(addressSelection);
        }
    }

    public void setValueCallBack(ValueCallback<AddressSelection> valueCallBack) {
        this.mValueCallBack = valueCallBack;
    }

    public enum Address {
        ShippingAddres,
        BillingAddress
    }
}