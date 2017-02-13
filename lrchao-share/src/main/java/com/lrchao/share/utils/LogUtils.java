package com.lrchao.share.utils;

import android.util.Log;

/**
 * Description: 日志类
 *
 * @author liuranchao
 * @date 16/7/6 下午2:32
 */
public final class LogUtils {

    private static final String TAG = "ShareSDK";

    private static boolean IS_OPEN = true;

    private LogUtils() {
    }


    //====================================
    // verbose
    //====================================


    //====================================
    // debug
    //====================================

    /**
     * 输出d
     */
    public static void d(String tag, String msg) {
        if (IS_OPEN) {
            Log.d(tag, msg);
        }
    }

    /**
     * 输出d
     */
    public static void d(String msg) {
        if (IS_OPEN) {
            Log.d(TAG, msg);
        }
    }

    //====================================
    // warn
    //====================================

    /**
     * 输出w
     */
    public static void w(String msg) {
        if (IS_OPEN) {
            Log.w(TAG, msg);
        }
    }

    //====================================
    // error
    //====================================

    /**
     * 输出e
     */
    public static void e(String msg) {
        if (IS_OPEN) {
            Log.e(TAG, msg);
        }
    }

    /**
     * 输出e
     */
    public static void e(String tag, String msg) {
        if (IS_OPEN) {
            Log.e(tag, msg);
        }
    }

    //=================WTF===================

    /**
     * 打印crash
     *
     * @param throwable Throwable
     */
    public static void wtf(Throwable throwable) {
        if (IS_OPEN) {
            Log.wtf(TAG, throwable);
        }
    }
}
