package com.ytsky.appframe.http.api;


import com.ytsky.appframe.http.base.BaseEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des     common request api,use for encapsulating http framework
 */
public interface SkyApi {
    @FormUrlEncoded
    @POST("/{sourceUrl}")
    Observable<BaseEntity> request(@Path("sourceUrl") String sourceUrl,
                                   @FieldMap Map<String, Object> params);
}
