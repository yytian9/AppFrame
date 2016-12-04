package com.ytsky.appframe.constant;

/**
 * Author  Farsky
 * Date    2016/11/16 0016
 * Des
 */

public class MessageCode {

    // these are local define MessageCode，use for http request error
    public static final int JSON_DATA_ERROR = 901;  //receive a jsonException when try to analysis a string to bean
    public static final int SERVER_DATA_ERROR = 902;  //serve's response mDataBean error :neither ret=1 or ret=2
    public static final int REQUEST_PARAMS_ERROR = 903;  //receive a Error when try to  get the request params : the json of mDataBean
    public static final int HTTP_SERIES_ERROR = 904;  //ConnectException ||SocketTimeoutException||TimeoutException|| HttpException
    public static final int LOCAL_DATA_ERROR = 905;  //local computing error
    public static final int LOADPAGE_EMPTY_DATA = 906;  //requets empty or null data,this code is normal state
}
