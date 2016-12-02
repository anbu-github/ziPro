package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.CustomerContact;

import java.util.List;

/**
 * Created by apple on 19/10/16.
 */

public class Cust_Contact_Adapter extends RecyclerView.Adapter<Cust_Contact_Adapter.ViewHolder>{

    //String from;
    private Context mContext;
    List<CustomerContact> mItems;

    public Cust_Contact_Adapter(List<CustomerContact> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
        //this.from = from;

    }

    @Override
    public Cust_Contact_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cust_contact_view1, viewGroup, false);
        Cust_Contact_Adapter.ViewHolder viewHolder = new Cust_Contact_Adapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(Cust_Contact_Adapter.ViewHolder viewHolder, int i) {
        final CustomerContact nature = mItems.get(i);

//        if(nature.getTitle().equals("null"))
//        {
//            viewHolder.title_name.setVisibility(View.GONE);
//            viewHolder.name.setText(nature.getName());
//        }
//        else {
//            viewHolder.title_name.setVisibility(View.VISIBLE);
//            viewHolder.title_name.setText(nature.getTitle());
//            viewHolder.name.setText(nature.getName());
//        }



        if(nature.getTitle().equals("null"))
        {
            viewHolder.name.setText(nature.getName());
        }
        else {
            viewHolder.name.setText(nature.getTitle()+ " "+nature.getName());
        }

        //viewHolder.name.setText(nature.getTitle()+ " "+nature.getName());
        viewHolder.email.setText(nature.getEmail());
        viewHolder.phoneno.setText(nature.getPhone());


        //dataFromAdapterToActivity = (Cust_Add_Adaptor.DataFromAdapterToActivity) mContext;


    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,email,phoneno;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.mem_name);
            email = (TextView)itemView.findViewById(R.id.mem_email);
            phoneno = (TextView)itemView.findViewById(R.id.phone);

            //title_name = (TextView)itemView.findViewById(R.id.title_name);
        }
    }
}
