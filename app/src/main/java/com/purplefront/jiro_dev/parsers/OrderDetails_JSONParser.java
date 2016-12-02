package com.purplefront.jiro_dev.parsers;

import android.util.Log;

import com.purplefront.jiro_dev.model.Order_Detail_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 08/11/16.
 */

public class OrderDetails_JSONParser {

    public static List<Order_Detail_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Order_Detail_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Order_Detail_Model flower = new Order_Detail_Model();
                flower.setId(obj.getString("id"));
                flower.setProduct_id(obj.getString("product_id"));
                flower.setProduct_name(obj.getString("product_name"));
                flower.setLogo(obj.getString("logo"));
                flower.setProduct_code(obj.getString("product_code"));
                flower.setPrice_original(obj.getString("price_original"));
                flower.setCurrency_id(obj.getString("currency_id"));
                flower.setQuantity(obj.getString("quantity"));
                flower.setDiscount(obj.getString("discount"));
                flower.setPrice_sold(obj.getString("price_sold"));
                feedslist.add(flower);
            }
            return feedslist;
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            Log.d("json connection", "No internet access" + e);
            return null;
        }
    }
}
