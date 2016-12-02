package com.purplefront.jiro_dev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.R;

import java.util.List;

/**
 * Created by apple on 13/10/16.
 */

public class CustomAttrDisplayAdapter extends RecyclerView.Adapter<CustomAttrDisplayAdapter.ViewHolder> {

    String from;
    private Context mContext;
    List<AttributeModel> mItems;


    public CustomAttrDisplayAdapter(List<AttributeModel> mItems1, Context c){
        this.mContext = c;
        this.mItems = mItems1;

       /* for (AttributeModel attr:mItems1){
            if (attr.getValue().equals("")){
                mItems.remove(Integer.parseInt(attr.getId())-1);
            }


        }*/
        //this.from = from;
    }

    public interface DataFromAdapterToActivity
    {
        public void memberID(String mem_name, String mem_contact, String id);
    }

    @Override
    public CustomAttrDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_attr_adapter, viewGroup, false);
        CustomAttrDisplayAdapter.ViewHolder viewHolder = new CustomAttrDisplayAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAttrDisplayAdapter.ViewHolder viewHolder, int i) {
        final AttributeModel nature = mItems.get(i);

        viewHolder.name.setText(nature.getName());
        viewHolder.contact.setText(nature.getValue());

//        if (nature.getValue().equals("")||nature.getValue().equals(null)){
//            viewHolder.name.setVisibility(View.GONE);
//            viewHolder.contact.setVisibility(View.GONE);
//        }
//        else{
//            viewHolder.name.setVisibility(View.VISIBLE);
//            viewHolder.contact.setVisibility(View.VISIBLE);
//
//        }




    }

    @Override
    public int getItemCount() {

        Log.d("AITSIZE","AITSIZE"+mItems.size());
        return mItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //TextView name,contact;

        public TextView name,contact;

        public ViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.cv_category);
            name = (TextView) itemView.findViewById(R.id.name);
            contact = (TextView)itemView.findViewById(R.id.contact);
        }
    }

}
