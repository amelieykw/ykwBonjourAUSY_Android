package com.ausy.yu.bonjourausy.MVP.Candidate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ausy.yu.bonjourausy.MVP.Base.BaseAppForInjection;
import com.ausy.yu.bonjourausy.MVP.Base.RdvView;
import com.ausy.yu.bonjourausy.MVP.Manager.ManagerRdvPresenter;
import com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter.BackgroundItemDecoration;
import com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter.CandidatRdvAdapter;
import com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter.ManagerRdvAdapter;
import com.ausy.yu.bonjourausy.R;
import com.ausy.yu.bonjourausy.models.RdvItem;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.networking.NetworkService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public class CandidateRdvActivity extends BaseAppForInjection implements RdvView {

    @Inject
    public NetworkService networkService;
    //    private String role;
    public CandidateRdvPresenter candidateRdvPresenter;
    public SwipeRefreshLayout candidat_rdv_swiperefreshlayout;
    public LinearLayoutManager mLayoutManager;
    private List<RdvListData> rdvs = null;
    private List<RdvItem> candidatRdvItemList_dejaPriseEnCharge = new ArrayList<>();
    private List<RdvItem> candidatRdvItemList_nonPriseEnCharge = new ArrayList<>();
    private List<RdvItem> candidatRdvItemList = new ArrayList<>();
    private CandidatRdvAdapter candidatRdvAdapter;
    private AlertDialog alert = null;
    private View alertDialog_view;
    private int lastVisibleItem;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_rdv);
        getDaggerInjector().inject(this);

        /***** Android Tablette never gose to sleep mode when app's running *****/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /***** RecyclerView *****/
        prepareRecyclerView();

        /***** Fetch Data from Server according to User's Choise  *****/
        // use the request details to get datas from server
        candidateRdvPresenter = new CandidateRdvPresenter(networkService, this);
        candidateRdvPresenter.getRdvListForCandidatMode(15, 0);
    }

    /***** RecyclerView *****/
    private void prepareRecyclerView() {
        /***** RecyclerView *****/
        recyclerView = (RecyclerView) findViewById(R.id.manager_rdv_recycle_view);
        candidatRdvAdapter = new CandidatRdvAdapter(candidatRdvItemList);
        candidatRdvAdapter.setOnItemClickListener(recyclerView_CandidatRdvAdapter_Item_Handler);
        recyclerView.setAdapter(candidatRdvAdapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.scrollToPosition(1);
        recyclerView.setLayoutManager(mLayoutManager);
        setHeaderView(recyclerView);
        setFooterView(recyclerView);

        /***** Add Divider *****/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.listdivider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        /***** SwipeRefreshLayout *****/
        candidat_rdv_swiperefreshlayout = (SwipeRefreshLayout)this.findViewById(R.id.manager_rdv_swiperefreshlayout);
        candidat_rdv_swiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        candidat_rdv_swiperefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        candidat_rdv_swiperefreshlayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        /***** SwipeRefreshLayout Scroll Down Refresh *****/
        candidat_rdv_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                candidatRdvItemList_nonPriseEnCharge.removeAll(candidatRdvItemList_nonPriseEnCharge);
                candidatRdvItemList_dejaPriseEnCharge.removeAll(candidatRdvItemList_dejaPriseEnCharge);
                candidatRdvItemList.removeAll(candidatRdvItemList);
                candidatRdvAdapter.notifyDataSetChanged();
                candidateRdvPresenter.getRdvListForCandidatMode(15, 0);
                candidat_rdv_swiperefreshlayout.setRefreshing(false);
            }
        });

        /***** RecyclerView Pull Up Load More *****/
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == candidatRdvAdapter.getItemCount()) {
                    candidatRdvAdapter.changeMoreStatus(candidatRdvAdapter.LOADING_MORE);
                    candidateRdvPresenter.getRdvListForCandidatMode(15, lastVisibleItem);
                    candidatRdvAdapter.changeMoreStatus(candidatRdvAdapter.PULLUP_LOAD_MORE);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                Log.d("lastVisibleItem", lastVisibleItem+"");
                Log.d("getItemCount()", candidatRdvAdapter.getItemCount()+"");
            }
        });
    }

    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(this).inflate(R.layout.activity_manager_rdv_header, view, false);
        candidatRdvAdapter.setHeaderView(header);
    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(this).inflate(R.layout.activity_manager_rdv_footer, view, false);
        candidatRdvAdapter.setFooterView(footer);
    }

    CandidatRdvAdapter.OnItemClickListener recyclerView_CandidatRdvAdapter_Item_Handler = new CandidatRdvAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CandidateRdvActivity.this);

        if(!itemDismiss) {
            alertDialog_view = CandidateRdvActivity.this.getLayoutInflater().inflate(R.layout.activity_manager_alert_dialog_view_custom, null, false);
            builder.setView(alertDialog_view);
            builder.setCancelable(false);
            alert = builder.create();

            // alertMessage
            TextView alertMessage = (TextView)alertDialog_view.findViewById(R.id.alertMessage);
            alertMessage.setText(alertDialogMessage);

            // the dialog window will disappear if it dosen't be actived for 2 mins
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    new CountDownTimer(120000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            alert.dismiss();
                            itemViewSelected.setBackgroundColor(getResources().getColor(itemPositionSelected % 2 == 0 ? R.color.color_item_evenBackground : R.color.color_item_oddBackground));
                        }
                    }.start();
                }
            });

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
                    candidateRdvPresenter.candidatValideRdvPrevenirManager(rdvs.get(itemPositionSelected-1).getRDVId());
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
            alertDialog_view = CandidateRdvActivity.this.getLayoutInflater().inflate(R.layout.activity_manager_alert_dialog_confirm_custom, null, false);
            builder.setView(alertDialog_view);
            builder.setCancelable(false);
            alert = builder.create();

            // the dialog window will disappear after 10 seconds if it dosen't be closed
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            alert.dismiss();
                        }
                    }.start();
                }
            });

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
                Log.d("isValide : ", rdvs.get(i).isValide()+"");
                if(rdvs.get(i).isValide() == false) {
                    candidatRdvItemList_nonPriseEnCharge.add(new RdvItem(rdv.getRdvPrenom()+" "+rdv.getRdvNom(), "j'ai RDV avec "+rdv.getContactPrenom()+" "+rdv.getContactNom(), "à "+heurePrevue[0]+"h"+heurePrevue[1]));
                }
                if(rdvs.get(i).isValide() == true) {
                    candidatRdvItemList_dejaPriseEnCharge.add(new RdvItem(rdv.getRdvPrenom()+" "+rdv.getRdvNom(), "j'ai RDV avec "+rdv.getContactPrenom()+" "+rdv.getContactNom(), "à "+heurePrevue[0]+"h"+heurePrevue[1]));
                }
            }
        }
        candidatRdvItemList.addAll(candidatRdvItemList_nonPriseEnCharge);
        candidatRdvItemList.addAll(candidatRdvItemList_nonPriseEnCharge.size(), candidatRdvItemList_dejaPriseEnCharge);
        candidatRdvAdapter.notifyDataSetChanged();

        /***** Add Alternate Row Colors *****/
        recyclerView.addItemDecoration(new BackgroundItemDecoration(R.color.color_item_oddBackground, R.color.color_item_evenBackground, "Candidat", candidatRdvItemList.size()-candidatRdvItemList_dejaPriseEnCharge.size(), candidatRdvItemList.size()-1));
    }

    @Override
    public void valideRdvSuccess(int isRelance1) {
        Toast.makeText(CandidateRdvActivity.this, "Ce RDV a été validé. Vous pouvez glisser pour actualiser!", Toast.LENGTH_LONG).show();
    }
}
