package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.purplefront.jiro_dev.model.Sub_Category_Model;

import java.util.List;



public class Sub_Category_Adaptor extends RecyclerView.Adapter<Sub_Category_Adaptor.ViewHolder>{


    private Context mContext;
    List<Sub_Category_Model> mItems;
    DataFromAdapterToActivity dataFromAdapterToActivity;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

    public Sub_Category_Adaptor(List<Sub_Category_Model> mItems, Context c){
        this.mContext = c;
        this.mItems = mItems;
    }

    public interface DataFromAdapterToActivity
    {
        public void subCategoryId(String subCategory_name, String id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sub_category_adaptor, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Sub_Category_Model nature = mItems.get(i);




        viewHolder.tvspecies.setText(nature.getName());



        String letter = "A";
        String title=nature.getName();

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();
        // Create a circular icon consisting of  a random background colour and first letter of title
        viewHolder.thumbnail_image = TextDrawable.builder()
                .buildRound(letter, color);



        viewHolder.imgThumbnail.setImageDrawable(viewHolder.thumbnail_image);



        dataFromAdapterToActivity = (DataFromAdapterToActivity) mContext;

       // Glide.with(mContext).load(nature.getImage()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFromAdapterToActivity.subCategoryId(nature.getName(), nature.getId());
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
        private TextDrawable thumbnail_image;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.thumbnail_image);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
        }
    }

}
