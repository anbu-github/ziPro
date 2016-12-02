package com.purplefront.jiro_dev.model;

/**
 * Created by pf-05 on 11/27/2016.
 */

public class Contact {
    private int contactId;
    private String contactName;
    private boolean isSelected;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
