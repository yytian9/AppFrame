package com.ytsky.appframe.modules.dagger2test;

import com.ytsky.appframe.util.Logger;

/**
 * author:  yytian
 * time:    2016/12/1 0001 上午 9:53
 * des:
 */

public class Dagger2TestModel implements Dagger2TestContract.Model {
    public Dagger2TestModel() {
    }

    @Override
    public void printSuccessLog() {
        System.out.println("************************************");
        Logger.e("yytian"+"调用成功");
    }
}
