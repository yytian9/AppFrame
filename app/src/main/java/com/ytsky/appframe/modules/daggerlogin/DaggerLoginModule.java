package com.ytsky.appframe.modules.daggerlogin;

import com.ytsky.appframe.http.retrofit.FungoReqeust;
import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;
import com.ytsky.appframe.http.schedulers.SchedulerProvider;
import com.ytsky.appframe.modules.dagger2test.TestScoped;
import com.ytsky.appframe.modules.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * author:  yytian
 * time:    2016/11/30
 * des:     This is a Dagger module. We use this to pass in the View dependency to the {@link LoginPresenter}.
 */
@Module
public class DaggerLoginModule {
    private final DaggerLoginContract.View mView;


    public DaggerLoginModule(DaggerLoginContract.View view) {
        mView = view;
    }
    @TestScoped
    @Provides
    DaggerLoginContract.View provideContractView() {
        return mView;
    }
    @TestScoped
    @Provides
    DaggerLoginContract.Model provideContractModel() {
        return new DaggerLoginModel(FungoReqeust.getInstance());
    }
    @TestScoped
    @Provides
    BaseSchedulerProvider provideScheduler() {
        return SchedulerProvider.getInstance();
    }
}
