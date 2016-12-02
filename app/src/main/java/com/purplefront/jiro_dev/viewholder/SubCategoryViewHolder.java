package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.purplefront.jiro_dev.Product_SubList_Adaptor;
import com.purplefront.jiro_dev.controller.AppController;
import com.purplefront.jiro_dev.R;
import com.purplefront.jiro_dev.model.Product;

import java.util.List;

/**
 * Created by apple on 23/10/16.
 */

public class SubCategoryViewHolder extends RecyclerView.ViewHolder{
    private final Product_SubList_Adaptor.DataFromAdapterToActivity mActivityCommunicator;

    public SubCategoryViewHolder(View itemView, Product_SubList_Adaptor.DataFromAdapterToActivity activityCommunicator) {
        super(itemView);
        this.mActivityCommunicator = activityCommunicator;
    }

    public void bindData(List<Product> items, boolean isCategory){
        inflateSubCategories(items, isCategory);
    }

    private View getSubCategoryItemView(ViewGroup parent) {
        return LayoutInflater.from(AppController.getInstance())
                .inflate(R.layout.pro_sublist_adaptor, parent, false);
    }

    private void inflateSubCategories(List<Product> items, final boolean isCategory) {
        LinearLayout layout1 = (LinearLayout) itemView.findViewById(R.id.sub_view_1);
        LinearLayout layout2 = (LinearLayout) itemView.findViewById(R.id.sub_view_2);
        ProductsViewHolder productsViewHolder1 = new ProductsViewHolder(getSubCategoryItemView((ViewGroup) itemView), mActivityCommunicator, isCategory);
        final Product product1 = items.get(0);
        productsViewHolder1.bindData(product1);
        layout1.addView(productsViewHolder1.itemView);

        if (items.size() == 2) {
            ProductsViewHolder productsViewHolder2 = new ProductsViewHolder(getSubCategoryItemView((ViewGroup) itemView), mActivityCommunicator, isCategory);
            final Product product2 = items.get(1);
            productsViewHolder2.bindData(product2);
            layout2.addView(productsViewHolder2.itemView);
        }
    }
}
