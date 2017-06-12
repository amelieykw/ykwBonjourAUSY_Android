package com.ausy.yu.bonjourausy.MVP.Manager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DividerItemDecoration;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ausy.yu.bonjourausy.MVP.Base.RdvView;
import com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter.BackgroundItemDecoration;
import com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter.ManagerRdvAdapter;
import com.ausy.yu.bonjourausy.MVP.Base.BaseAppForInjection;
import com.ausy.yu.bonjourausy.R;
import com.ausy.yu.bonjourausy.models.ManagerRdvItem;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.networking.NetworkService;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Unlike ListView, there is no way to add or remove items directly through the RecyclerView adapter.
 * You need to make changes to the data source directly and notify the adapter of any changes.
 * Also, whenever adding or removing elements, always make changes to the existing list.
 * since it has a memory reference to the old list.
 *
 * Created by yukaiwen on 17/04/2017.
 */

public class ManagerRdvActivity extends BaseAppForInjection implements RdvView {

    @Inject
    public NetworkService networkService;
//    private String role;
    public ManagerRdvPresenter managerRdvPresenter;
    public SwipeRefreshLayout manager_rdv_swiperefreshlayout;
    public LinearLayoutManager mLayoutManager;
    private String site;
    private List<RdvListData> rdvs = null;
    private List<ManagerRdvItem> managerRdvItemList = new ArrayList<ManagerRdvItem>();
    private ManagerRdvAdapter managerRdvAdapter;
    private AlertDialog alert = null;
    private View alertDialog_view;
    private int lastVisibleItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_rdv);
        getDaggerInjector().inject(this);

        /***** RecyclerView *****/
        prepareRecyclerView();

        /***** Get Data of User's Choise from MainActivity *****/
        // get the request details from previous activity
        getDataFromMainActivity();

        /***** Fetch Data from Server according to User's Choise  *****/
        // use the request details to get datas from server
        managerRdvPresenter = new ManagerRdvPresenter(networkService, this);
        managerRdvPresenter.getRdvList(site, 15, 0);
    }

    /***** RecyclerView *****/
    private void prepareRecyclerView() {
        /***** RecyclerView *****/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.manager_rdv_recycle_view);
        managerRdvAdapter = new ManagerRdvAdapter(managerRdvItemList);
        managerRdvAdapter.setOnItemClickListener(recyclerView_ManagerRdvAdapter_Item_Handler);
        recyclerView.setAdapter(managerRdvAdapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.scrollToPosition(1);
        recyclerView.setLayoutManager(mLayoutManager);
        setHeaderView(recyclerView);
        setFooterView(recyclerView);
        /***** Add Divider *****/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.listdivider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        /***** Add Alternate Row Colors *****/
        recyclerView.addItemDecoration(new BackgroundItemDecoration(R.color.color_item_oddBackground, R.color.color_item_evenBackground));
        /***** SwipeRefreshLayout *****/
        manager_rdv_swiperefreshlayout = (SwipeRefreshLayout)this.findViewById(R.id.manager_rdv_swiperefreshlayout);
        manager_rdv_swiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        manager_rdv_swiperefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        manager_rdv_swiperefreshlayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        /***** SwipeRefreshLayout Scroll Down Refresh *****/
        manager_rdv_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                managerRdvItemList.removeAll(managerRdvItemList);
                managerRdvAdapter.notifyDataSetChanged();
                managerRdvPresenter.getRdvList(site, 15, 0);
                manager_rdv_swiperefreshlayout.setRefreshing(false);
            }
        });
        /***** RecyclerView Pull Up Load More *****/
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == managerRdvAdapter.getItemCount()) {
                    managerRdvAdapter.changeMoreStatus(managerRdvAdapter.LOADING_MORE);
                    managerRdvPresenter.getRdvList(site, 15, lastVisibleItem);
                    managerRdvAdapter.changeMoreStatus(managerRdvAdapter.PULLUP_LOAD_MORE);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                Log.d("lastVisibleItem", lastVisibleItem+"");
                Log.d("getItemCount()", managerRdvAdapter.getItemCount()+"");
            }
        });
    }

    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.activity_manager_rdv_header, view, false);
        managerRdvAdapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.activity_manager_rdv_footer, view, false);
        managerRdvAdapter.setFooterView(footer);
    }

    /***** Get Data of User's Choise from MainActivity *****/
    private void getDataFromMainActivity(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        site = bundle.getString("site");
    }

    ManagerRdvAdapter.OnItemClickListener recyclerView_ManagerRdvAdapter_Item_Handler = new ManagerRdvAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Log.d("rdvs", "rdvs"+rdvs.get(position-1).getHeurePrevu());
            RdvListData rdvListData = rdvs.get(position-1);
            String candidateNom = rdvListData.getRdvNom();
            String managerNom = rdvListData.getContactNom();

            view.setBackgroundColor(getResources().getColor(R.color.color_item_press));

            String alertDialogMessage = "Bonjour M/Mme "+candidateNom+" ,cliquez\nsur OK pour mettre fin à\n l'attente de M/Mme "+managerNom;
            showAlertDialogView(view, position, alertDialogMessage, false);
        }
    };

    private void showAlertDialogView(View view, int position, String alertDialogMessage, boolean dimiss) {
        final View itemViewSelected = view;
        final int itemPositionSelected = position;
        final boolean itemDismiss = dimiss;

        alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ManagerRdvActivity.this);

        if(!itemDismiss) {
            alertDialog_view = ManagerRdvActivity.this.getLayoutInflater().inflate(R.layout.activity_manager_alert_dialog_view_custom, null, false);
            builder.setView(alertDialog_view);
            builder.setCancelable(false);
            alert = builder.create();

            // alertMessage
            TextView alertMessage = (TextView)alertDialog_view.findViewById(R.id.alertMessage);
            alertMessage.setText(alertDialogMessage);

            // Button alertDialog Annuler
            alertDialog_view.findViewById(R.id.alertDialog_annuler).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alert.dismiss();
                    itemViewSelected.setBackgroundColor(getResources().getColor(itemPositionSelected % 2 == 0 ? R.color.color_item_evenBackground : R.color.color_item_oddBackground));
                }
            });

            // Button alertDialog OK
            alertDialog_view.findViewById(R.id.alertDialog_OK).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                  sendSMS(itemPositionSelected);
                    managerRdvPresenter.valideRdv(rdvs.get(itemPositionSelected-1).getRDVId());
                    itemViewSelected.setBackgroundColor(getResources().getColor(R.color.color_item_blocked));
                    itemViewSelected.setClickable(false);
                    if(itemDismiss) {
                        alert.dismiss();
                    }else {
                        alert.dismiss();
                        TextView alertTitle = (TextView)alertDialog_view.findViewById(R.id.alertTitle);
                        alertTitle.setText((CharSequence)"Merci");
                        String newAlertDialogMessage = "Bon entretien !";
                        showAlertDialogView(itemViewSelected, itemPositionSelected, newAlertDialogMessage, true);
                    }
                }
            });
        } else {
            alertDialog_view = ManagerRdvActivity.this.getLayoutInflater().inflate(R.layout.activity_manager_alert_dialog_confirm_custom, null, false);
            builder.setView(alertDialog_view);
            builder.setCancelable(false);
            alert = builder.create();

            // Button alertDialog Annuler
            alertDialog_view.findViewById(R.id.alertDialog_fermer).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alert.dismiss();
                }
            });
        }
        alert.show();           //show the alert dialog
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void sendSMS(int position) {
        String phoneNumber = "0667776584";
        String message = "YU Kaiwen, t'es con!";

        // check if the phoneNumber legal or not
        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getRdvListSuccess(List<RdvListData> listRdvListData) {
        rdvs = listRdvListData;

        int rdvsLength = rdvs.size();

        if(rdvsLength > 0) {
            for(int i = 0; i < rdvsLength; i++) {
                RdvListData rdv = rdvs.get(i);
                String heurePrevue[] = rdv.getHeurePrevu().split(" ", 2)[1].split(":");
                managerRdvItemList.add(new ManagerRdvItem(rdv.getRdvPrenom()+" "+rdv.getRdvNom(), "j'ai RDV avec "+rdv.getContactPrenom()+" "+rdv.getContactNom(), "à "+heurePrevue[0]+"h"+heurePrevue[1]));
            }
        }

        managerRdvAdapter.notifyDataSetChanged();
    }

    @Override
    public void valideRdvSuccess(int isValide) {
        Toast.makeText(ManagerRdvActivity.this, "Ce RDV a été validé. Vous pouvez glisser pour actualiser!", Toast.LENGTH_LONG).show();
    }
}