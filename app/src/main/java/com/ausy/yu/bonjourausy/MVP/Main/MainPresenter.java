package com.ausy.yu.bonjourausy.MVP.Main;

import android.util.Log;

import com.ausy.yu.bonjourausy.MVP.Main.MainView;
import com.ausy.yu.bonjourausy.models.SiteListData;
import com.ausy.yu.bonjourausy.networking.NetworkError;
import com.ausy.yu.bonjourausy.networking.NetworkService;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public class MainPresenter {

    private final NetworkService networkService;
    private final MainView mainView;
    private CompositeSubscription subscriptions;

    public MainPresenter(NetworkService networkService, MainView mainView) {
        this.networkService = networkService;
        this.mainView = mainView;
//        Log.d("msg", "mainView : "+mainView.toString());
        this.subscriptions = new CompositeSubscription();
    }

    public void getSiteList() {

//        Log.d("msg", "enter mainPresenter getSiteList");

        Subscription subscription = networkService.getSiteList(new NetworkService.GetSiteListCallback() {
            @Override
            public void onSuccess(List<SiteListData> listSiteListData) {
//                Log.d("msg", "onSuccess mainPresenter getSiteList");
                mainView.getSiteListSuccess(listSiteListData);
            }

            @Override
            public void onError(NetworkError networkError) {
                mainView.onFailure(networkError.getAppErrorMessage());
            }

        });

//        if(subscription == null) {
//            Log.d("msg", "subscription == null");
//        } else {
//            Log.d("msg", "subscription != null");
//        }

        subscriptions.add(subscription);
    }

    public void onStop() {
        Log.d("msg", "MainPresenter onStop");
        if(subscriptions!=null && !subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }
}
