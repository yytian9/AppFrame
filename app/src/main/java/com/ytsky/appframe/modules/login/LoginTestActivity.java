package com.ytsky.appframe.modules.login;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ytsky.appframe.R;
import com.ytsky.appframe.constant.ParamName;
import com.ytsky.appframe.constant.RequestPath;
import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.retrofit.TempReqeust;
import com.ytsky.appframe.http.schedulers.SchedulerProvider;
import com.ytsky.appframe.util.MapUtils;
import com.ytsky.appframe.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
    }

    public void onClickTest(View view) {
        TempReqeust tempReqeust = TempReqeust.getInstance();

        ArrayMap<String, Object> dataParams = MapUtils.getDataParams(
                ParamName.USER, "mountain",
                ParamName.PASSWORD, "123456");
        tempReqeust.getRequest(RequestPath.LOGIN,dataParams,LoginInfo.class,true)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginInfo>() {
                    @Override
                    public void accept(LoginInfo loginInfo) throws Exception {
                        ToastUtils.openToast("登录成功",ToastUtils.TYPE_SUCCESE);
                    }
                });
    }
}
