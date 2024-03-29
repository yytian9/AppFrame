package com.ytsky.appframe.modules.login;

import android.support.v4.util.ArrayMap;

import com.ytsky.appframe.constant.ParamName;
import com.ytsky.appframe.constant.RequestPath;
import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.retrofit.FungoReqeust;
import com.ytsky.appframe.util.MapUtils;

import io.reactivex.Flowable;

/**
 * Author  Farsky
 * Date    2016/11/29 0029
 * Des
 */

public class LoginModel implements LoginContract.Model {
    private final FungoReqeust mRequest;

    public LoginModel(FungoReqeust request) {
        mRequest = request;
    }
    @Override
    public Flowable<LoginInfo> getLoginResult(String userName, String password) {
        ArrayMap<String, Object> dataParams = MapUtils.getDataParams(
                ParamName.USER, userName,
                ParamName.PASSWORD, password);
        return mRequest.getRequest(RequestPath.LOGIN,dataParams,LoginInfo.class,true);
    }

    @Override
    public void saveUserInfo(LoginInfo loginInfo) {

    }
}
