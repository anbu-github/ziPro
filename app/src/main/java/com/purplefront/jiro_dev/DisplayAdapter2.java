package com.purplefront.jiro_dev;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.Product_List_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 27/10/16.
 */

public class DisplayAdapter2 extends BaseAdapter implements Filterable {
    List<Product_List_Model> database_existing;
    List<Product_List_Model> mStringFilterList;
    //List<Group_Model> countrylist;
    private Context mContext;
    private String from = " ";
    private String contact_id;
    ValueFilter valueFilter;
    List<String> contarr = new ArrayList<>();
    List<String> selectedprod = new ArrayList<>();

    public DisplayAdapter2(Context c, List<Product_List_Model> database_existing) {

        this.mContext = c;
        this.database_existing = database_existing;
        mStringFilterList = database_existing;
    }

    public DisplayAdapter2(Context c, List<Product_List_Model> database_existing, String from) {
        this.mContext = c;
        this.database_existing = database_existing;
        mStringFilterList = database_existing;
        this.from = from;
    }


    public int getCount() {
        return database_existing.size();
    }

    public Object getItem(int position) {
        return database_existing.get(position);
    }

    public long getItemId(int position) {
        return database_existing.indexOf(getItem(position));
    }

    public View getView(final int pos, View child, ViewGroup parent) {
        final Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell, null);
            mHolder = new Holder();


            final Product_List_Model group_model = database_existing.get(pos);


            //	contact_id = database_existing.get(pos).getId();

            contarr.add("false");

            Log.v("IDDETA", "IDDEA" + contact_id);


//			if(!from.equals("crtegrp"))
//			{
//				mHolder.txt_name.setCheckMarkDrawable(null);
//			} else {
//				mHolder.txt_name.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if (mHolder.txt_name.isChecked()) {
//							contarr.remove(pos);
//							contarr.add(pos,"false");
//							mHolder.txt_name.setChecked(false);
//							database_existing.get(pos).setStatus(false);
//
//
//						} else {
//							contarr.remove(pos);
//							contarr.add(pos,database_existing.get(pos).getId());
//							mHolder.txt_name.setChecked(true);
//							database_existing.get(pos).setStatus(true);
//							Log.v("SELID","SELID"+contarr);
//						}
//					}
//
//
//				});
//			}

            mHolder.txt_phoneNumber = (TextView) child.findViewById(R.id.txt_phoneNumber);
            mHolder.txt_checkbox = (CheckBox) child.findViewById(R.id.txt_checkbox);
            mHolder.txt_name = (TextView) child.findViewById(R.id.txt_name);
            mHolder.layout = (LinearLayout) child.findViewById(R.id.layout);

            //mHolder.txt_name.setText(database_existing.get(pos).getName());

            mHolder.txt_name.setText(group_model.getProduct_name());


            if (JiroUtil.isAdded(group_model.getProduct_id())) {
                mHolder.txt_checkbox.setChecked(true);
            } else {
                mHolder.txt_checkbox.setChecked(false);
            }


            //mHolder.txt_phoneNumber.setText(database_existing.get(pos).getPhone());


            mHolder.txt_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    InputMethodManager imm = (InputMethodManager) buttonView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(buttonView.getWindowToken(), 0);
                    if (isChecked)
                        JiroUtil.addProducts(group_model.getProduct_id());
                    else
                        JiroUtil.removeProduct(group_model.getProduct_id());
                }
            });


            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        //	mHolder.txt_name.setText(database_existing.get(pos).getName());
        //	mHolder.txt_phoneNumber.setText(database_existing.get(pos).getPhone());
        return child;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }


    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Product_List_Model> filterList = new ArrayList<>();

                for (int i = 0; i < mStringFilterList.size(); i++) {

                    if ((mStringFilterList.get(i).getProduct_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Product_List_Model group_model = new Product_List_Model(mStringFilterList.get(i).getProduct_id(),
                                mStringFilterList.get(i).getProduct_name());

                        filterList.add(group_model);

                    }


                }

                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            database_existing = (List<Product_List_Model>) results.values;
            notifyDataSetChanged();

        }
    }

    public class Holder {
        TextView txt_phoneNumber, txt_name;
        CheckBox txt_checkbox;
        LinearLayout layout;

    }
}