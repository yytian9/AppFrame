package com.ytsky.appframe.http.dependency;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ytsky.appframe.base.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author:  yytian
 * time:    2016/11/30 0030 下午 5:22
 * des:
 */
@Singleton
@Component(modules = {NetModule.class, ApplicationModule.class})
public interface NetComponent {
    void inject(Fragment fragment);
    void inject(Activity activity);
}
