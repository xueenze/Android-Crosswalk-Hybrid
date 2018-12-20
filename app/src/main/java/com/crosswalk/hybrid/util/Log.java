package com.crosswalk.hybrid.util;

/**
 * Created by enzexue on 2018/12/20.
 */
public class Log {

    /**
     * @param TAG
     * @param msg
     */
    public static void d(String TAG, String msg) {
        android.util.Log.d(TAG, msg);
    }

    /**
     * @param TAG
     * @param msg
     * @param tr
     */
    public static void d(String TAG, String msg, Throwable tr) {
        android.util.Log.d(TAG, msg, tr);
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void v(String TAG, String msg) {
        android.util.Log.v(TAG, msg);
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void i(String TAG, String msg) {
        android.util.Log.i(TAG, msg);
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void e(String TAG, String msg) {
        android.util.Log.e(TAG, msg);
    }

    /**
     * @param TAG
     * @param msg
     */
    public static void w(String TAG, String msg) {
        android.util.Log.w(TAG, msg);
    }
}
