package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Product_Description_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class ProductDescription_JSONParser {

    public static List<Product_Description_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Product_Description_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Product_Description_Model flower = new Product_Description_Model();
                flower.setProduct_code(obj.getString("code"));
                flower.setProduct_name(obj.getString("name"));
                flower.setProduct_specification(obj.getString("specification"));
                flower.setProduct_description(obj.getString("description"));
                flower.setVideo_url(obj.getString("video_url"));
                flower.setCode_name(obj.getString("product_code"));
                flower.setPrice_name(obj.getString("price"));
                //flower.setPrice_name(obj.getString("price_name"));
                feedslist.add(flower);
            }
            return feedslist;
        } catch (JSONException e) {

            //Log.d("error in json", "l " + e);
            return null;
        } catch (Exception e) {
            //Log.d("json connection", "No internet access" + e);
            return null;
        }
    }

}
