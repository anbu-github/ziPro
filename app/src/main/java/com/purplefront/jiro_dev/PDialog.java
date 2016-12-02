package com.purplefront.jiro_dev;

import android.app.ProgressDialog;
import android.content.Context;

import com.purplefront.jiro_dev.controller.AppController;

/**
 * Created by pf-05 on 11/28/2016.
 */

public class PDialog extends AppController {

    static ProgressDialog pDialog = null;

    public static  void show(Context csContext) {
        pDialog = new ProgressDialog(csContext);
        // pDialog = new ProgressDialog(csContext,R.style.MyTheme);
        // Showing progress dialog before making http request

        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

    }



    public static  void show(Context csContext,String message) {
        pDialog = new ProgressDialog(csContext);
        // pDialog = new ProgressDialog(csContext,R.style.MyTheme);
        // Showing progress dialog before making http request
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }



    public static  void hide() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
