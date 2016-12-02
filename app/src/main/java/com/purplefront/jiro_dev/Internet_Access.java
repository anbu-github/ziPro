package com.purplefront.jiro_dev;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by apple on 04/10/16.
 */

public class Internet_Access {

    public boolean isonline(Context v)
    {
        ConnectivityManager cm = (ConnectivityManager) v.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        return netinfo != null && netinfo.isConnectedOrConnecting();
    }
}
