package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 12/1/2016.
 */

public class ApprovedCustomerParser {
    public static List<CustomerModel> parserFeed(String content) {
        try {
            //JSONArray ar = new JSONArray(content);
            JSONObject jsonObject1 = new JSONObject(content);

            String jsonArray1 = jsonObject1.getString("customers");

            JSONArray ar = new JSONArray(jsonArray1);

            List<CustomerModel> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                CustomerModel flower = new CustomerModel();
                flower.setId(obj.getString("id"));
                flower.setName(obj.getString("name"));
                flower.setContact(obj.getString("contact"));
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
