package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Create_Address_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 25/10/16.
 */

public class Create_Address_JSONParser {

    public static List<Create_Address_Model> parserFeed(String content) {
        try {
            // JSONObject parentObject = new JSONObject(content);
            JSONArray ar = new JSONArray(content);
            List<Create_Address_Model> respnse = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject parentObject = ar.getJSONObject(i);
                Create_Address_Model flower = new Create_Address_Model();
                flower.setAddressline1(parentObject.getString("address_line1"));
                flower.setAddressline2(parentObject.getString("address_line2"));
                flower.setAddressline3(parentObject.getString("address_line3"));
                flower.setCity(parentObject.getString("city"));
                flower.setPincode(parentObject.getString("zipcode"));
                flower.setState(parentObject.getString("state"));
                flower.setStateid(parentObject.getString("state_id"));
                flower.setId(parentObject.getString("id"));
                respnse.add(flower);
            }

            return respnse;
        } catch (JSONException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

    }

}
