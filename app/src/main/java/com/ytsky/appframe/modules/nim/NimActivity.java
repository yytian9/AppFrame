package com.ytsky.appframe.modules.nim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ytsky.appframe.R;
import com.ytsky.appframe.util.ToastUtils;

public class NimActivity extends AppCompatActivity {


    private static final String TAG = NimActivity.class.getSimpleName();
    private EditText mTvUserName;
    private TextView mTvLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nim);

        mTvUserName = (EditText) findViewById(R.id.tv_user_name);

        mTvLoginResult = (TextView) findViewById(R.id.tv_login_result);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNim(mTvUserName.getText().toString().trim());
                ToastUtils.openToast("开始登录",ToastUtils.TYPE_NORMAL);
                mTvLoginResult.setText("");
            }
        });
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.openToast("退出",ToastUtils.TYPE_NORMAL);
                NIMClient.getService(AuthService.class).logout();
            }
        });

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        Log.i(TAG, "User status changed to: " + status);
//                        ToastUtils.openToast("退出成功",ToastUtils.TYPE_SUCCESE);
                    }
                }, true);
    }



    private void initNim(String userName) {
        LoginInfo info = new LoginInfo(userName,"123456"); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        ToastUtils.openToast("登录成功"+loginInfo.getAccount(),ToastUtils.TYPE_SUCCESE);
                        Log.i(TAG, "登录成功---->account = " + loginInfo.getAccount());
                        mTvLoginResult.setText("登录成功---->account = " + loginInfo.getAccount());
                    }

                    @Override
                    public void onFailed(int i) {
                        ToastUtils.openToast("登录失败---->"+i,ToastUtils.TYPE_ERROR);
                        Log.i(TAG, "登录失败---->"+i);
                        mTvLoginResult.setText("登录失败---->"+i);

                    }

                    @Override
                    public void onException(Throwable throwable) {
                        ToastUtils.openToast("异常",ToastUtils.TYPE_ERROR);
                        Log.i(TAG, "异常");
                        mTvLoginResult.setText("异常"+throwable.toString());

                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
}
    }
