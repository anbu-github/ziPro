package com.purplefront.jiro_dev.model;

/**
 * Created by apple on 04/10/16.
 */

public class Sub_Category_Model {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String id;
    String name;
    String image;

    public String getObject_master_id() {
        return object_master_id;
    }

    public void setObject_master_id(String object_master_id) {
        this.object_master_id = object_master_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    String object_master_id;
    String object_id;

}
