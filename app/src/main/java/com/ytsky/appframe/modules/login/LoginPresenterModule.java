package com.ytsky.appframe.modules.login;

import dagger.Module;
import dagger.Provides;

/**
 * author:  yytian
 * time:    2016/11/30
 * des:     This is a Dagger module. We use this to pass in the View dependency to the {@link LoginPresenter}.
 */
@Module
public class LoginPresenterModule {
    private final LoginContract.View mView;


    public LoginPresenterModule(LoginContract.View view) {
        mView = view;
    }

    @Provides
    LoginContract.View provideAddEditTaskContractView() {
        return mView;
    }
}
