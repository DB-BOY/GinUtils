package com.gin.library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by gin on 16/4/14.
 * 单例Toast
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(final Activity activity, String msg) {
        if (toast == null) {
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        }
        if ("main".equals(Thread.currentThread().getName())) {
            toast.show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });

        }
    }

    public static void showToast(final Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        if ("main".equals(Thread.currentThread().getName())) {
            toast.show();
        } else {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();//先移除
                    toast.show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }.start();

        }
    }

    /**
     * @param duritation
     *
     * @see Toast#LENGTH_SHORT
     */
    public void setDuritation(int duritation) {
        toast.setDuration(duritation);
    }

}
