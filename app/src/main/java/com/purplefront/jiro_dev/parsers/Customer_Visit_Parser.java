package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.CustomerVisit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 11/28/2016.
 */

public class Customer_Visit_Parser {

    public static List<CustomerVisit> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<CustomerVisit> respnse = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject parentObject = ar.getJSONObject(i);
                CustomerVisit flower = new CustomerVisit();
                flower.setId(parentObject.getString("id"));
                flower.setVisit_date(parentObject.getString("visit_date2"));
                flower.setCustomer(parentObject.getString("customer"));
                flower.setCustomer_contact(parentObject.getString("customer_contact"));
                respnse.add(flower);

            }

            return respnse;
        } catch (JSONException e) {

            //Log.d("error in json", "l " + e);
            return null;
        } catch (Exception e) {
            //Log.d("json connection", "No internet access" + e);
            return null;
        }

    }
}
