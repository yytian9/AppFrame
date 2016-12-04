package com.ytsky.appframe.modules.dagger2test;

import dagger.Component;

/**
 * author:  yytian
 * time:    2016/11/30 23:57
 * des:
 */
@TestScoped
@Component(modules = {Dagger2TestModule.class})
public interface Dagger2TestComponent {
    void inject(DaggerTestActivity activity);
}
