package com.ytsky.appframe.util;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */

public abstract class GsonUtils {
    private static final String TAG = GsonUtils.class.getName();
    private static final Gson GSON = createGson(true);
    private static final Gson GSON_NO_NULLS = createGson(false);

    public GsonUtils() {
    }

    public static final Gson createGson() {
        return createGson(true);
    }

    public static final Gson createGson(boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new GsonUtils.DateFormatter());
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        //Using this naming policy with Gson will ensure that the field name is unchanged.
        //the default type(so,it can omit)
        builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        if(serializeNulls) {
            builder.serializeNulls();
        }

        return builder.create();
    }

    public static final Gson getGson() {
        return GSON;
    }

    public static final Gson getGson(boolean serializeNulls) {
        return serializeNulls?GSON:GSON_NO_NULLS;
    }

    public static final String toJson(Object object) {
        return toJson(object, true);
    }

    public static final String toJson(Object object, boolean includeNulls) {
        return includeNulls?GSON.toJson(object):GSON_NO_NULLS.toJson(object);
    }

    public static final <V> V fromJson(String json, Class<V> type) {
        return GSON.fromJson(json, type);
    }

    public static final <V> V fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static final <V> V fromJson(Reader reader, Class<V> type) {
        return GSON.fromJson(reader, type);
    }

    public static final <V> V fromJson(Reader reader, Type type) {
        return GSON.fromJson(reader, type);
    }

    public static class DateFormatter implements JsonDeserializer<Date>, JsonSerializer<Date> {
        private final DateFormat[] formats = new DateFormat[1];

        public DateFormatter() {
            this.formats[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone timeZone = TimeZone.getTimeZone("Zulu");
            DateFormat[] var2 = this.formats;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                DateFormat format = var2[var4];
                format.setTimeZone(timeZone);
            }

        }

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String value = json.getAsString();
            if(!StringUtils.isEmpty(value) && value.length() != 1) {
                DateFormat[] var5 = this.formats;
                int var6 = var5.length;
                int var7 = 0;

                while(var7 < var6) {
                    DateFormat format = var5[var7];

                    try {
                        synchronized(format) {
                            return format.parse(value);
                        }
                    } catch (ParseException var12) {
                        android.util.Log.e(GsonUtils.TAG, "日期转换错误， " + value, var12);
                        ++var7;
                    }
                }

                return new Date(0L);
            } else {
                return null;
            }
        }

        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            DateFormat primary = this.formats[0];
            String formatted;
            synchronized(primary) {
                formatted = primary.format(date);
            }

            return new JsonPrimitive(formatted);
        }
    }
}
