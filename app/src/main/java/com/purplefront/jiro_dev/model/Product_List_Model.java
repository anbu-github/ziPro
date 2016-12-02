package com.purplefront.jiro_dev.model;

import java.util.ArrayList;

/**
 * Created by apple on 04/10/16.
 */

public class Product_List_Model {

    public ArrayList<String> uomidlist = new ArrayList<>();

    public Product_List_Model(String product_name, String product_id) {
        this.product_name = product_name;
        this.product_id = product_id;
    }

    public Product_List_Model() {

    }


    public ArrayList<String> getUompricelist1() {
        return uompricelist1;
    }

    public void setUompricelist1(ArrayList<String> uompricelist1) {
        this.uompricelist1 = uompricelist1;
    }

    public ArrayList<String> uompricelist1 = new ArrayList<>();

    public ArrayList<String> getUomnamelist() {
        return uomnamelist;
    }

    public void setUomnamelist(ArrayList<String> uomnamelist) {
        this.uomnamelist = uomnamelist;
    }

    public ArrayList<String> getUomidlist() {
        return uomidlist;
    }

    public void setUomidlist(ArrayList<String> uomidlist) {
        this.uomidlist = uomidlist;
    }

    public ArrayList<String> uomnamelist = new ArrayList<>();

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

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }




    public String getProduct_uom() {
        return product_uom;
    }

    public void setProduct_uom(String product_uom) {
        this.product_uom = product_uom;
    }



    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }



    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    String product_uom;
    String product_id;
    String product_name;
    String product_image;
    String product_price;
    Boolean isChecked=false;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    String qty = "1";


}
