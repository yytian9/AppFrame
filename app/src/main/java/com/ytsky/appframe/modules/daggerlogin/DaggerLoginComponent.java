package com.ytsky.appframe.modules.daggerlogin;

import com.ytsky.appframe.modules.dagger2test.TestScoped;

import dagger.Component;

/**
 * author:  yytian
 * time:    2016/11/30
 * des:
 */
@TestScoped
@Component(modules = {DaggerLoginModule.class})
public interface DaggerLoginComponent {
    void inject(DaggerLoginActivity activity);
}
