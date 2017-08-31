package com.ausy.yu.bonjourausy.MVP.Candidate;

import android.util.Log;

import com.ausy.yu.bonjourausy.MVP.Base.RdvView;
import com.ausy.yu.bonjourausy.models.IsRelance;
import com.ausy.yu.bonjourausy.models.IsValide;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.networking.NetworkError;
import com.ausy.yu.bonjourausy.networking.NetworkService;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yukaiwen on 23/05/2017.
 */

public class CandidateRdvPresenter {

    private final NetworkService networkService;
    private final RdvView rdvView;
    private CompositeSubscription subscriptions;

    public CandidateRdvPresenter(NetworkService networkService, RdvView rdvView) {
        this.networkService = networkService;
        this.rdvView = rdvView;
        this.subscriptions = new CompositeSubscription();
    }

    public void getRdvListForCandidatMode(int maxNbOfData, int Offset) {

        Subscription subscription = networkService.getRdvListForCandidatMode(maxNbOfData, Offset, new NetworkService.GetRdvListCallback() {
            @Override
            public void onSuccess(List<RdvListData> listRdvListData) {
                rdvView.getRdvListSuccess(listRdvListData);
            }

            @Override
            public void onError(NetworkError networkError) {
                rdvView.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void candidatValideRdvPrevenirManager(int RDVId) {

        Subscription subscription = networkService.candidatValideRdvPrevenirManager(RDVId, new NetworkService.Relance1RDVCallback() {
            @Override
            public void onSuccess(List<IsRelance> isRelance) {
                Log.d("msg", "relance1Rdv : "+isRelance.get(0).getIsRelance());
                rdvView.valideRdvSuccess(isRelance.get(0).getIsRelance());
            }

            @Override
            public void onError(NetworkError networkError) {
                rdvView.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        if(subscriptions!=null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }
}
