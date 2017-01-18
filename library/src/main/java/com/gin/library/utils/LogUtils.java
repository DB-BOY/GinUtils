package com.gin.library.utils;

import com.gin.library.BuildConfig;

/**
 * 根据build.pradle 中debuggable 决定是否打印日志
 * Created by wang.lichen on 2017/1/18.
 * <pre>
 *
 * buildTypes {
 *     release {
 *         debuggable false
 *          minifyEnabled false
 *          proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
 *          }
 *     debug {
 *          debuggable true
 *          minifyEnabled false
 *          }
 *      }
 * </pre>
 */
public class LogUtils {

    private static boolean DEBUGABLE = BuildConfig.DEBUG;
    public static boolean allowD = DEBUGABLE;
    public static boolean allowE = DEBUGABLE;
    public static boolean allowI = DEBUGABLE;
    public static boolean allowV = DEBUGABLE;
    public static boolean allowW = DEBUGABLE;
    private static String tag = "tag: ";

    public static void i(String msg) {
        if (allowI)
            android.util.Log.i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (allowI)
            android.util.Log.i(tag, msg);
    }

    public static void d(String msg) {
        if (allowD)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (allowD)
            android.util.Log.d(tag, msg);
    }

    public static void w(String msg) {
        if (allowW)
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (allowW)
            android.util.Log.w(tag, msg);
    }

    public static void v(String msg) {
        if (allowV)
            android.util.Log.v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (allowV)
            android.util.Log.v(tag, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }
}
