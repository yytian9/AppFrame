package com.ytsky.appframe.http.subscribe;


import io.reactivex.subscribers.ResourceSubscriber;

/**
 * author： yytian
 * time：   2016/8/31 16:41
 * des：    use for http response
 */
public abstract class HttpSubscribe<T> extends ResourceSubscriber<T> {
    @Override
    protected void onStart() {
        super.onStart();
        _onStart();
    }

    public void _onStart() {

    }

    @Override
    public void onComplete() {

    }


}
