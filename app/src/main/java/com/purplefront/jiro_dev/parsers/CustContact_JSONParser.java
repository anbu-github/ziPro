package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.CustomerContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 19/10/16.
 */

public class CustContact_JSONParser {

    public static List<CustomerContact> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<CustomerContact> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                CustomerContact flower = new CustomerContact();
                flower.setName(obj.getString("name"));
                flower.setEmail(obj.getString("email"));
                flower.setPhone(obj.getString("contact"));
                flower.setTitle(obj.getString("title"));
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
