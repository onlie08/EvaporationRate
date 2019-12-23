package com.ch.utils;

import android.content.Context;

import com.ch.view.DateChooseController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getSystemDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * date类型进行格式化输出
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 将"2015-08-31 21:08:06"型字符串转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 将CST时间类型字符串进行格式化输出
     * @param str
     * @return
     * @throws ParseException
     */
    public static String CSTFormat(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date = (Date) formatter.parse(str);
        return dateFormat(date);
    }


    /**
     * 将long类型转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date LongToDare(long str) throws ParseException{
        return new Date(str * 1000);
    }

    public static String countTwoTime(long startTime,long endTime){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff=endTime-startTime;
        // 计算差多少天

        long day = diff / nd;

        // 计算差多少小时

        long hour = diff % nd / nh;

        // 计算差多少分钟

        long min = diff % nd % nh / nm;

        // 计算差多少秒//输出结果

        long sec = diff % nd % nh % nm / ns;

        String sDay;
        String sHour ;
        String sMin ;
        String sSec ;
        if(day>0){
            sDay = day+"天";
        }else {
            sDay = "";
        }
        if(hour<10){
            sHour = "0" + hour;
        }else {
            sHour = "" + hour;
        }
        if(min<10){
            sMin = "0" + min;
        }else {
            sMin = "" + min;
        }
        if(sec<10){
            sSec = "0" + sec;
        }else {
            sSec = "" + sec;
        }
        return  sDay+ sHour + ":" + sMin + ":" + sSec;
//        return day + "天" + hour + "小时" + min + "分钟";
//        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }
}
