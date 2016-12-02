package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.CustomerModel;

import java.util.List;

/**
 * Created by apple on 17/10/16.
 */

public class CustSegmentSelectionAdapter extends RecyclerView.Adapter<CustSegmentSelectionAdapter.ViewHolder> {

    String from;
    private Context mContext;
    List<CustomerModel> mItems;

    CustSegmentSelectionAdapter.DataFromAdapterToActivity dataFromAdapterToActivity;

    public CustSegmentSelectionAdapter(List<CustomerModel> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
        //this.from = from;
    }

    public interface DataFromAdapterToActivity
    {
        public void memberID(String mem_name, String mem_contact,String id);
    }

    @Override
    public CustSegmentSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custmem_select, viewGroup, false);
        CustSegmentSelectionAdapter.ViewHolder viewHolder = new CustSegmentSelectionAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustSegmentSelectionAdapter.ViewHolder viewHolder, int i) {
        final CustomerModel nature = mItems.get(i);

        viewHolder.name.setText(nature.getName());
        viewHolder.contact.setText(nature.getContact());

        dataFromAdapterToActivity = (CustSegmentSelectionAdapter.DataFromAdapterToActivity) mContext;

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataFromAdapterToActivity.memberID(nature.getName(), nature.getContact(),nature.getId());


            }
        });
    }
    @Override
    public int getItemCount() {

        return mItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //TextView name,contact;

        public TextView name,contact;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.customercardvw);
            name = (TextView) itemView.findViewById(R.id.name);
            contact = (TextView)itemView.findViewById(R.id.contact);
        }
    }
}
