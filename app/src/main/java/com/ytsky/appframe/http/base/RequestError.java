package com.ytsky.appframe.http.base;

/**
 * Author  Farsky
 * Date    2016/11/16 0016
 * Des     use in FungoRequest
 */

public class RequestError extends Throwable {
    private int mState;

    public RequestError(int state, String message) {
        super(message);
        mState = state;
    }

    public int getState() {
        return mState;
    }

}

