package com.ytsky.appframe.http.retrofit;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ytsky.appframe.constant.CommonData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author  yytian
 * Date    2016/11/14 0014
 * Des
 */
public class FungoSevice {

    private static class LoggingInterceptor implements Interceptor {
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

    private static OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();

    private static Gson mGson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create();

    private static Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(CommonData.URL_ROOT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build();

    private FungoSevice() {
    }

    public static <T> T createService(Class<T> mClass) {
        return mRetrofit.create(mClass);
    }
}
