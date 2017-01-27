package com.ytsky.appframe.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.ytsky.appframe.constant.CommonData;
import com.ytsky.appframe.http.dependency.DaggerNetComponent;
import com.ytsky.appframe.http.dependency.NetComponent;
import com.ytsky.appframe.http.dependency.NetModule;

/**
 * Author  yytian
 * Date    2016/11/29 0029
 * Des
 */

public class BaseApplication extends Application {

    private NetComponent mNetComponent;
    private static Context baseApplication;
    private static int mMainThreadId;
    private static Thread mMainThread;
    private static Handler mMainThreadHandler;
    private static  Looper mMainLooper;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        baseApplication = getApplicationContext();

        mNetComponent= DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(CommonData.URL_ROOT))
                .build();

        ComponetHolder.setAppComponent(mNetComponent);

        //初始化云通信
        initNim();

    }

    private void initNim() {
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());
    }


    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {

        return null;
    }
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();
        return options;

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
    public static Context getApplication() {
        return baseApplication;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }
    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getAppMainLooper() {
        return mMainLooper;
    }
}
