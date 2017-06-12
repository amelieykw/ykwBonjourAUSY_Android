package com.ausy.yu.bonjourausy.MVP.Manager;

import android.util.Log;

import com.ausy.yu.bonjourausy.MVP.Base.RdvView;
import com.ausy.yu.bonjourausy.models.IsValide;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.networking.NetworkError;
import com.ausy.yu.bonjourausy.networking.NetworkService;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yukaiwen on 18/04/2017.
 */

public class ManagerRdvPresenter {

    private final NetworkService networkService;
    private final RdvView rdvView;
    private CompositeSubscription subscriptions;

    public ManagerRdvPresenter(NetworkService networkService, RdvView rdvView) {
        this.networkService = networkService;
        this.rdvView = rdvView;
        this.subscriptions = new CompositeSubscription();
    }

    public void getRdvList(String siteLibelle, int maxNbOfData, int Offset) {

        Log.d("site", "ManagerRdvPresenter => "+siteLibelle);

        Subscription subscription = networkService.getRdvList(siteLibelle, maxNbOfData, Offset, new NetworkService.GetRdvListCallback() {
            @Override
            public void onSuccess(List<RdvListData> listRdvListData) {
//                Log.d("msg", "onSuccess mainPresenter getSiteList");
                rdvView.getRdvListSuccess(listRdvListData);
            }

            @Override
            public void onError(NetworkError networkError) {
                rdvView.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void valideRdv(int RDVId) {

        Subscription subscription = networkService.valideRdv(RDVId, new NetworkService.ValideRDVCallback() {
            @Override
            public void onSuccess(List<IsValide> isValide) {
                Log.d("msg", "valideRdv : "+isValide.get(0).getValide());
                rdvView.valideRdvSuccess(isValide.get(0).getValide());
            }

            @Override
            public void onError(NetworkError networkError) {
                rdvView.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        Log.d("msg", "ManagerRdvPresenter onStop");
        if(subscriptions!=null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }
}
