package com.ytsky.appframe.base;

import com.ytsky.appframe.http.dependency.NetComponent;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 2:53
 * des:
 */

public class ComponetHolder {
    private static NetComponent sAppComponent;

    public static void setAppComponent(NetComponent appComponent) {
        sAppComponent = appComponent;
    }

    public static NetComponent getAppComponent() {
        return sAppComponent;
    }
}
