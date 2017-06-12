package com.ausy.yu.bonjourausy.MVP.Main;

import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.models.SiteListData;

import java.util.List;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public interface MainView {

    void onFailure(String appErrorMessage);

    void getSiteListSuccess(List<SiteListData> listSiteListData);
}
