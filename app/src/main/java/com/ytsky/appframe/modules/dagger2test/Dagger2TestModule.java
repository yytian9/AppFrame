package com.ytsky.appframe.modules.dagger2test;

import dagger.Module;
import dagger.Provides;

/**
 * author:  yytian
 * time:    2016/11/30 23:56
 * des:
 */
@Module
public class Dagger2TestModule {
    private final Dagger2TestContract.View mView;
//    private final Dagger2TestContract.Model mModel;

    public Dagger2TestModule(Dagger2TestContract.View view) {
        mView = view;
//        mModel = model;
    }


    @Provides
    @TestScoped
    Dagger2TestContract.View provideContractView() {
        return mView;
    }
    @Provides
    @TestScoped
    Dagger2TestContract.Model provideContractModel() {
        return new Dagger2TestModel();
    }

}
