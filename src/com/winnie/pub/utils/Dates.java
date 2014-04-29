package com.winnie.pub.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-10-10
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class Dates {
    public static final String LONGDATEFORMAT = "yyyy-MM-dd";
    public static final String LONGDATETIMEFORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * 将日期将成 yyyy-MM-dd 格式
     * @param date
     * @return
     */
    public static String toLDate(Date date) {
        return format(date, LONGDATEFORMAT);
    }


    public static String toLDateTime(Date date) {
        return format(date, LONGDATETIMEFORMAT);
    }

    public static String format(Date date, String formatStr) {
        if (date == null) return "";

        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    public static Date get(int year, int month, int date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);

        return c.getTime();
    }

    public static Date get(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(LONGDATETIMEFORMAT);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

    }
}
