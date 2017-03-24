package com.ausy.yu.bonjourausy;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button button_roles, button_sites;
    private PopupWindow popupWindow_roles, popupWindow_sites;
    private ArrayList<String> data_roles, data_sites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_roles = (Button) findViewById(R.id.button_roles);
        button_sites = (Button) findViewById(R.id.button_sites);

        // Roles
        data_roles = new ArrayList<>();
        data_roles.add("Manager");
        data_roles.add("Candidate");

        // Sites
        data_sites = new ArrayList<>();
        data_sites.add("Sèvre");
        data_sites.add("Aix-en-provence");
        data_sites.add("Bordeaux");
        data_sites.add("Brest");
        data_sites.add("Cherbourg");
        data_sites.add("Grenoble");
        data_sites.add("Lannion");
        data_sites.add("Le mans");
        data_sites.add("Lille");
        data_sites.add("Lyon");
        data_sites.add("Montpellier");
        data_sites.add("Nantes");
        data_sites.add("Nice");
        data_sites.add("Niort");
        data_sites.add("Orléans");
        data_sites.add("Pierrelotte");
        data_sites.add("Rennes");
        data_sites.add("Strasbourg");
        data_sites.add("Toulouse");
        data_sites.add("Tours");

        button_roles.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_roles = popupWindowList(data_roles, v);
                popupWindow_roles.showAsDropDown(v); // show pop up like dropdown list
            }
        });

        button_sites.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_sites = popupWindowList(data_sites, v);
                popupWindow_sites.showAsDropDown(v); // show pop up like dropdown list
            }
        });
    }

    /**
     * show popup window method return Popupwindow
     */
    private PopupWindow popupWindowList(ArrayList<String> data, View view) {
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        // the drop down list is a list view
        ListView dropdown_listView = new ListView(this);
        // set our adapter and pass our pop up window contents
        dropdown_listView.setAdapter(adapter);

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(((View)view.getParent()).getMeasuredHeight() - view.getHeight());
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // set the list view as pop up window content
        popupWindow.setContentView(dropdown_listView);

        return popupWindow;
    }
}
