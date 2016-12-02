package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.MemberModel;
import com.purplefront.jiro_dev.model.VisitCustomerDisplayModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pf-05 on 11/29/2016.
 */

public class CustomerVisitDisplay_JSONParser {

    public static List<VisitCustomerDisplayModal> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<VisitCustomerDisplayModal> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                VisitCustomerDisplayModal flower = new VisitCustomerDisplayModal();
                flower.setId(obj.getString("id"));
                flower.setCustomer(obj.getString("customer"));
                flower.setCustomer_contact(obj.getString("customer_contact"));
                flower.setVisit_date(obj.getString("visit_date"));
                flower.setAddress_line1(obj.getString("address_line1"));
                flower.setAddress_line2(obj.getString("address_line2"));
                flower.setAddress_line3(obj.getString("address_line3"));
                flower.setCity(obj.getString("city"));
                flower.setZipcode(obj.getString("zipcode"));
                flower.setState(obj.getString("state"));
                flower.setCountry(obj.getString("country"));
                flower.setNotes(obj.getString("notes"));
                flower.setAssign_to(obj.getString("assigned_to"));
                flower.setCheckin_date(obj.getString("checkin_date"));
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
