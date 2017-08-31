package com.ausy.yu.bonjourausy.networking;

import android.util.Log;

import com.ausy.yu.bonjourausy.models.IsRelance;
import com.ausy.yu.bonjourausy.models.IsValide;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.models.SiteListData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public class NetworkService {

    private final NetworkAPIs networkAPIs;

    public NetworkService(NetworkAPIs networkAPIs) {
        this.networkAPIs = networkAPIs;
    }

    public Subscription getSiteList(final GetSiteListCallback callback) {

        Observable<List<SiteListData>> observableSiteListResponse = networkAPIs.getSiteList();

        Subscription subscription = observableSiteListResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SiteListData>>() {
                    @Override
                    public void onCompleted() {
//                        Log.d("msg", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d("msg", "callback.onError");
//                        Log.d("msg", "error : "+e.toString());
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<SiteListData> listSiteListData) {
//                        Log.d("msg", "callback.onSuccess");
                        callback.onSuccess(listSiteListData);
                    }
                });

        return subscription;
    }

    public interface GetSiteListCallback{
        void onSuccess(List<SiteListData> listSiteListData);

        void onError(NetworkError networkError);
    }

    public Subscription getRdvListForManageMode(String siteLibelle, int maxNbOfData, int Offset ,final GetRdvListCallback callback) {

        Observable<List<RdvListData>> observableRdvListResponse = networkAPIs.getRdvListForManageMode(siteLibelle, maxNbOfData, Offset);

        Subscription subscription = observableRdvListResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RdvListData>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("msg", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("msg", "callback.onError");
                        Log.d("msg", "error : "+e.toString());
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<RdvListData> listRdvListData) {
                        Log.d("msg", "callback.onSuccess");
                        callback.onSuccess(listRdvListData);
                    }
                });

        return subscription;
    }

    public Subscription getRdvListForCandidatMode(int maxNbOfData, int Offset ,final GetRdvListCallback callback) {

        Observable<List<RdvListData>> observableRdvListResponse = networkAPIs.getRdvListForCandidatMode(maxNbOfData, Offset);

        Subscription subscription = observableRdvListResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RdvListData>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("msg", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("msg", "callback.onError");
                        Log.d("msg", "error : "+e.toString());
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<RdvListData> listRdvListData) {
                        Log.d("msg", "callback.onSuccess");
                        callback.onSuccess(listRdvListData);
                    }
                });

        return subscription;
    }

    public interface GetRdvListCallback{
        void onSuccess(List<RdvListData> listRdvListData);

        void onError(NetworkError networkError);
    }

    public Subscription managerValideRdvPriseEnCharge(int RDVId, final ValideRDVCallback callback) {

        Log.d("RDVId", "NetworkService : "+RDVId);

        Observable<List<IsValide>> observableRdvIdValideResponse = networkAPIs.managerValideRdvPriseEnCharge(RDVId);

        if(observableRdvIdValideResponse == null) {
            Log.d("msg", "observableRdvIdValideResponse == null");
        } else {
            Log.d("msg", "observableRdvIdValideResponse != null");
        }

        Subscription subscription = observableRdvIdValideResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IsValide>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("msg", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("msg", "callback.onError");
                        Log.d("msg", "error : "+e.toString());
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<IsValide> isValide) {
                        Log.d("msg", "callback.onSuccess");
                        callback.onSuccess(isValide);
                    }
                });

        return subscription;
    }

    public interface ValideRDVCallback{
        void onSuccess(List<IsValide> isValide);

        void onError(NetworkError networkError);
    }

    public Subscription candidatValideRdvPrevenirManager(int RDVId, final Relance1RDVCallback callback) {

        Observable<List<IsRelance>> observableRdvIdRelanceResponse = networkAPIs.candidatValideRdvPrevenirManager(RDVId);

        Subscription subscription = observableRdvIdRelanceResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<IsRelance>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("msg", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("msg", "callback.onError");
                        Log.d("msg", "error : "+e.toString());
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<IsRelance> isRelance) {
                        Log.d("msg", "callback.onSuccess");
                        callback.onSuccess(isRelance);
                    }
                });

        return subscription;
    }

    public interface Relance1RDVCallback{
        void onSuccess(List<IsRelance> isRelance);

        void onError(NetworkError networkError);
    }

}
