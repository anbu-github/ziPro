package com.purplefront.jiro_dev.parsers;

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
import com.purplefront.jiro_dev.model.Product_List_Model;
import com.purplefront.jiro_dev.R;

import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class Product_List_Adaptor extends RecyclerView.Adapter<Product_List_Adaptor.ViewHolder>{


    private Context mContext;
    List<Product_List_Model> mItems;
    DataFromAdapterToActivity dataFromAdapterToActivity;

    public Product_List_Adaptor(List<Product_List_Model> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
    }

    public interface DataFromAdapterToActivity
    {
        public void productId(String product_name, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_adaptor, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Product_List_Model nature = mItems.get(i);

        viewHolder.tvspecies.setText(nature.getProduct_name());
        dataFromAdapterToActivity = (DataFromAdapterToActivity) mContext;

        Glide.with(mContext).load(nature.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFromAdapterToActivity.productId(nature.getProduct_name(), nature.getProduct_id());
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
