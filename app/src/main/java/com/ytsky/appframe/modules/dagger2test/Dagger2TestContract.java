package com.ytsky.appframe.modules.dagger2test;

/**
 * author:  yytian
 * time:    2016/11/30 23:58
 * des:
 */
public class Dagger2TestContract {
    interface View  {
        void btnClickSuccessToast();
    }

    interface Presenter  {

        void btnClickTest();
    }
    interface Model{
        void printSuccessLog();
    }
}
