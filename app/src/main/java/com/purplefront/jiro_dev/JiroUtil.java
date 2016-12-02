package com.purplefront.jiro_dev;

import android.content.Context;
import android.content.SharedPreferences;

import com.purplefront.jiro_dev.controller.AppController;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by apple on 27/10/16.
 */

public class JiroUtil{

    public static SharedPreferences productPreference(){
        return AppController.getInstance().getSharedPreferences("products", Context.MODE_PRIVATE);
    }

    public static void addProducts(String productId)
    {
        SharedPreferences.Editor editor = productPreference().edit();
        try {
            JSONArray products = new JSONArray(getProducts());
            if (!isAdded(productId))
                products.put(productId);
            editor.putString("products", products.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void removeProduct(String productId) {
        SharedPreferences.Editor editor = productPreference().edit();
        try {
            JSONArray products = new JSONArray(getProducts());
            JSONArray productsToSave = new JSONArray();
            for (int i = 0; i < products.length(); i++) {
                if (!products.get(i).toString().equals(productId)) {
                    productsToSave.put(products.get(i).toString());
                }
            }
            editor.putString("products", productsToSave.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void removeAllProduct() {
        SharedPreferences.Editor editor = productPreference().edit();
        try {
            JSONArray products = new JSONArray(getProducts());
            JSONArray productsToSave = new JSONArray();

            editor.putString("products", productsToSave.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getProducts(){
        return productPreference().getString("products", "[]");
    }

    public static void deleteProducts() {
        SharedPreferences.Editor editor = productPreference().edit();
        editor.remove("products");
        editor.apply();
    }

    public static boolean isAdded(String productId) {
        boolean added = false;
        try {
            JSONArray products = new JSONArray(getProducts());
            for (int i = 0; i < products.length(); i++) {
                if (products.get(i).toString().equals(productId))
                    added = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return added;
    }
}
