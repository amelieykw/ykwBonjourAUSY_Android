package com.ausy.yu.bonjourausy.daggerInjector;

import com.ausy.yu.bonjourausy.MVP.Candidate.CandidateRdvActivity;
import com.ausy.yu.bonjourausy.MVP.Main.MainActivity;
import com.ausy.yu.bonjourausy.MVP.Manager.ManagerRdvActivity;
import com.ausy.yu.bonjourausy.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yukaiwen on 17/04/2017.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface DaggerInjector {

    void inject(MainActivity mainActivity);

    void inject(ManagerRdvActivity managerRdvActivity);

    void inject(CandidateRdvActivity candidateRdvActivity);
}
