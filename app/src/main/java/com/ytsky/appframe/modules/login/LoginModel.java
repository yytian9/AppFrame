package com.ytsky.appframe.modules.login;

import com.ytsky.appframe.entity.LoginInfo;

import io.reactivex.Observable;

/**
 * Author  Farsky
 * Date    2016/11/29 0029
 * Des
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginInfo> getLoginResult(String userName, String password) {
        return null;
    }

    @Override
    public void saveUserInfo(LoginInfo loginInfo) {

    }
}
