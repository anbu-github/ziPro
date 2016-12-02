package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.AttributeModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by apple on 04/11/16.
 */

public class AttributeView_JSONParser {

    public static ArrayList<AttributeModel> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            ArrayList<AttributeModel> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                AttributeModel flower = new AttributeModel();

                flower.setId(obj.getString("id"));
                flower.setName(obj.getString("object_attr_name"));


//                flower.setProduct_id(obj.getString("id"));
//                flower.setProduct_name(obj.getString("name"));
//                flower.setProduct_image(obj.getString("image"));
//                flower.setProduct_price(obj.getString("price"));

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
