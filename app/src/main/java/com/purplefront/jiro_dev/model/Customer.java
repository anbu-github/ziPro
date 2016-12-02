package com.purplefront.jiro_dev.model;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class Customer {
    private int customerId;
    private String customerName;
    private boolean isSelected;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
