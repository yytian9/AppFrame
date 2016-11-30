package com.ytsky.appframe.util;

import android.support.v4.util.ArrayMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建者：     yytian
 * 创建时间：   2016/5/26 10:08
 * 描述：
 */
public class MapUtils {

    /**
     * baseurl/map参数的合成，没有进行base64的编码
     *
     * @param baseUrl   基准的url，进入后会判断是否有?,没有加上
     * @param paramsMap 存get参数的集合
     * @return
     */
    public static String getUrlWithoutEncode(String baseUrl, Map<String, String> paramsMap) {
        StringBuilder params = new StringBuilder();

        if (baseUrl.contains("?")) {
            baseUrl = baseUrl + "&";
        } else {
            baseUrl = baseUrl + "?";
        }

        Iterator uee = paramsMap.entrySet().iterator();

        while (uee.hasNext()) {
            Map.Entry entry = (Map.Entry) uee.next();
            params.append((String) entry.getKey());
            params.append('=');
            params.append((String) entry.getValue());
            params.append('&');
        }
        return baseUrl + params.toString();
    }

    /**
     * baseurl/map参数的合成，进行base64的编码
     */
    public static String getUrlWithEncode(String baseUrl, Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        if (baseUrl.contains("?")) {
            baseUrl = baseUrl + "&";
        } else {
            baseUrl = baseUrl + "?";
        }
        try {
            Iterator uee = params.entrySet().iterator();

            while (uee.hasNext()) {
                Map.Entry entry = (Map.Entry) uee.next();
                encodedParams.append(URLEncoder.encode(dealParamKey((String) entry.getKey()), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode((String) entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }

            return baseUrl + encodedParams.toString();
        } catch (UnsupportedEncodingException var5) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, var5);
        }
    }

    public static String dealParamKey(String key) {
        String content = key;
        String regex = "\\[[0-9]\\d*\\]$";
        System.out.println(key);
        Matcher matcher = Pattern.compile(regex).matcher(key);
        if (matcher.find()) {
            content = key.replace(matcher.group(), "[]");
        }

        return content;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        return params;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        return params;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2,
                                                         String key_3, Object value_3) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        params.put(key_3, value_3);
        return params;

    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2,
                                                         String key_3, Object value_3,
                                                         String key_4, Object value_4) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        params.put(key_3, value_3);
        params.put(key_4, value_4);
        return params;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2,
                                                         String key_3, Object value_3,
                                                         String key_4, Object value_4,
                                                         String key_5, Object value_5) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        params.put(key_3, value_3);
        params.put(key_4, value_4);
        params.put(key_5, value_5);
        return params;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2,
                                                         String key_3, Object value_3,
                                                         String key_4, Object value_4,
                                                         String key_5, Object value_5,
                                                         String key_6, Object value_6) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        params.put(key_3, value_3);
        params.put(key_4, value_4);
        params.put(key_5, value_5);
        params.put(key_6, value_6);
        return params;
    }

    public static ArrayMap<String, Object> getDataParams(String key_1, Object value_1,
                                                         String key_2, Object value_2,
                                                         String key_3, Object value_3,
                                                         String key_4, Object value_4,
                                                         String key_5, Object value_5,
                                                         String key_6, Object value_6,
                                                         String key_7, Object value_7) {
        ArrayMap<String, Object> params = new ArrayMap<>();
        params.put(key_1, value_1);
        params.put(key_2, value_2);
        params.put(key_3, value_3);
        params.put(key_4, value_4);
        params.put(key_5, value_5);
        params.put(key_6, value_6);
        params.put(key_7, value_7);
        return params;
    }
}
