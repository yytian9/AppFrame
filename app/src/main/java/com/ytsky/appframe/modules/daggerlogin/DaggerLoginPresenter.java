package com.ytsky.appframe.modules.daggerlogin;

import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;
import com.ytsky.appframe.http.subscribe.HttpSubscribe;
import com.ytsky.appframe.util.StringUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * author:  yytian
 * time:    2016/12/1 0001 上午 10:09
 * des:
 */

public class DaggerLoginPresenter implements DaggerLoginContract.Presenter {
    private final DaggerLoginContract.View mView;
    private final BaseSchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mSubscriptions;
    private final DaggerLoginContract.Model mModel;

    @Inject
    public DaggerLoginPresenter(DaggerLoginContract.View view, BaseSchedulerProvider schedulerProvider,
                          DaggerLoginContract.Model model) {
        mModel = model;
        mView = view;
        mSchedulerProvider = schedulerProvider;
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void login(String userName, String password) {
        mSubscriptions.clear();
        if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
            mView.showUserNameOrPasswordErrorToast();
            return;
        }

        ResourceSubscriber<LoginInfo> resourceSubscriber = mModel.getLoginResult(userName, password)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(new HttpSubscribe<LoginInfo>() {
                    @Override
                    public void _onStart() {
                        mView.showLoadingIndicator(true);
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        mView.showLoadingIndicator(false);
                        mView.showSuccessView();
                        mModel.saveUserInfo(loginInfo);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showLoadingIndicator(false);
                        mView.showFailureToast();
                    }
                });

        mSubscriptions.add(resourceSubscriber);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.dispose();
    }
}
