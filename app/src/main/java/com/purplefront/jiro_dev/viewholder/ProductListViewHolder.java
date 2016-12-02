package com.purplefront.jiro_dev.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.purplefront.jiro_dev.R;

/**
 * Created by apple on 06/11/16.
 */

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    private CardView cv;
    private ImageView imgThumbnail;
    private TextView tvspecies,qty;
    private Spinner uomprice, uomqty;

    public ProductListViewHolder(View itemView) {
        super(itemView);
        cv = (CardView)itemView.findViewById(R.id.cv_category);
        imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);


        tvspecies = (TextView)itemView.findViewById(R.id.tv_species);

        uomprice = (Spinner)itemView.findViewById(R.id.cat_sel_spinner);

        uomqty = (Spinner)itemView.findViewById(R.id.cat_sel_spinner1);

        qty = (TextView) itemView.findViewById(R.id.edittext_quantity);
    }


}
