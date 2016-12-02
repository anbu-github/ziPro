package com.purplefront.jiro_dev.parsers;
import android.util.Log;

import com.purplefront.jiro_dev.model.ExistingUser_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class ExistingUser_JSONParser {


    public static List<ExistingUser_Model> parserFeed(String content) {

        List<ExistingUser_Model> createadmin = new ArrayList<>();

        try {


            JSONArray parentArray = new JSONArray(content);

            for (int i = 0; i < parentArray.length(); i++)
            {
                JSONObject parentObject = parentArray.getJSONObject(i);
                ExistingUser_Model flower = new ExistingUser_Model();
                flower.setId(parentObject.getString("id"));
                flower.setBusiness_id(parentObject.getString("business_id"));
                flower.setName(parentObject.getString("name"));
                flower.setEmail(parentObject.getString("email"));
                flower.setMessage(parentObject.getString("success"));
                flower.setContact(parentObject.getString("contact"));
                flower.setActive(parentObject.getString("status"));
                flower.setProfile_id(parentObject.getString("profile_id"));
                createadmin.add(flower);
            }


        } catch (JSONException e) {
            Log.d("error in json", "l " + e);
            return null;
        } catch (Exception e) {
            Log.d("json connection", "No internet access" + e);
            return null;
        }
        return createadmin;
    }


}
