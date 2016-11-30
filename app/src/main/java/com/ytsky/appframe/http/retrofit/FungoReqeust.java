package com.ytsky.appframe.http.retrofit;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.JsonElement;
import com.ytsky.appframe.constant.MessageCode;
import com.ytsky.appframe.constant.ParamName;
import com.ytsky.appframe.constant.ParamValues;
import com.ytsky.appframe.http.api.SkyApi;
import com.ytsky.appframe.http.base.BaseEntity;
import com.ytsky.appframe.http.base.RequestError;
import com.ytsky.appframe.util.GsonUtils;
import com.ytsky.appframe.util.MapUtils;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class FungoReqeust {

    private static FungoReqeust INSTANCE = null;

    private FungoReqeust() {

    }

    public static FungoReqeust getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FungoReqeust();
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
    public <T> Flowable<T> getRequest(String sourceUrl, Map<String, Object> params, Class<T> clazz, boolean needRetry) {
        //1.加密
        Map<String, Object> encryptionParams = getEncryptionData(params);
        //3.2不用重试机制
        return getRequestByStr(sourceUrl, encryptionParams, clazz);
    }

    private <T> Flowable<T> getRequestByStr(final String sourceUrl, final Map<String, Object> dataParams, final Class<T> clazz) {
        return Flowable.just(sourceUrl)
                .flatMap(new Function<String, Publisher<BaseEntity>>() {
                    @Override
                    public Publisher<BaseEntity> apply(String s) throws Exception {
                        return FungoSevice.createService(SkyApi.class).request(sourceUrl, dataParams);
                    }
                })
                .flatMap(new Function<BaseEntity, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(final BaseEntity baseEntity) throws Exception {
                        if (baseEntity.status == 200) {
                            return Flowable.create(new FlowableOnSubscribe<T>() {
                                @Override
                                public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                                    try {
                                        JsonElement data = baseEntity.data;
                                        T bean = GsonUtils.fromJson(data.toString(), clazz);
                                        emitter.onNext(bean);
                                    } catch (Exception e) {
                                        //compare to server,the mDataBean's type of client is different
                                        emitter.onError(new RequestError(MessageCode.JSON_DATA_ERROR, baseEntity.toString()));
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else  {
                            return Flowable.error(new RequestError(baseEntity.status, baseEntity.message));
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, Publisher<? extends T>>() {
                    @Override
                    public Publisher<? extends T> apply(Throwable throwable) throws Exception {
                        //在这里做全局的错误处理
                        Log.i("Request Error", "--->  " + throwable.toString());
                        throwable.printStackTrace();

                        //transform this error to local error,since this way is easier to handle
                        if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException
                                || throwable instanceof TimeoutException || throwable instanceof HttpException) {
                            return Flowable.error(new RequestError(MessageCode.HTTP_SERIES_ERROR,"http_series_error"));
                        }
                        return Flowable.error(throwable);
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


