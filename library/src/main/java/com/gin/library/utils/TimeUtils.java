package com.gin.library.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by wang.lichen on 2017/1/18.
 */
public class TimeUtils {
    /**
     * 获取当前时间
     *
     * @param format
     *         "yyyy-MM-dd HH:mm:ss"
     *
     * @return 当前时间
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 获取当前时间为本月的第几周
     *
     * @return int
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    /**
     * 获取当前时间为本周的第几天
     *
     * @return int
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    /**
     * 获取当前时间的年份
     *
     * @return 年
     */
    public static int getYear() {
        return GregorianCalendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     *
     * @return 月
     */
    public static int getMonth() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前时间是哪天
     *
     * @return 日
     */
    public static int getDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 时间比较大小
     *
     * @param d1
     *         date1
     * @param d2
     *         date2
     * @param format
     *         "yyyy-MM-dd HH:mm:ss"
     *
     * @return 1:d1>d2；
     * -1:d1<d2
     */
    public static int compareDate(String d1, String d2, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(d1);
            Date dt2 = df.parse(d2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 毫秒格式化
     *
     * @param ms
     *         如"1449466616602"
     * @param format
     *         如"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    @Deprecated
    public static String msFormat(Object ms, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(ms);
    }

    /**
     * 时间戳转北京时间
     *
     * @param millisecond
     *         如"1449455517602"
     * @param format
     *         如"yyyy-MM-dd HH:mm:ss"
     *
     * @return 格
     */
    public static String utc2BjTime(Object millisecond, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(millisecond);
    }

    /**
     * 北京时间转时间戳
     * 注意第一个参数和第二个参数格式必须一样
     *
     * @param bjTime
     *         如"2017-1-18 20:35:9"
     * @param format
     *         如"yyyy-MM-dd HH:mm:ss"
     *
     * @return 时间戳
     */
    public static long bjTime2UTC(String bjTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        long utc = 0;
        try {
            Date date = sdf.parse(bjTime);
            utc = date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utc;
    }
}