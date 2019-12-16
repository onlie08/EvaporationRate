package com.ch.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;
import com.ch.service.dataacq.DataCalculate;
import com.ch.service.dataacq.DataReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataService extends Service {

    private LocalBinder mBinder = new LocalBinder();
    private List<OnDataCallback> mlstHandler;
    private Timer mTimerRT;
    private DataReader mDataReader;

    private Long mExpperiod;//实验周期
    private Date mStartTime;//实验开始时间
    private Long mRecPeroid;//采集周期
    private Long mCount;//采集计数器

    private int mMediumtype;//介质类型
    private Float mValidV;//有效容积

    private BeanOperaParam mLNGParam;   //LNG运算参数
    private BeanOperaParam mLN2Param;   //LN2运算参数

    private static final long calculatePreiod = 60; //1分钟计算一次蒸发率
    private Long mCountCal;//计算计数器
    private List<BeanRTData> mlsthisData;  //存储历史数据，用于计算

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mlstHandler = new ArrayList<OnDataCallback>();
        mDataReader = new DataReader();
        mlsthisData    = new ArrayList<BeanRTData>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearResource();
    }

    public final class LocalBinder extends Binder{
        public DataService getService(){
            return DataService.this;
        }
    }

    //-----start 回调接口-----------
    public interface OnDataCallback{

        /*
         * 接收实时采集的数据
         */
        public void revRealTimeData(BeanRTData data);

        /**
         * 接收计算结果数据（默认1分钟计算一次）
         * testEvaR - 测试蒸发率  staticEvaR - 静态蒸发率
         */
        public void revCalResult(Float testEvaR,Float staticEvaR);

        /**
         * 接收采集周期的数据
         */
        public void revAcqTimeData(BeanRTData data);

        /**
         * 实验结束回调
         * testEvaR - 测试蒸发率  staticEvaR - 静态蒸发率
         */
        public void experimentOver(BeanRTData data,Float testEvaR,Float staticEvaR);

    }

    public void addOnDataCallback(OnDataCallback cb)
    {
        mlstHandler.add(cb);
    }
    //-----end 回调接口-----------

    /**
     * 开始采集数据（实验开始）
     * params:
     *      recPeroid   - 时间间隔范围(单位秒,30分钟)
     *      mediumtype  - 介质类型（1-LN2,2-LNG）
     *      expperiod   - 实验周期（单位秒 ，24小时）
     *      lng         - LNG 计算参数
     *      ln2         - LN2 计算参数
     *      validV      - 有效容积（来自试验参数）
     */
    public void startAcqData(long recPeroid, int mediumtype, long expperiod, BeanOperaParam lng,BeanOperaParam ln2,float validV)
    {
        mlsthisData.clear();

        mLNGParam = lng;
        mLN2Param = ln2;
        mValidV = validV*0.001f; //单位L转换为m3

        mMediumtype = mediumtype;
        mRecPeroid = recPeroid;
        mCount = 0l;
        mCountCal = 0l;
        mExpperiod = expperiod;
        mStartTime = new Date();


        mTimerRT = new Timer();

        mTimerRT.schedule(mTask,1000,1000);
    }

    /**
     * 停止采集数据（结束实验）
     */
    public void stopAcqData()
    {
        clearResource();
    }


    protected void clearResource()
    {
        if(mTimerRT!=null)
        {
            mTimerRT.cancel();
            mTimerRT = null;
        }
        mlsthisData.clear();
    }
    /**
     * 实时数据采集定时器
     */
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            //Log.e("data Service gen", "--timer run");
            BeanRTData testData = mDataReader.getData();

            Message msg = new Message();
            msg.obj = testData;
            handlerRTData.sendMessage(msg);
        }
    };
    private Handler handlerRTData = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //实时数据回调
            BeanRTData revData = (BeanRTData)msg.obj;
            //Log.e("data Service rev", "--receive data flow: "+revData.getAccFlow());
            for (int i=0;i<mlstHandler.size();i++)
            {
                mlstHandler.get(i).revRealTimeData(revData);
            }

            //每分钟进行计算并且回调
            mCountCal++;
            if(mCountCal>=calculatePreiod)
            {
                mlsthisData.add(revData); //记录数据

                Float[] calRes = DataCalculate.CalTestEvaRate(mMediumtype,mlsthisData,mLN2Param,mLNGParam,mValidV);
                for (int i=0;i<mlstHandler.size();i++)
                {
                    mlstHandler.get(i).revCalResult(calRes[0],calRes[1]);
                }
                mCountCal=0l;
            }

            //采集周期的回调用
            mCount++;
            if(mCount>=mRecPeroid)
            {
                for (int i=0;i<mlstHandler.size();i++)
                {
                    mlstHandler.get(i).revAcqTimeData(revData);
                }
                mCount=0l;
            }

            //实验结束回调
            Date curDate = new Date();
            long detTime = (curDate.getTime() - mStartTime.getTime())/1000;
            if(detTime>=mExpperiod)
            {
                mlsthisData.add(revData); //记录数据
                Float[] calRes = DataCalculate.CalTestEvaRate(mMediumtype,mlsthisData,mLN2Param,mLNGParam,mValidV);

                for (int i=0;i<mlstHandler.size();i++)
                {
                    mlstHandler.get(i).experimentOver(revData,calRes[0],calRes[1]);
                }
                clearResource();
            }

        }
    };


}
