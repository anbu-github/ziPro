package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.TaxTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by apple on 15/11/16.
 */

public class TaxList_JSONParser {

    public static ArrayList<TaxTypeModel> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            ArrayList<TaxTypeModel> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                TaxTypeModel flower = new TaxTypeModel();
                flower.setTax_id(obj.getString("tax_id"));
                flower.setTax_name(obj.getString("tax_name"));
                flower.setTax_type_id(obj.getString("tax_type_id"));
                flower.setTax_type(obj.getString("tax_type"));
                flower.setTax_value(obj.getString("tax_value"));

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
