package com.purplefront.jiro_dev.model;

/**
 * Created by apple on 02/11/16.
 */

public class Purchase_Order_Model {

    String id;
    String Date;
    String customer_contact;
    String customer_company;
    String order_type;
    String quantity;

    public String getAssignuser() {
        return assignuser;
    }

    public void setAssignuser(String assignuser) {
        this.assignuser = assignuser;
    }

    String assignuser;


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String total;

    public String getCustomer_company() {
        return customer_company;
    }

    public void setCustomer_company(String customer_company) {
        this.customer_company = customer_company;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }

}
