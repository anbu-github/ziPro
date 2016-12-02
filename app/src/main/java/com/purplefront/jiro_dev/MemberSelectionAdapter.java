package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.MemberModel;

import java.util.List;

/**
 * Created by apple on 13/10/16.
 */

public class MemberSelectionAdapter extends RecyclerView.Adapter<MemberSelectionAdapter.ViewHolder> {

    String from;
    private Context mContext;
    List<MemberModel> mItems;

    MemberSelectionAdapter.DataFromAdapterToActivity dataFromAdapterToActivity;

    public MemberSelectionAdapter(List<MemberModel> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
        //this.from = from;
    }

    public interface DataFromAdapterToActivity
    {
        public void memberID(String mem_name, String mem_contact,String id);
    }

    @Override
    public MemberSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mem_select, viewGroup, false);
        MemberSelectionAdapter.ViewHolder viewHolder = new MemberSelectionAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberSelectionAdapter.ViewHolder viewHolder, int i) {
        final MemberModel nature = mItems.get(i);

        viewHolder.name.setText(nature.getName());
        viewHolder.contact.setText(nature.getContact());
        viewHolder.role.setText(nature.getRole());

        dataFromAdapterToActivity = (MemberSelectionAdapter.DataFromAdapterToActivity) mContext;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

        public TextView name,contact,role;

        public ViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.cv_category);
            name = (TextView) itemView.findViewById(R.id.name);
            contact = (TextView)itemView.findViewById(R.id.contact);
            role = (TextView)itemView.findViewById(R.id.memrole);
        }
    }

}
