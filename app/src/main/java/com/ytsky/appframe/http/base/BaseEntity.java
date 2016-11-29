package com.ytsky.appframe.http.base;

import com.google.gson.JsonElement;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class BaseEntity {
    /**
     * message : OK
     * status : 200
     * mDataBean : {"token":"edf4c72443814159fe316e73ebca55a3"}
     */

    public String message;
    public int status;
    public JsonElement data;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

}
