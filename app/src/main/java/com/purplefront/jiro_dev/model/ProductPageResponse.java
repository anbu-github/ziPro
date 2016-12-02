package com.purplefront.jiro_dev.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apple on 22/10/16.
 */

public class ProductPageResponse {
    @SerializedName("category")
    private List<Product> categories;

    @SerializedName("products")
    private List<Product> products;


    public List<Product> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }
}
