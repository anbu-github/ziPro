package com.purplefront.jiro_dev.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apple on 04/11/16.
 */

public class AttributeModel implements Parcelable{

    String id;
    String name="";
    private String mValue="";
    private String obj_master_id = "";



    public AttributeModel(){

    }

    protected AttributeModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        mValue = in.readString();
        obj_master_id = in.readString();
    }

    public static final Creator<AttributeModel> CREATOR = new Creator<AttributeModel>() {
        @Override
        public AttributeModel createFromParcel(Parcel in) {
            return new AttributeModel(in);
        }

        @Override
        public AttributeModel[] newArray(int size) {
            return new AttributeModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getValue() {
        return mValue;
    }

    public void setValue(String userattr) {
        this.mValue = userattr;
    }



    public String getObj_master_id() {
        return obj_master_id;
    }

    public void setObj_master_id(String obj_master_id) {
        this.obj_master_id = obj_master_id;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(mValue);
        dest.writeString(obj_master_id);
    }
}
