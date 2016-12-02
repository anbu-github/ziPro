package com.purplefront.jiro_dev.model;

import com.purplefront.jiro_dev.viewholder.AddressViewHolder;

/**
 * Created by apple on 05/11/16.
 */

public class AddressSelection {
    private int mPosition=0;
    private AddressViewHolder.Address address;
    private boolean isChecked=false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public AddressViewHolder.Address getAddress() {
        return address;
    }

    public void setAddress(AddressViewHolder.Address address) {
        this.address = address;
    }
}
