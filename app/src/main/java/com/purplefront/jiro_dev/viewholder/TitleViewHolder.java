package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.purplefront.jiro_dev.R;

/**
 * Created by apple on 22/10/16.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder{

    private TextView mTitle;

    public TitleViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.product_page_title);
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title) && null != mTitle){
            mTitle.setText(title);
        }
    }
}
