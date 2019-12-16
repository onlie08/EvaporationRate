package com.ch.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateChooseController {
    private Context mContext;
    private DateChooseListener listener;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;       //时
    private int mMinute;     //分
    private int mSeconds;    //秒
    private Calendar calendar;  //日期类
    private String days;

    public void setListener(DateChooseListener listener) {
        this.listener = listener;
    }

    public interface DateChooseListener{
        void dateResult(String date);
    }

    public DateChooseController(Context mContext) {
        this.mContext = mContext;
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//获取当前年
        mMonth = calendar.get(Calendar.MONTH);//获取月份，加1是因为月份是从0开始计算的
        mDay = calendar.get(Calendar.DATE);//获取日
        mHour = calendar.get(Calendar.HOUR);//获取小时
        mMinute = calendar.get(Calendar.MINUTE);//获取分钟
        mSeconds = calendar.get(Calendar.SECOND);//获取秒钟
    }

    public void chooseSingleDateDialog(){
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear + 1;
                mDay = dayOfMonth;
                String days;
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("/").append("0").
                                append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("/").append("0").
                                append(mMonth + 1).append("/").append(mDay).append("").toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("/").
                                append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("/").
                                append(mMonth + 1).append("/").append(mDay).append("").toString();
                    }

                }
                listener.dateResult(days);
            }
        }, mYear, mMonth, mDay).show();
    }

    public void showChooseDateDialog(){
        new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
//            String days;
//            if (mMonth + 1 < 10) {
//                if (mDay < 10) {
//                    days = new StringBuffer().append(mYear).append("年").append("0").
//                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
//                } else {
//                    days = new StringBuffer().append(mYear).append("年").append("0").
//                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
//                }
//
//            } else {
//                if (mDay < 10) {
//                    days = new StringBuffer().append(mYear).append("年").
//                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
//                } else {
//                    days = new StringBuffer().append(mYear).append("年").
//                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
//                }
//
//            }
            showTimeChooseDialog();
//            listener.dateResult(days);
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            listener.dateResult(mYear+"/"+mMonth+"/"+mDay+"  "+mHour+":"+mMinute);
        }
    };

    private void showTimeChooseDialog(){
        new TimePickerDialog(mContext, onTimeSetListener,mHour,mMinute,true).show();//记得使用show才能显示！
    }

}
