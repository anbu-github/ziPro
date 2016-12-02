package com.purplefront.jiro_dev.model;

/**
 * Created by apple on 08/11/16.
 */

public class Order_Detail_Model {

    private String id;

    private String product_id;

    private String product_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getPrice_original() {
        return price_original;
    }

    public void setPrice_original(String price_original) {
        this.price_original = price_original;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice_sold() {
        return price_sold;
    }

    public void setPrice_sold(String price_sold) {
        this.price_sold = price_sold;
    }

    private String logo;

    private String product_code;

    private String price_original;

    private String currency_id;

    private String quantity;

    private String discount;

    private String price_sold;



}
