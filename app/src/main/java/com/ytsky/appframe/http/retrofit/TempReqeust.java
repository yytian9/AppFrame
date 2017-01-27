package com.ytsky.appframe.http.retrofit;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.JsonElement;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.ytsky.appframe.constant.MessageCode;
import com.ytsky.appframe.constant.ParamName;
import com.ytsky.appframe.constant.ParamValues;
import com.ytsky.appframe.http.api.TempApi;
import com.ytsky.appframe.http.base.BaseEntity;
import com.ytsky.appframe.http.base.RequestError;
import com.ytsky.appframe.util.GsonUtils;
import com.ytsky.appframe.util.MapUtils;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class TempReqeust {

    private static TempReqeust INSTANCE = null;

    private TempReqeust() {

    }

    public static TempReqeust getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TempReqeust();
        }
        return INSTANCE;
    }

    /**
     * @param sourceUrl       接口号
     * @param params    请求中data对应的参数，一个json格式数据
     * @param clazz     请求成功对象data里面的实体对象
     * @param <T>       请求成功对象data里面的实体对象，对过clazz确定
     * @return 返回成功后的数据
     */
    public <T> Observable<T> getRequest(String sourceUrl, Map<String, Object> params, Class<T> clazz, boolean needRetry) {
        //1.加密
        Map<String, Object> encryptionParams = getEncryptionData(params);
        //3.2不用重试机制
        return getRequestByStr(sourceUrl, encryptionParams, clazz);
    }

    private <T> Observable<T> getRequestByStr(final String sourceUrl, final Map<String, Object> dataParams, final Class<T> clazz) {

        return Observable.just(sourceUrl)
                .flatMap(new Function<String, ObservableSource<BaseEntity>>() {
                    @Override
                    public ObservableSource<BaseEntity> apply(String s) throws Exception {
                        return FungoSevice.createService(TempApi.class).request(sourceUrl, dataParams);
                    }
               })
                .flatMap(new Function<BaseEntity, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseEntity baseEntity) throws Exception {
                        if (baseEntity.status == 200) {
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                                    try {
                                        JsonElement data = baseEntity.data;
                                        T bean = GsonUtils.fromJson(data.toString(), clazz);
                                        subscriber.onNext(bean);
                                    } catch (Exception e) {
                                        //compare to server,the mDataBean's type of client is different
                                        subscriber.onError(new RequestError(MessageCode.JSON_DATA_ERROR, baseEntity.toString()));
                                    }
                                }
                            });
                        } else  {
                            return Observable.error(new RequestError(baseEntity.status, baseEntity.message));
                        }
                    }

                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(Throwable e) throws Exception {
                        //在这里做全局的错误处理
                        Log.i("Request Error", "--->  " + e.toString());
                        e.printStackTrace();

                        //transform this error to local error,since this way is easier to handle
                        if (e instanceof ConnectException || e instanceof SocketTimeoutException
                                || e instanceof TimeoutException || e instanceof HttpException) {
                            return Observable.error(new RequestError(MessageCode.HTTP_SERIES_ERROR,"http_series_error"));
                        }
                        return Observable.error(e);
                    }
                });

    }


    /**
     * this's ready for encryption
     */
    private Map<String, Object> getEncryptionData(Map<String, Object> params) {
        ArrayMap<String, Object> clientInfoParams = MapUtils.getDataParams(
                ParamName.PLATFORM, ParamValues.ANDROID,
                ParamName.CHANNEL, ParamValues.CHANNEL,
                ParamName.VERSION, ParamValues.VERSION);
        JSONObject jsonObject = new JSONObject(clientInfoParams);
        String clientInfo = jsonObject.toString();

        params.put(ParamName.CLIENT_INFO,clientInfo);
        return params;
    }


}


