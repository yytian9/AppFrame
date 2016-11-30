package com.ytsky.appframe.base;

import android.app.Application;

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

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent= DaggerNetComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(CommonData.URL_ROOT))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
