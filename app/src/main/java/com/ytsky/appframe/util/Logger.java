package com.ytsky.appframe.util;

import android.util.Log;

import com.ytsky.appframe.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Author  yytian
 * Date    2016/11/30
 * Des
 */
public class Logger {
    public static boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = "loveshowtest";
    private static final int MIN_STACK_OFFSET = 3;
    private static final int METHOD_COUNT = 1;
    private static final int JSON_INDENT = 4;
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    private static synchronized void log(int logType, String content, boolean showHeader) {
        if (DEBUG) {
            if (showHeader) {
                logHeaderContent();
            }

            content = "| " + content;
            byte[] bytes = content.getBytes();
            int length = bytes.length;

            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                String msg = new String(bytes, i, count);

                /*int lastLineSeparator = msg.lastIndexOf(System.getProperty("line.separator"));
                if (count == CHUNK_SIZE && lastLineSeparator > 0) {
                    msg = msg.substring(0, lastLineSeparator);
                    i += lastLineSeparator + 1;
                } else {
                    i += CHUNK_SIZE;
                }*/

                switch (logType) {
                    case Log.ERROR:
                        Log.e(TAG, msg);
                        break;
                    case Log.INFO:
                        Log.i(TAG, msg);
                        break;
                    default:
                        Log.v(TAG, msg);
                }
            }
        }
    }

    private static void log(int logType, String content) {
        log(logType, content, true);
    }

    public static void i(String content) {
        log(Log.INFO, content);
    }

    public static void i(String... args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                stringBuilder.append(arg);
                stringBuilder.append(",");
            }
            i(stringBuilder.toString());
        } catch (Throwable throwable) {
            e(throwable);
        }
    }

    public static void i(int content) {
        i(String.valueOf(content));
    }

    public static void d(String tag, String content) {
        if(DEBUG){
            Log.d(tag,content);
        }
    }

    public static void e(String content) {
        log(Log.ERROR, content);
    }

    public static void e(Throwable throwable) {
        if (throwable != null) {
            e(throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    public static void json(String json) {
        if (DEBUG) {
            try {
                String message = json;
                if (json == null || json.isEmpty()) {
                    message = "";
                } else if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    message = jsonObject.toString(JSON_INDENT);
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    message = jsonArray.toString(JSON_INDENT);
                }
                i(message);
            } catch (Throwable throwable) {
                e(throwable.getCause().getMessage() + "\n" + json);
            }
        }
    }

    private static void logHeaderContent() {
        try {
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            int stackOffset = getStackOffset(trace);

            int stackEnd = stackOffset + METHOD_COUNT;
            if (stackEnd >= trace.length) {
                stackEnd = trace.length - 1;
            }

            //Log.i(TAG, "|------------------------------------------------------");
            Log.d(TAG, "|---Thread: " + Thread.currentThread().getName()+" ------------------------------------------------------");

            for (int i = stackEnd; i >= stackOffset; i--) {

                StringBuilder builder = new StringBuilder();
                builder.append("| ")
                        .append(getSimpleClassName(trace[i].getClassName()))
                        .append(".")
                        .append(trace[i].getMethodName())
                        .append(" ")
                        .append(" (")
                        .append(trace[i].getFileName())
                        .append(":")
                        .append(trace[i].getLineNumber())
                        .append(")");
                Log.d(TAG, builder.toString());
            }
        } catch (Throwable throwable) {
            e(throwable);
        }
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        int start = -1;

        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement element = trace[i];
            String name = element.getClassName();

            if (!name.equals(Logger.class.getName())) {
                start = i;
                break;
            }
        }
        return start;
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }
}
