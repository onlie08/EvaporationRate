package com.ch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xhu_ww on 2018/6/1.
 * description:
 */
public class DateUtil {

    public static String formatDateToMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public static String formatDateToMD1(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public static String formatDateToYMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public static String getSystemDate1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getSystemDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
