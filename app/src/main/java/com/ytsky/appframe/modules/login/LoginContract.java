package com.ytsky.appframe.modules.login;

import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.base.BasePresenter;

import io.reactivex.Flowable;

/**
 * Author  Farsky
 * Date    2016/11/29 0029
 * Des
 */

public class LoginContract {
    interface View  {
        void showUserNameOrPasswordErrorToast();

        void showSuccessView();

        void showLoadingIndicator(boolean active);

        void showFailuredToast();
    }

    interface Presenter extends BasePresenter {

        void login(String userName, String password);
    }
    interface Model{
        Flowable<LoginInfo> getLoginResult(String userName, String password);

        void saveUserInfo(LoginInfo loginInfo);
    }
}
