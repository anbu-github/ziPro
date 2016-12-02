package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.AttributeModel;
import com.purplefront.jiro_dev.viewholder.AttributeViewHolder;

import java.util.ArrayList;

/**
 * Created by apple on 04/11/16.
 */

public class Attr_List_Adaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    ArrayList<AttributeModel> mItems;


    public Attr_List_Adaptor(ArrayList<AttributeModel> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.attr_child_view, viewGroup, false);
        return new AttributeViewHolder(v);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
       if(viewHolder instanceof AttributeViewHolder){
           if(null != mItems && mItems.size()>i) {
               ((AttributeViewHolder)viewHolder).bindData(mItems.get(i));
           }
       }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public EditText typeattname;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.cusattView);
            typeattname = (EditText)itemView.findViewById(R.id.attetxname);



        }
    }

    public ArrayList<AttributeModel> getAllAttributes(){
        return mItems;
    }



}
