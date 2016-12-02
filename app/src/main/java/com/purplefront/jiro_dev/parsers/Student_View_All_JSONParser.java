package com.purplefront.jiro_dev.parsers;

import android.util.Log;

import com.purplefront.jiro_dev.model.Student_View_All_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Student_View_All_JSONParser {

    public static List<Student_View_All_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Student_View_All_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Student_View_All_Model flower = new Student_View_All_Model();
                flower.setStudent_name(obj.getString("attr_name"));
                flower.setGrade_name(obj.getString("attr_value"));
                feedslist.add(flower);
            }
            return feedslist;
        } catch (JSONException e) {

            Log.d("error in json", "l " + e);
            return null;
        } catch (Exception e) {
            Log.d("json connection", "No internet access" + e);
            return null;
        }
    }
}
