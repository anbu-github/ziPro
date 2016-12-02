package com.purplefront.jiro_dev;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purplefront.jiro_dev.model.Product_List_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 03/11/16.
 */

public class CatPro_List_Adaptor1 extends RecyclerView.Adapter<CatPro_List_Adaptor1.ViewHolder> {

    List<Product_List_Model> mItems;

    private Context mContext;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapter1;
    private List<Product_List_Model> mFeedList;
    String nameprice;
    Integer uompos=0;


    public CatPro_List_Adaptor1(){
        mItems = new ArrayList<>();
        mFeedList = new ArrayList<>();

    }

    public CatPro_List_Adaptor1(List<Product_List_Model> feedlist, Context mContext)
    {
        this.mFeedList = feedlist;
        this.mContext = mContext;

    }

    public void setData(List<Product_List_Model> feedlist) {
        this.mFeedList = feedlist;
        updateData();
    }

    private void updateData() {
        mItems = new ArrayList<>();
        for (Product_List_Model product:mFeedList){
            if (JiroUtil.isAdded(product.getProduct_id())) {
                mItems.add(product);
            }
        }
        notifyDataSetChanged();
    }

    public interface DataFromAdapterToActivity
    {
        public void productId(String product_name, String id);
    }

    @Override
    public CatPro_List_Adaptor1.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.prorvw_disp, viewGroup, false);
        CatPro_List_Adaptor1.ViewHolder viewHolder = new CatPro_List_Adaptor1.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CatPro_List_Adaptor1.ViewHolder viewHolder, final int i) {
        final Product_List_Model nature = mItems.get(i);

        viewHolder.tvspecies.setText(nature.getProduct_name());

        viewHolder.uomprice.setText(StaticVariable.umo_name_pricelist.get(i));



//        for(int j=0;i < StaticVariable.pro_total_amtlist.size();j++) {
//            Log.v("testtotl1", StaticVariable.pro_total_amtlist.get(j));
//        }



        try {
            viewHolder.qty.setText(StaticVariable.qtylist.get(i));

            Log.v("SPRI","SPRI"+StaticVariable.qtylist.get(i));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }







        viewHolder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nature.setQty(viewHolder.qty.getText().toString());

                try {

                    StaticVariable.qtylist.remove(i);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                StaticVariable.qtylist.add(i,viewHolder.qty.getText().toString());

                Log.d("ADQTY","ADQTY"+StaticVariable.qtylist.size());

                Log.d("QTEX","QTEX"+nature.getQty());

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });


        //int fprice = Integer.parseInt(nature)

        try {
            int fprice = Integer.parseInt(StaticVariable.pro_total_amtlist.get(i)) * Integer.parseInt(StaticVariable.qtylist.get(i));

            if(StaticVariable.currencyName.equals("Rupee"))
            {
                viewHolder.total.setText(String.valueOf("Total : ₹"+StaticVariable.pro_total_amtlist.get(i)));
            }
            else if(StaticVariable.currencyName.equals("Dollar"))
            {
                viewHolder.total.setText(String.valueOf("Total : $"+StaticVariable.pro_total_amtlist.get(i)));

            }
            else {
                viewHolder.total.setText(String.valueOf("Total : ₹"+StaticVariable.pro_total_amtlist.get(i)));
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //int fprice = Integer.parseInt(StaticVariable.pro_total_amtlist.get(i)) * Integer.parseInt(StaticVariable.qtylist.get(i));

        //viewHolder.total.setText(String.valueOf("Total : ₹"+StaticVariable.pro_total_amtlist.get(i)));

        //viewHolder.total.setText(StaticVariable.pro_total_amtlist.get(i));


        Log.d("AAAA","AAAA"+StaticVariable.pro_total_amtlist.get(i));

        Context context = viewHolder.tvspecies.getContext();
        Glide.with(context).load(nature.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);

        //nameprice = nature.getUomnamelist() + "-"+ nature.getUompricelist1();

//        dataAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, StaticVariable.umo_name_pricelist);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        viewHolder.uomprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                //StaticVariable.qtylist.add(i,viewHolder.qty.getText().toString());
//
//                uompos=position;
//                int fprice = Integer.parseInt(nature.getUompricelist1().get(position)) * Integer.parseInt(StaticVariable.qtylist.get(i));
//                viewHolder.total.setText(String.valueOf("Total :₹"+fprice));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//
//        viewHolder.uomprice.setAdapter(dataAdapter);
//        viewHolder.uomprice.setEnabled(false);

        dataAdapter1 = new ArrayAdapter<>(context, R.layout.spinner_layout, context.getResources().getStringArray(R.array.uom_qty));
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



//        viewHolder.proddel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JiroUtil.removeProduct(nature.getProduct_id());
//                updateData();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public ImageView imgThumbnail;
        public TextView tvspecies,qty,total,uomprice;
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



        }
    }

}
