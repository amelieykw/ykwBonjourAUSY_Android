package com.ausy.yu.bonjourausy.MVP.Main;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ausy.yu.bonjourausy.MVP.Base.BaseAppForInjection;
import com.ausy.yu.bonjourausy.MVP.Candidate.CandidateRdvActivity;
import com.ausy.yu.bonjourausy.MVP.Manager.ManagerRdvActivity;
import com.ausy.yu.bonjourausy.R;
import com.ausy.yu.bonjourausy.models.SiteListData;
import com.ausy.yu.bonjourausy.networking.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class MainActivity extends BaseAppForInjection implements MainView {

    @Inject
    public NetworkService networkService;
    private Button button_roles, button_sites, button_next;
    private PopupWindow popupWindow_roles, popupWindow_sites;
    private List<String> data_roles;
    private List<String> data_sites = null;
    private String chosed_data_role = null;
    private String chosed_data_site = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDaggerInjector().inject(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();

        MainPresenter mainPresenter = new MainPresenter(networkService, this);
        mainPresenter.getSiteList();
    }

    private void initView() {
        button_roles = (Button) findViewById(R.id.button_roles);
        button_sites = (Button) findViewById(R.id.button_sites);
        button_next = (Button) findViewById(R.id.button_next);

        // Roles
        data_roles = new ArrayList<>();
        data_roles.add("Manager");
        data_roles.add("Candidat");

        button_roles.setOnClickListener(button_roles_handler);

        // Suivant (Next)
        button_next.setOnClickListener(button_next_handler);

    }

    // Roles
    View.OnClickListener button_roles_handler = new View.OnClickListener() {
        public void onClick(View v) {
            popupWindow_roles = popupWindowList(data_roles, v, button_roles);
            popupWindow_roles.showAsDropDown(v); // show pop up like dropdown list
        }
    };

    // Sites
    View.OnClickListener button_sites_handler = new View.OnClickListener() {
        public void onClick(View v) {
            popupWindow_sites = popupWindowList(data_sites, v, button_sites);
            popupWindow_sites.showAsDropDown(v); // show pop up like dropdown list
        }
    };

    // Suivant (Next)
    View.OnClickListener button_next_handler = new View.OnClickListener() {
        public void onClick(View v) {
            if(chosed_data_role != null) {

                Bundle bundle = new Bundle();

                if(chosed_data_role == "Manager") {
                    Intent intent_manager = new Intent(MainActivity.this, ManagerRdvActivity.class);
                    if(chosed_data_site != null) {
                        bundle.putString("site", chosed_data_site);
                        intent_manager.putExtras(bundle);
                    }
                    startActivity(intent_manager);
                } else if(chosed_data_role == "Candidat") {
                    Intent intent_candidate = new Intent(MainActivity.this, CandidateRdvActivity.class);
                    startActivity(intent_candidate);
                } else {
                    Log.e("error", "something's wrong with chosed_data_role ");
                }

            } else if(chosed_data_site != null) {
                Toast.makeText(MainActivity.this, "Vous n'avez pas encore choisi l'application que vous souhaitez installer", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Veuillez choisir l'application que vous souhaitez installer et votre agence", Toast.LENGTH_LONG).show();
            }

            Log.d("role : ", chosed_data_role);
            if(chosed_data_site != null) {
                Log.d("site : ", chosed_data_site);
            }else {
                Log.d("site : ", "");
            }

        }
    };

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getSiteListSuccess(List<SiteListData> listSiteListData) {
        // Sites
        data_sites = new ArrayList<>();
        List<SiteListData> sites = listSiteListData;

        for(SiteListData site : sites) {
            data_sites.add(site.getLibelle());
        }

        button_sites.setOnClickListener(button_sites_handler);
    }

    /**
     * show popup window method return Popupwindow
     */
    private PopupWindow popupWindowList(List<String> data, View view, Button button) {
        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
        // the drop down list is a list view
        ListView dropdown_listView = new ListView(this);
        // set our adapter and pass our pop up window contents
        dropdown_listView.setAdapter(adapter);
        // set on item selected
        dropdown_listView.setOnItemClickListener(onItemClickListener(button));

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(((View) view.getParent()).getMeasuredHeight() - view.getHeight());
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // set the list view as pop up window content
        popupWindow.setContentView(dropdown_listView);

        return popupWindow;
    }

    private AdapterView.OnItemClickListener onItemClickListener(Button button) {
        final Button btn = button;

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if(button_sites.isEnabled() && btn == button_sites) {
                    chosed_data_site = data_sites.get(position);
                    Toast.makeText(MainActivity.this, "Vous avez choisi "+chosed_data_site, Toast.LENGTH_LONG).show();

                    if(chosed_data_role != null && chosed_data_site != null) {
                        Toast.makeText(MainActivity.this, "Veuillez cliquer suivant", Toast.LENGTH_LONG).show();
                    }
                }
                if(btn == button_roles){
                    chosed_data_role = data_roles.get(position);
                    Toast.makeText(MainActivity.this, "Vous avez choisi "+chosed_data_role, Toast.LENGTH_LONG).show();

                    if(chosed_data_role == "Candidat") {
                        button_sites.setEnabled(false);
                        if(chosed_data_site != null) {
                            chosed_data_site = null;
                        }
                    }
                    if(chosed_data_role == "Manager"){
                        button_sites.setEnabled(true);
                    }

                    if(chosed_data_role != null &&
                            ((button_sites.isEnabled() && chosed_data_site != null) || (!button_sites.isEnabled() && chosed_data_site == null))) {
                        Toast.makeText(MainActivity.this, "Veuillez cliquer suivant", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d("msg", "the reference of btn is wrong");
                }
            }

        };
    }
}
