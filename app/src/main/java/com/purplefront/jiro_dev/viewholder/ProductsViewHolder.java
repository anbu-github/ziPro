package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purplefront.jiro_dev.Product_SubList_Adaptor;
import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.model.Product;

/**
 * Created by apple on 22/10/16.
 */

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    private final Product_SubList_Adaptor.DataFromAdapterToActivity mActivityCommunicator;
    private final boolean mIsCategory;
    private CardView cv;
    private ImageView imgThumbnail;
    private TextView tvspecies;

    public ProductsViewHolder(View itemView, Product_SubList_Adaptor.DataFromAdapterToActivity mActivityCommunicator, boolean isCategory) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cv_category);
        imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        tvspecies = (TextView) itemView.findViewById(R.id.tv_species);
        this.mIsCategory = isCategory;
        this.mActivityCommunicator = mActivityCommunicator;
    }

    public void bindData(final Product product){
        if(null != product){
            if(null != imgThumbnail && null != product.getProduct_image()){
                Glide.with(itemView.getContext()).load(product.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imgThumbnail);
            }

            if(null != tvspecies){
                tvspecies.setText(product.getProduct_name());
            }
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsCategory) {
                        mActivityCommunicator.productId(product.getProduct_name(), product.getProduct_id(), "categoires");
                    } else {
                        mActivityCommunicator.productId(product.getProduct_name(), product.getProduct_id(), "products");

                    }
                }
            });
        }
    }
}
