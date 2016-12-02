package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Purchase_Order_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 02/11/16.
 */

public class OrderDisp_JSONParser {

    public static List<Purchase_Order_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Purchase_Order_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Purchase_Order_Model flower = new Purchase_Order_Model();
                flower.setId(obj.getString("id"));
                flower.setDate(obj.getString("created"));
                flower.setCustomer_company(obj.getString("customer_name"));
                flower.setTotal(obj.getString("order_total"));
                flower.setAssignuser(obj.getString("assigned_user"));
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
