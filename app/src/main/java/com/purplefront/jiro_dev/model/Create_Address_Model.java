package com.purplefront.jiro_dev.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apple on 18/10/16.
 */

public class Create_Address_Model implements Parcelable{


    String Addressline1;
    String Addressline2;
    public Create_Address_Model(){

    }


    public static final Creator<Create_Address_Model> CREATOR = new Creator<Create_Address_Model>() {
        @Override
        public Create_Address_Model createFromParcel(Parcel in) {
            return new Create_Address_Model(in);
        }

        @Override
        public Create_Address_Model[] newArray(int size) {
            return new Create_Address_Model[size];
        }
    };

    public String getAddressline1() {
        return Addressline1;
    }

    public void setAddressline1(String addressline1) {
        Addressline1 = addressline1;
    }

    public String getAddressline2() {
        return Addressline2;
    }

    public void setAddressline2(String addressline2) {
        Addressline2 = addressline2;
    }

    public String getAddressline3() {
        return Addressline3;
    }

    public void setAddressline3(String addressline3) {
        Addressline3 = addressline3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    String Addressline3;
    String city;
    String state;
    String pincode;
    String id;

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    String stateid;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String country;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    Boolean isChecked=false;



    private boolean isBillCheck,isShipCheck;

    public boolean isBillCheck() {
        return isBillCheck;
    }

    public void setBillCheck(boolean billCheck) {
        isBillCheck = billCheck;
    }

    public boolean isShipCheck() {
        return isShipCheck;
    }

    public void setShipCheck(boolean shipCheck) {
        isShipCheck = shipCheck;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Addressline1);
        dest.writeString(Addressline2);
        dest.writeString(Addressline3);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(pincode);
        dest.writeString(id);
        dest.writeString(stateid);
        dest.writeString(country);
        dest.writeByte((byte)(isBillCheck?1:0));
        dest.writeByte((byte)(isShipCheck?1:0));
    }

    protected Create_Address_Model(Parcel in) {
        Addressline1 = in.readString();
        Addressline2 = in.readString();
        Addressline3 = in.readString();
        city = in.readString();
        state = in.readString();
        pincode = in.readString();
        id = in.readString();
        stateid = in.readString();
        country = in.readString();
        isBillCheck = in.readByte() != 0;
        isShipCheck = in.readByte() != 0;
    }
}
