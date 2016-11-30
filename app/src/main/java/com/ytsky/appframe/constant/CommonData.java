package com.ytsky.appframe.constant;


import com.ytsky.appframe.BuildConfig;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class CommonData {
    //http://api.ncz.a8tiyu.com/
    //http://192.168.1.108:8080/
    //API地址
    public static String URL_ROOT = BuildConfig.DEBUG? "http://192.168.1.88:8080/" : "http://api.ncz.a8tiyu.com/";

    public final static String QQ_URL = "mqqwpa://im/chat?chat_type=wpa&uin=";

}
