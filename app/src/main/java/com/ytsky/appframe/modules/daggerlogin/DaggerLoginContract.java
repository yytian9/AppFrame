package com.ytsky.appframe.modules.daggerlogin;

import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.base.BasePresenter;

import io.reactivex.Flowable;

/**
 * author:  yytian
 * time:    2016/12/1 0001 上午 10:07
 * des:
 */

public interface DaggerLoginContract {
    interface View  {
        void showUserNameOrPasswordErrorToast();

        void showSuccessView();

        void showLoadingIndicator(boolean active);

        void showFailureToast();
    }

    interface Presenter extends BasePresenter {

        void login(String userName, String password);
    }
    interface Model{
        Flowable<LoginInfo> getLoginResult(String userName, String password);

        void saveUserInfo(LoginInfo loginInfo);
    }
}
