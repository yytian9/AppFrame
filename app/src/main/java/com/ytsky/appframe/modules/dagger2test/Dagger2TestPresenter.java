package com.ytsky.appframe.modules.dagger2test;

import javax.inject.Inject;

/**
 * author:  yytian
 * time:    2016/11/30 23:56
 * des:
 */
public class Dagger2TestPresenter implements Dagger2TestContract.Presenter {

    private final Dagger2TestContract.View mView;
    private final Dagger2TestContract.Model mModel;

    @Inject
    public Dagger2TestPresenter(Dagger2TestContract.View view ,Dagger2TestContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void btnClickTest() {
        mView.btnClickSuccessToast();
        mModel.printSuccessLog();
    }
}
