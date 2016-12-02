package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purplefront.jiro_dev.model.Product;

import java.util.List;

/**
 * Created by apple on 06/10/16.
 */

public class Product_SubList1_Adaptor extends RecyclerView.Adapter<Product_SubList1_Adaptor.ViewHolder> {

    String from;
    private Context mContext;
    List<Product> mItems;
    Product_SubList1_Adaptor.DataFromAdapterToActivity dataFromAdapterToActivity;

    public Product_SubList1_Adaptor(List<Product> mItems, Context c, String from){
        this.mContext = c;
        this.mItems = mItems;
        this.from = from;

    }

    public interface DataFromAdapterToActivity
    {
        public void productId(String product_name, String id, String from);
    }

    @Override
    public Product_SubList1_Adaptor.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_sublist_adaptor, viewGroup, false);
        Product_SubList1_Adaptor.ViewHolder viewHolder = new Product_SubList1_Adaptor.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Product_SubList1_Adaptor.ViewHolder viewHolder, int i) {
        final Product nature = mItems.get(i);

        viewHolder.tvspecies.setText(nature.getProduct_name());
        dataFromAdapterToActivity = (Product_SubList1_Adaptor.DataFromAdapterToActivity) mContext;

        Glide.with(mContext).load(nature.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFromAdapterToActivity.productId(nature.getProduct_name(), nature.getProduct_id(),from);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public ImageView imgThumbnail;
        public TextView tvspecies;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
        }
    }

}

