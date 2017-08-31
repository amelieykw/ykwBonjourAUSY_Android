package com.ausy.yu.bonjourausy.networking;

import com.ausy.yu.bonjourausy.models.IsRelance;
import com.ausy.yu.bonjourausy.models.IsValide;
import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.models.SiteListData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public interface NetworkAPIs {

    /****** For MainActivity  ******/
    @GET("/fetch_data/all_sites.php")
    Observable<List<SiteListData>> getSiteList();

    /****** For Manage Mode ******/
    @GET("/fetch_data/rdv_info_of_one_site_for_manager.php")
    Observable<List<RdvListData>> getRdvListForManageMode(@Query("sitelibelle") String sitelibelle, @Query("maxNbOfData") int maxNbOfData, @Query("offset") int offset);

    @GET("/insert_data/valide_one_rdv_by_manager.php")
    Observable<List<IsValide>> managerValideRdvPriseEnCharge(@Query("RDVId") int RDVId);

    /****** For Candidat Mode ******/
    @GET("/fetch_data/rdv_info_for_candidat.php")
    Observable<List<RdvListData>> getRdvListForCandidatMode(@Query("maxNbOfData") int maxNbOfData, @Query("offset") int offset);

    @GET("/insert_data/valide_one_rdv_by_candidat.php")
    Observable<List<IsRelance>> candidatValideRdvPrevenirManager(@Query("RDVId") int RDVId);
}
