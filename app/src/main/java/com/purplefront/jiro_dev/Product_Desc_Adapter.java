package com.purplefront.jiro_dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplefront.jiro_dev.model.Student_View_All_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 07/10/16.
 */

public class Product_Desc_Adapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private int count;
    private ArrayAdapter<String> adapter;
    List<Student_View_All_Model> person;
    ArrayList<String> editQuantityList = new ArrayList<>();
    ArrayList<String> editQuantityList1 = new ArrayList<>();
    public Product_Desc_Adapter(Context context, List<Student_View_All_Model> person) {
        this.context = context;
        this.person=person;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return person.size();
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
        convertView = layoutInflater.inflate(R.layout.product_desc_adapter, null);
            /*ViewHolder viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
*/

        holder = new ViewHolder();

        holder.name = (TextView) convertView.findViewById(R.id.pro_type);
        holder.time = (TextView) convertView.findViewById(R.id.pro_value);


            holder.time.setText(person.get(position).getGrade_name());



        holder.name.setText(person.get(position).getStudent_name());


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
        TextView name,time;
        RelativeLayout rel_layout;

    }

}
