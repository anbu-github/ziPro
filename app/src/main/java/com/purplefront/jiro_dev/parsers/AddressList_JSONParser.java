package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Create_Address_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 18/10/16.
 */

public class AddressList_JSONParser {

    public static List<Create_Address_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Create_Address_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Create_Address_Model flower = new Create_Address_Model();
//                flower.setProduct_id(obj.getString("id"));
//                flower.setProduct_name(obj.getString("name"));
//                flower.setProduct_image(obj.getString("image"));

                flower.setAddressline1(obj.getString("address_line1"));
                flower.setAddressline2(obj.getString("address_line2"));
                flower.setAddressline3(obj.getString("address_line3"));
                flower.setCity(obj.getString("city"));
                flower.setState(obj.getString("state"));
                flower.setPincode(obj.getString("zipcode"));
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
