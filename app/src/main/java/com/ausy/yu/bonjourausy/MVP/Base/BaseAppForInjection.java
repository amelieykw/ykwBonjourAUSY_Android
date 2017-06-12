package com.ausy.yu.bonjourausy.MVP.Base;

import android.app.Activity;
import android.os.Bundle;

import com.ausy.yu.bonjourausy.daggerInjector.DaggerDaggerInjector;
import com.ausy.yu.bonjourausy.daggerInjector.DaggerInjector;
import com.ausy.yu.bonjourausy.networking.NetworkModule;

import java.io.File;

/**
 * Created by yukaiwen on 17/04/2017.
 */

public class BaseAppForInjection extends Activity {

    protected DaggerInjector daggerInjector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        daggerInjector = DaggerDaggerInjector.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    public DaggerInjector getDaggerInjector() {
        return daggerInjector;
    }

}
