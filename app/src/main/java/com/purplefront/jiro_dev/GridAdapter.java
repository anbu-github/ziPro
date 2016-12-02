package com.purplefront.jiro_dev;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.purplefront.jiro_dev.model.Home_Model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class GridAdapter extends  RecyclerView.Adapter<GridAdapter.ViewHolder>{

    private Context mContext;
    List<Home_Model> mItems;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

    GridAdapter(List<Home_Model> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Home_Model nature = mItems.get(i);


        viewHolder.tvspecies.setText(StringUtils.capitalize(nature.getName()));

        //viewHolder.tvspecies.setText(nature.getName());
        viewHolder.tvdescription.setText(nature.getDescription());


        String letter = "A";
        String title=nature.getName();

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();
        // Create a circular icon consisting of  a random background colour and first letter of title
        viewHolder.thumbnail_image = TextDrawable.builder()
                .buildRound(letter, color);



        viewHolder.image.setImageDrawable(viewHolder.thumbnail_image);



//        Glide.with(mContext).load(nature.getImage()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.objectid = "";
                Intent intent = new Intent(mContext, SubCategory.class);
                intent.putExtra("id", nature.getId());
                intent.putExtra("name", nature.getName());
                intent.putExtra("business_id", StaticVariable.business_id);
                mContext.startActivity(intent);

                //     mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        viewHolder.tvspecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubCategory.class);
                intent.putExtra("id", nature.getId());
                intent.putExtra("name", nature.getName());
                intent.putExtra("business_id", StaticVariable.business_id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        android.support.v7.widget.CardView cv;
        public ImageView imgThumbnail,image;
        public TextView tvspecies;
        public TextView tvdescription;
        private TextDrawable thumbnail_image;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
         //   imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            image = (ImageView)itemView.findViewById(R.id.thumbnail_image);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
            tvdescription = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }


}
