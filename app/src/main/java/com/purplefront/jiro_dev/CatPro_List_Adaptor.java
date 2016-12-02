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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.purplefront.jiro_dev.model.Product_List_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 26/10/16.
 */

public class CatPro_List_Adaptor extends RecyclerView.Adapter<CatPro_List_Adaptor.ViewHolder> {

    List<Product_List_Model> mItems;

    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> dataAdapter1;
    ArrayList<Integer> uomposList=new ArrayList<>();
    ArrayList<String> uompriceList=new ArrayList<>();
    private List<Product_List_Model> mFeedList;

    ArrayList<String> UmoNameList = new ArrayList<>();

    ArrayList<String> totalamtList = new ArrayList<>();


    Integer uompos=0;

    int qtysize = 1;



    String nameprice;


    public CatPro_List_Adaptor(){
        mItems = new ArrayList<>();
        mFeedList = new ArrayList<>();
        totalamtList.clear();
        for (int i=0;i<25;i++){
            uomposList.add(0);
            UmoNameList.add("");
            totalamtList.add("");
            uompriceList.add("");
        }
    }

    public void setData(List<Product_List_Model> feedlist) {
        this.mFeedList = feedlist;
        updateData();
    }

    private void updateData() {
        mItems = new ArrayList<>();
        int i=0;
        for (Product_List_Model product:mFeedList){
            i++;
            StaticVariable.qtylist.add("1");
            StaticVariable.uom_pos_list.add(0);
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
    public CatPro_List_Adaptor.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.catpro3_disp, viewGroup, false);
        CatPro_List_Adaptor.ViewHolder viewHolder = new CatPro_List_Adaptor.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CatPro_List_Adaptor.ViewHolder viewHolder, final int i) {
        final Product_List_Model nature = mItems.get(i);

        viewHolder.tvspecies.setText(nature.getProduct_name());
        StaticVariable.pro_total_amtlist.clear();

        try {
            viewHolder.qty.setText(StaticVariable.qtylist.get(i));

            Log.v("SPRI","SPRI"+StaticVariable.uom_pos_list.get(i));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            int fprice = Integer.parseInt(StaticVariable.pro_total_amtlist.get(StaticVariable.uom_pos_list.get(i))) * Integer.parseInt(StaticVariable.qtylist.get(i));
            totalamtList.add(i,fprice+"");

            Log.d("CUNAMe","CUNAMe"+StaticVariable.currencyName);

            if(StaticVariable.currencyName.equals("Rupee"))
            {
                viewHolder.totalamt.setText(String.valueOf("Total : ₹"+fprice));
            }
            else if(StaticVariable.currencyName.equals("Dollar"))
            {
                viewHolder.totalamt.setText(String.valueOf("Total : $"+fprice));

            }
            else {
                viewHolder.totalamt.setText(String.valueOf("Total : ₹"+fprice));

            }


            totalamtList.remove(i);
            totalamtList.add(i, String.valueOf(fprice));


            StaticVariable.pro_total_amtlist = totalamtList;

        }
        catch (IndexOutOfBoundsException e)
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

                try
                {
                    int fprice = Integer.parseInt(nature.getUompricelist1().get(uomposList.get(i))) * Integer.parseInt(StaticVariable.qtylist.get(i));

                    if(StaticVariable.currencyName.equals("Rupee"))
                    {
                        viewHolder.totalamt.setText(String.valueOf("Total :₹"+fprice));
                    }
                    else if(StaticVariable.currencyName.equals("Dollar"))
                    {
                        viewHolder.totalamt.setText(String.valueOf("Total :$"+fprice));
                    }
                    else {
                        viewHolder.totalamt.setText(String.valueOf("Total :₹"+fprice));
                    }

                    //viewHolder.totalamt.setText(String.valueOf("Total :₹"+fprice));

                    totalamtList.remove(i);
                    totalamtList.add(i, String.valueOf(fprice));


                    StaticVariable.pro_total_amtlist = totalamtList;

                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });

        Context context = viewHolder.tvspecies.getContext();
        Glide.with(context).load(nature.getProduct_image()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imgThumbnail);


        dataAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, nature.getUomnamelist());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewHolder.uomprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //StaticVariable.qtylist.add(i,viewHolder.qty.getText().toString());




               try {
                   uomposList.remove(i);
                   uomposList.add(i, position);
                   UmoNameList.remove(i);
                   UmoNameList.add(i, nature.getUomnamelist().get(position));
                    uompriceList.remove(i);
                   uompriceList.add(i, nature.getUompricelist1().get(position));

                   StaticVariable.uom_price_list=uompriceList;

                   StaticVariable.uom_pos_list=uomposList;



                   StaticVariable.umo_name_pricelist = UmoNameList;



                   Log.d("SPID","SPID"+ StaticVariable.umo_name_pricelist.get(i));

               }catch (Exception e){
                   e.printStackTrace();
               }


                try
                {
                    int fprice = Integer.parseInt(nature.getUompricelist1().get(position)) * Integer.parseInt(StaticVariable.qtylist.get(i));

                    if(StaticVariable.currencyName.equals("Rupee"))
                    {
                        viewHolder.totalamt.setText(String.valueOf("Total :₹"+fprice));
                    }
                    else if(StaticVariable.currencyName.equals("Dollar"))
                    {
                        viewHolder.totalamt.setText(String.valueOf("Total :$"+fprice));
                    }
                    else
                    {
                        viewHolder.totalamt.setText(String.valueOf("Total :₹"+fprice));
                    }





                    totalamtList.remove(i);
                    totalamtList.add(i, String.valueOf(fprice));


                    StaticVariable.pro_total_amtlist = totalamtList;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }







            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.uomprice.setAdapter(dataAdapter);

        dataAdapter1 = new ArrayAdapter<>(context, R.layout.spinner_layout, context.getResources().getStringArray(R.array.uom_qty));
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewHolder.proddel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariable.qtylist.remove(i);
                JiroUtil.removeProduct(nature.getProduct_id());
                updateData();
            }
        });


        viewHolder.uomprice.setSelection(StaticVariable.uom_pos_list.get(i));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public ImageView imgThumbnail, proddel;
        public TextView tvspecies,totalamt,minus,plus;
        Spinner uomprice, uomqty;
        public EditText qty;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_category);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            proddel = (ImageView)itemView.findViewById(R.id.img_prodel);

            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);

            uomprice = (Spinner)itemView.findViewById(R.id.cat_sel_spinner);

            uomqty = (Spinner)itemView.findViewById(R.id.cat_sel_spinner1);

            qty = (EditText)itemView.findViewById(R.id.edittext_quantity);

            totalamt = (TextView)itemView.findViewById(R.id.totalamt);

            minus = (TextView)itemView.findViewById(R.id.textview_minus1);

            plus = (TextView)itemView.findViewById(R.id.textview_plus1);



        }
    }


}
