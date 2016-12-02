package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.MemberModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 13/10/16.
 */

public class MemProfileCategory_JSONParser {

    public static List<MemberModel> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<MemberModel> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                MemberModel flower = new MemberModel();
                flower.setId(obj.getString("id"));
                flower.setRole_id(obj.getString("role_id"));
                flower.setRole(obj.getString("role"));
                flower.setName(obj.getString("name"));
                flower.setContact(obj.getString("contact"));
                flower.setEmail(obj.getString("email"));
                flower.setProfile_id(obj.getString("profile_id"));
                flower.setActive(obj.getString("active"));
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
