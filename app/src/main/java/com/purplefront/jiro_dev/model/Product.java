package com.purplefront.jiro_dev.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 04/10/16.
 */

public class Product {
    @SerializedName("id")
    private String productId;

    @SerializedName("name")
    private String productName;

    @SerializedName("image")
    private String productImage;

    public String getProduct_id() {
        return productId;
    }

    public void setProduct_id(String product_id) {
        this.productId = product_id;
    }

    public String getProduct_name() {
        return productName;
    }

    public void setProduct_name(String product_name) {
        this.productName = product_name;
    }

    public String getProduct_image() {
        return productImage;
    }

    public void setProduct_image(String product_image) {
        this.productImage = product_image;
    }

}
