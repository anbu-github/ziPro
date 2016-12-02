package com.purplefront.jiro_dev;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.purplefront.jiro_dev.model.Databaseaccess;

import java.util.List;

/**
 * Created by apple on 03/10/16.
 */

public class Front_Page extends Fragment {

    View rootView;
    List<Databaseaccess> database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.front_page, container, false);
        setHasOptionsMenu(true);

        try {
            getActivity().getActionBar().removeAllTabs();
            getActivity().invalidateOptionsMenu();
            getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getActivity().setTitle((Html.fromHtml("<font color=\"" + getResources().getString(R.string.actionbar_text_color) + "\">" + getString(R.string.app_name_home) + "</font>")));
            getActivity().getActionBar().hide();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbhelp.DatabaseHelper2 entry = new dbhelp.DatabaseHelper2(getActivity());
            entry.close();
            database = entry.getTodo();
        } catch (Exception e) {
            //Log.d("Exception : ", "" + e);
            // Log.d("exception", "user does not exist");
        }

        Button b = (Button)rootView.findViewById(R.id.prereg_existinguser);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


            }
        });

        return rootView;
    }
}
