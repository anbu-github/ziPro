

/**
 * Created by pf-05 on 2/8/2016.
 */
package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.Purchase_Order_Model;

import java.util.List;


public class PurchaseOrderListAdapter extends RecyclerView.Adapter<PurchaseOrderListAdapter.MyViewHolder> {

    List<Purchase_Order_Model> persons;
    String date,contact,company,id,totalamt,usrname;
   DataFromAdapterToActivity dataFromAdapterToActivity;
    Context context;
    String mode="";

    public PurchaseOrderListAdapter(List<Purchase_Order_Model> persons,Context context) {
        this.persons = persons;
        this.context=context;
    }
    public PurchaseOrderListAdapter(Context context,List<Purchase_Order_Model> persons) {
        this.persons = persons;
        this.context=context;
        mode=persons.get(0).getId();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //PDialog.hide();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_adapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        dataFromAdapterToActivity = (PurchaseOrderListAdapter.DataFromAdapterToActivity) context;
        date=persons.get(position).getDate();
        contact=persons.get(position).getCustomer_contact();

        company=persons.get(position).getCustomer_company();
        id=persons.get(position).getId();

        totalamt = persons.get(position).getTotal();
        usrname = persons.get(position).getAssignuser();


        holder.date.setText(date);
        holder.contact.setText(contact);


        holder.company.setText(company);
        holder.id.setText(id);
        //holder.total.setText("₹"+totalamt);

        if(StaticVariable.currencyName.equals("Rupee"))
        {
            holder.order_type.setText("₹"+totalamt);
        }
        else if(StaticVariable.currencyName.equals("Dollar"))
        {
            holder.order_type.setText("$"+totalamt);
        }
        else
        {
            holder.order_type.setText("₹"+totalamt);
        }
        //holder.order_type.setText("₹"+totalamt);
        holder.username.setText(usrname);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CARID","CARID"+persons.get(position).getId());

                dataFromAdapterToActivity.viewOrders(persons.get(position).getId());
            }
        });

    }

    public interface DataFromAdapterToActivity
    {
        public void viewOrders(String id);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView date,contact,company;
        CheckBox cb;
        TextView city,id,order_type,total,textView14,username;

        public MyViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.select_address);
            date = (TextView)itemView.findViewById(R.id.date);
            contact = (TextView)itemView.findViewById(R.id.contact);
            company = (TextView)itemView.findViewById(R.id.item);
            order_type = (TextView)itemView.findViewById(R.id.order_type);
            id = (TextView)itemView.findViewById(R.id.id);
            //total = (TextView)itemView.findViewById(R.id.total);
            username = (TextView)itemView.findViewById(R.id.username);



        }
    }
}
