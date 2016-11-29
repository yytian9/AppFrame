package com.ytsky.appframe.modules.login;

import com.ytsky.appframe.http.base.BasePresenter;
import com.ytsky.appframe.entity.LoginInfo;

import io.reactivex.Observable;

/**
 * Author  Farsky
 * Date    2016/11/29 0029
 * Des
 */

public class LoginContract {
    interface View  {
    }

    interface Presenter extends BasePresenter {

        void login(String userName, String password);
    }
    interface Model{
        Observable<LoginInfo> getLoginResult(String userName, String password);

        void saveUserInfo(LoginInfo loginInfo);
    }
}
