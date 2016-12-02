//package com.purplefront.jiro_dev.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import com.purplefront.jiro_dev.CreateVisitorCust;
//import com.purplefront.jiro_dev.R;
//import com.purplefront.jiro_dev.listener.CustomerVisitListener;
//import com.purplefront.jiro_dev.model.Address;
//import com.purplefront.jiro_dev.model.Contact;
//import com.purplefront.jiro_dev.model.Create_Address_Model;
//import com.purplefront.jiro_dev.model.Customer;
//import com.purplefront.jiro_dev.model.WhenSelection;
//import com.purplefront.jiro_dev.utils.Utils;
//import com.purplefront.jiro_dev.viewholder.AddressHolder;
//import com.purplefront.jiro_dev.viewholder.AddressViewHolder;
//import com.purplefront.jiro_dev.viewholder.ContactHolder;
//import com.purplefront.jiro_dev.viewholder.CustomerHolder;
//import com.purplefront.jiro_dev.viewholder.ErrorHolder;
//import com.purplefront.jiro_dev.viewholder.TitleHolder;
//import com.purplefront.jiro_dev.viewholder.TitleViewHolder;
//import com.purplefront.jiro_dev.viewholder.WhenSelectionHolder;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * Created by pf-05 on 11/27/2016.
// */
//public class CustomerListAdapter extends RecyclerView.Adapter{
//    private static final int CUSTOMER = 1;
//    private static final int CONTACT = 2;
//    private static final int ADDRESS_TITLE = 3;
//    private static final int ADDRESS = 4;
//    private static final int WHEN_SELECTION = 5;
//    private final CustomerVisitListener mActivityListener;
//    List<Object> mItems = new ArrayList<>();
//
//    public CustomerListAdapter(CustomerVisitListener listener){
//        this.mActivityListener = listener;
//    }
//
//    public Object getItem(int index) {
//        return mItems.get(index);
//    }
//
//    public List<Object> getItems() {
//        return mItems;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        RecyclerView.ViewHolder viewHolder = null;
//
//        switch (viewType) {
//            case CUSTOMER:
//                viewHolder = new CustomerHolder(inflater.inflate(R.layout.customer_layout, parent, false), mActivityListener);
//                break;
//            case CONTACT:
//                viewHolder = new ContactHolder(inflater.inflate(R.layout.contact_layout, parent, false), mActivityListener);
//                break;
//            case ADDRESS_TITLE:
//                viewHolder = new TitleHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
//                break;
//            case ADDRESS:
//                viewHolder = new AddressHolder(inflater.inflate(R.layout.address_layout, parent, false), mActivityListener);
//                break;
//            case WHEN_SELECTION:
//                viewHolder = new WhenSelectionHolder(inflater.inflate(R.layout.when_selection_layout, parent, false), mActivityListener);
//                break;
//            default:
//                viewHolder = new ErrorHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
//        }
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == CUSTOMER) {
//            ((CustomerHolder) holder).bindData((List<Customer>) getItem(position));
//        } else if (getItemViewType(position) == CONTACT) {
//            ((ContactHolder) holder).bindData((List<Contact>) getItem(position));
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return CUSTOMER;
//        } else if (position == 1) {
//            return CONTACT;
//        } else if (getItem(position) instanceof String && getItem(position).equals("Address")) {
//            return ADDRESS_TITLE;
//        } else if (getItem(position) instanceof Address) {
//            return ADDRESS;
//        } else if (getItem(position) instanceof WhenSelection) {
//            return WHEN_SELECTION;
//        }
//
//        return -1;
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
//
//    public void addAddress(List<Create_Address_Model> address_models) {
//        List<Object> items = mItems;
//        mItems.clear();
//        mItems.add(0, items.get(0));
//        mItems.add(1, items.get(1));
//
//        mItems.add("Address");
//
//        for (Create_Address_Model address : address_models) {
//            mItems.add(address);
//        }
//
//        mItems.add(items.get(items.size() - 1));
//    }
//}
