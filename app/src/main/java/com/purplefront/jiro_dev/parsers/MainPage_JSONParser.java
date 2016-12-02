package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Home_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class MainPage_JSONParser {
    public static List<Home_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Home_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Home_Model flower = new Home_Model();
                flower.setId(obj.getString("id"));
                flower.setName(obj.getString("name"));
                feedslist.add(flower);
            }
            return feedslist;
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
