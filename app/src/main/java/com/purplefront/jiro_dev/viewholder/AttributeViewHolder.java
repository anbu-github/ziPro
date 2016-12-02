package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.events.ValidateEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by apple on 06/11/16.
 */

public class AttributeViewHolder extends RecyclerView.ViewHolder {

    private EditText typeattname;
    private TextView mAttribute;
    private AttributeModel mAttributeModel;

    public AttributeViewHolder(View itemView) {
        super(itemView);
        mAttribute = (TextView) itemView.findViewById(R.id.cusattView);
        typeattname = (EditText) itemView.findViewById(R.id.attetxname);
        if (!EventBus.getDefault().isRegistered(AttributeViewHolder.this))
            EventBus.getDefault().register(AttributeViewHolder.this);
    }


    public void bindData(AttributeModel attributeModel) {
        if (null != attributeModel) {
            mAttributeModel = attributeModel;
            mAttribute.setText(attributeModel.getName());
            typeattname.setText(attributeModel.getValue());
        }
    }

    public void onEvent(ValidateEvent validateEvent) {
        mAttributeModel.setValue(typeattname.getText().toString());
        Log.d("VA1","VA1"+typeattname.getText().toString());
    }
}