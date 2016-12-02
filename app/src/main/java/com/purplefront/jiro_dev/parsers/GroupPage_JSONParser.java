package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Group_Category_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 04/10/16.
 */

public class GroupPage_JSONParser {

    public static List<Group_Category_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Group_Category_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Group_Category_Model flower = new Group_Category_Model();
                flower.setGroup_id(obj.getString("id"));
                flower.setGroup_name(obj.getString("name"));
                flower.setRole_id(obj.getString("role_id"));
                flower.setRole_name(obj.getString("role_name"));
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
