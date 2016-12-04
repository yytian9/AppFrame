package com.ytsky.appframe.http.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 2:45
 * des:
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.i("LoggingInterceptor", String.format("Sending request %s on %s%n%s", request.url(), chain
                .connection(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.i("LoggingInterceptor", String.format("Received response for %s in %.1fms%n%s", response.request
                ().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}
