package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class ProductsubList_JSONParser {

    public static List<Product> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Product> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Product flower = new Product();
                flower.setProduct_id(obj.getString("id"));
                flower.setProduct_name(obj.getString("name"));
                flower.setProduct_image(obj.getString("image"));
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
