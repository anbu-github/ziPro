package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purplefront.jiro_dev.model.Product;
import com.purplefront.jiro_dev.model.ProductPageResponse;
import com.purplefront.jiro_dev.viewholder.FalseViewHolder;
import com.purplefront.jiro_dev.viewholder.SubCategoryViewHolder;
import com.purplefront.jiro_dev.viewholder.TitleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 06/10/16.
 */

public class Product_SubList_Adaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITLE = 1;
    private static final int TYPE_PRODUCT = 2;
    private static final int TYPE_NOITEMS = 3;
    private int mProductsSize = 0;
    private int mCategoriesSize = 0;
    private List<Object> mItems = new ArrayList<>();
    private Product_SubList_Adaptor.DataFromAdapterToActivity dataFromAdapterToActivity;
    private Context mContext;

    public Product_SubList_Adaptor(ProductPageResponse items, Context c) {
        this.mContext = c;
        if (items.getCategories() != null && items.getCategories().size() > 0) {
            mItems.add("Categories");
            List<Product> categories = new ArrayList<>();
            for (int i = 0; i < items.getCategories().size(); i++) {
                categories.add(items.getCategories().get(i));

                if (categories.size() == 2 || i == items.getCategories().size() - 1) {
                    mItems.add(categories);
                    categories = new ArrayList<>();
                    mCategoriesSize++;
                }
            }
        }

        if (items.getProducts() != null && items.getProducts().size() > 0) {
            mProductsSize = items.getProducts().size();
            mItems.add("Products");
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < items.getProducts().size(); i++) {
                products.add(items.getProducts().get(i));

                if (products.size() == 2 || i == items.getProducts().size() - 1) {
                    mItems.add(products);
                    products = new ArrayList<>();
                }
            }
        }
        if (mItems.size() == 0)
            mItems.add("Error");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                View titleView = LayoutInflater.from(mContext)
                        .inflate(R.layout.titleview, viewGroup, false);
                return new TitleViewHolder(titleView);
            case TYPE_PRODUCT:
                View productView = LayoutInflater.from(mContext)
                        .inflate(R.layout.sub_items, viewGroup, false);
                return new SubCategoryViewHolder(productView, (Product_SubList_Adaptor.DataFromAdapterToActivity) mContext);
            case TYPE_NOITEMS:
                View noItemsView = LayoutInflater.from(mContext)
                        .inflate(R.layout.no_items, viewGroup, false);
                return new FalseViewHolder(noItemsView);
        }
        return new FalseViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).setTitle(getTitle(position));
        } else if (holder instanceof SubCategoryViewHolder){
            ((SubCategoryViewHolder) holder).bindData(getData(position), isCategory(position));
        }
    }

    private List<Product> getData(int position) {
        return (List<Product>) mItems.get(position);
    }

    private String getTitle(int position) {
        return (String) mItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof String && !mItems.get(position).toString().equals("Error")) {
            return TYPE_TITLE;
        } else if (mItems.get(position) instanceof String && mItems.get(position).toString().equals("Error")) {
            return TYPE_NOITEMS;
        }
        return TYPE_PRODUCT;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private boolean isCategory(int position) {
        return mCategoriesSize > 0 && position > 0 && position <= (mCategoriesSize + 1);
    }

    public interface DataFromAdapterToActivity {
        public void productId(String product_name, String id, String from);
    }

//    @Override
//    public void onBindViewHolder(Product_SubList_Adaptor.ViewHolder viewHolder, int i) {
//        final Product nature = mItems.get(i);
//
//        viewHolder.tvspecies.setText(nature.getProduct_name());
//        dataFromAdapterToActivity = (Product_SubList_Adaptor.DataFromAdapterToActivity) mContext;
//
//        Glide.with(mContext).load(nature.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);
//        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataFromAdapterToActivity.productId(nature.getProduct_name(), nature.getProduct_id(),from);
//            }
//        });
//    }


}

