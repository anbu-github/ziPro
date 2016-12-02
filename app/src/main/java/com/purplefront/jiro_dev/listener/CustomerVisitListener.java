package com.purplefront.jiro_dev.listener;

/**
 * Created by pf-05 on 11/27/2016.
 */

public interface CustomerVisitListener {
    void getContacts();

    void getCustomers();

    void notifyAdapter();

    void getSelectAddress();
}
