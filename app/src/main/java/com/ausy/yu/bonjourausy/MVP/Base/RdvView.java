package com.ausy.yu.bonjourausy.MVP.Base;

import com.ausy.yu.bonjourausy.models.RdvListData;
import com.ausy.yu.bonjourausy.models.SiteListData;

import java.util.List;

/**
 * Created by yukaiwen on 18/04/2017.
 */

public interface RdvView {

    void onFailure(String appErrorMessage);

    void getRdvListSuccess(List<RdvListData> listSiteListData);

    void valideRdvSuccess(int isValide);
}
