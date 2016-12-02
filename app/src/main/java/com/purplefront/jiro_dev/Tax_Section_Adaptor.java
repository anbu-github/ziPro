package com.purplefront.jiro_dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.TaxTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/11/16.
 */

public class Tax_Section_Adaptor extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int count;
    private ArrayAdapter<String> adapter;
    Float total_amt=00f;
    List<TaxTypeModel> taxlist;

   Tax_Section_Adaptor.DataFromAdapterToActivity dataFromAdapterToActivity;



    ArrayList<String> editQuantityList = new ArrayList<>();
    ArrayList<String> editQuantityList1 = new ArrayList<>();
    public Tax_Section_Adaptor(Context context, List<TaxTypeModel> taxlist) {
        this.context = context;
        this.taxlist=taxlist;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface DataFromAdapterToActivity
    {
        public void Subtotal(Float totalamt);
    }


    @Override
    public int getCount() {
        return taxlist.size();
    }

    @Override
    public String getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // if (convertView == null) {
        final ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.tax_child_adapter, null);
            /*ViewHolder viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
*/

        holder = new ViewHolder();

        dataFromAdapterToActivity = (Tax_Section_Adaptor.DataFromAdapterToActivity) context;


        holder.taxname = (TextView) convertView.findViewById(R.id.taxname);
        holder.taxvalue = (TextView) convertView.findViewById(R.id.taxvalue);


        if(taxlist.get(position).getTax_type().equals("Percentage"))
        {
            Float original_amt = StaticVariable.total_amt * (Float.parseFloat(taxlist.get(position).getTax_value()) / 100);
            total_amt=total_amt+original_amt;
            holder.taxvalue.setText(String.valueOf(original_amt));

        }
        else {
            holder.taxvalue.setText(taxlist.get(position).getTax_value());
            total_amt=total_amt+Float.parseFloat(taxlist.get(position).getTax_value());
        }

        holder.taxname.setText(taxlist.get(position).getTax_name());
        StaticVariable.net_amount=total_amt;

        dataFromAdapterToActivity.Subtotal(total_amt);






        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder) {
        //TODO implement
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'inflate_assignmentlist.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    private class ViewHolder {
        TextView taxname,taxvalue;
        RelativeLayout rel_layout;

    }



}
