package com.ytsky.appframe.modules.login;

import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Author  Farsky
 * Date    2016/11/29 0029
 * Des
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final BaseSchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mSubscriptions;
    private final LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View view, BaseSchedulerProvider schedulerProvider,
                                  LoginContract.Model model) {
        mModel = model;
        mView = view;
        mSchedulerProvider = schedulerProvider;
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void login(String userName, String password) {

    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {

    }
}
