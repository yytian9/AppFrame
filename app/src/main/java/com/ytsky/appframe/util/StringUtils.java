package com.ytsky.appframe.util;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class StringUtils {


    public static boolean isEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
