package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purplefront.jiro_dev.model.Order_Detail_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 08/11/16.
 */

public class OrderList_View_Adaptor extends RecyclerView.Adapter<OrderList_View_Adaptor.ViewHolder> {


    private List<Order_Detail_Model> mItems;

    private Context mContext;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapter1;
    //private List<Order_Detail_Model> mFeedList;
    String nameprice;


    public OrderList_View_Adaptor(){
        mItems = new ArrayList<>();

    }

    public OrderList_View_Adaptor(List<Order_Detail_Model> mItems, Context mContext)
    {
        this.mItems = mItems;
        this.mContext = mContext;

    }

    public void setData(List<Order_Detail_Model> mItems) {
        this.mItems = mItems;
    }


    public interface DataFromAdapterToActivity
    {
        public void productId(String product_name, String id);
    }

    @Override
    public OrderList_View_Adaptor.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.catpro5_disp, viewGroup, false);
        OrderList_View_Adaptor.ViewHolder viewHolder = new OrderList_View_Adaptor.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderList_View_Adaptor.ViewHolder viewHolder, final int i) {
        final Order_Detail_Model nature = mItems.get(i);

        viewHolder.tvspecies.setText(nature.getProduct_name());


        if(StaticVariable.currencyName.equals("Rupee"))
        {
            viewHolder.uomprice.setText("₹"+nature.getPrice_original());
        }
        else if(StaticVariable.currencyName.equals("Dollar"))
        {
            viewHolder.uomprice.setText("$"+nature.getPrice_original());
        }
        else
        {
            viewHolder.uomprice.setText("₹"+nature.getPrice_original());
        }





        viewHolder.qty.setText(nature.getQuantity());

        int fprice = Integer.parseInt(nature.getQuantity()) * Integer.parseInt(nature.getPrice_original());

        if(StaticVariable.currencyName.equals("Rupee"))
        {
            viewHolder.totalprice.setText(String.valueOf("Total : ₹"+fprice));

        }
        else if(StaticVariable.currencyName.equals("Dollar"))
        {
            viewHolder.totalprice.setText(String.valueOf("Total : $"+fprice));
        }
        else {
            viewHolder.totalprice.setText(String.valueOf("Total : ₹"+fprice));
        }


        Context context = viewHolder.tvspecies.getContext();
        Glide.with(context).load(nature.getLogo()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);



    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public ImageView imgThumbnail;
        public TextView tvspecies,qty,total,uomprice,totalprice;
        Spinner uomqty;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);


            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);

            uomprice = (TextView)itemView.findViewById(R.id.cat_sel_spinner);

            uomqty = (Spinner)itemView.findViewById(R.id.cat_sel_spinner1);

            qty = (TextView) itemView.findViewById(R.id.edittext_quantity);
            total = (TextView) itemView.findViewById(R.id.total);

            totalprice = (TextView)itemView.findViewById(R.id.textview_plus);



        }
    }

}
