package com.purplefront.jiro_dev.parsers;

import com.purplefront.jiro_dev.model.Product_List_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by apple on 26/10/16.
 */

public class CatProdList_JSONParser {

    public static ArrayList<Product_List_Model> parserFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            ArrayList<Product_List_Model> feedslist = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Product_List_Model flower = new Product_List_Model();
                flower.setProduct_id(obj.getString("id"));
                flower.setProduct_name(obj.getString("name"));
                flower.setProduct_image(obj.getString("image"));
                flower.setProduct_price(obj.getString("price"));

                String uomlist = obj.getString("uom_list");
                JSONArray ar1 = new JSONArray(uomlist);

               ArrayList<String> uomidlist = new ArrayList<>();

                ArrayList<String> uomnamelist1 = new ArrayList<>();

                ArrayList<String> uompricelist1 = new ArrayList<>();

                for (int index = 0; index < ar1.length(); index++) {

                    JSONObject obj1 = ar1.getJSONObject(index);

                    uomidlist.add(obj1.getString("uom_id"));
                    uomnamelist1.add(obj1.getString("uom_name")+" - "+obj1.getString("price"));
                    uompricelist1.add(obj1.getString("price"));


                }


                flower.setUomidlist(uomidlist);
                flower.setUomnamelist(uomnamelist1);
                flower.setUompricelist1(uompricelist1);

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
