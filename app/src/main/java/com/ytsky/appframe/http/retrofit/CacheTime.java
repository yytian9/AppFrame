package com.ytsky.appframe.http.retrofit;

/**
 * author： yytian
 * time：   2016/11/14 15:37
 * des：    user for FungoReqeust params
 */
public class CacheTime {
    public static final int NOT_CACHE=0;
    public static final int DEFAULT=4*60*60;
    public static final int A_MINUTE=60;
    public static final int AN_HOURE=60*60;
    public static final int SHORT=30*60*60;//30分钟
    public static final int MID=8*60*60;//4个小时
    public static final int LONG=3*24*60*60;//3天
    public static final int A_DAY=24*60*60;
}
