package com.ytsky.appframe.base;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author:  yytian
 * time:    2016/11/30 0030 下午 3:50
 * des:
 */
@Module
public final class ApplicationModule {
    private final Application mApplication;

    ApplicationModule(Application application) {
        mApplication = application;
    }
    @Singleton
    @Provides
    Application provideContext() {
        return mApplication;
    }
}
