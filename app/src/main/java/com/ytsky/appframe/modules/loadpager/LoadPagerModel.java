package com.ytsky.appframe.modules.loadpager;

import com.ytsky.appframe.http.retrofit.FungoReqeust;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 7:33
 * des:
 */

public class LoadPagerModel implements LoadPagerContract.Model {

    private final FungoReqeust mRequest;

    public LoadPagerModel(FungoReqeust request) {
        mRequest = request;
    }

    @Override
    public Flowable<Integer> getInitData() {
        return Flowable.just(1)
                .delay(3, TimeUnit.SECONDS);
    }
}
