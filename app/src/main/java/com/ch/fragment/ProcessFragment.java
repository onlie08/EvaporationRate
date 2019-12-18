package com.ch.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ch.db.DbManage;
import com.ch.service.DataService;
import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.DateUtil;
import com.ch.utils.RxTimerUtil;
import com.ch.utils.ToastHelper;
import com.ch.view.SpinnerController;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProcessFragment extends BaseProcessFragment{
    private DataService mDataService;//xxg
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Data service", "---service connected");

            mDataService = ((DataService.LocalBinder) iBinder).getService();

            mDataService.addOnDataCallback(new DataService.OnDataCallback() {
                @Override
                public void revRealTimeData(BeanRTData data) {
                    if(suspend){
                        return;
                    }
                    setBeanRTDataDate(data);
                    Log.d("Data service", "---activity get data:" + new Gson().toJson(data));
                }

                @Override
                public void revCalResult(Float testEvaR, Float staticEvaR) {
                    if(suspend){
                        return;
                    }
                    setEvaRDate(testEvaR,staticEvaR);
                    Log.d("Data service", "---calculate data:" + testEvaR + "--" + staticEvaR);
                }

                @Override
                public void revAcqTimeData(BeanRTData data) {
                    if(suspend){
                        return;
                    }
                    saveDateToDB(data);
                    Log.d("Data service", "---revAcqTimeData data:" + new Gson().toJson(data));
                }

                @Override
                public void experimentOver(BeanRTData data, Float testEvaR, Float staticEvaR) {
                    testControll(staticEvaR);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Data service", "---service disconnected");
        }
    };

    private void saveDateToDB(BeanRTData data) {
        data.setDeviceId(parameter.getDeviceId());
        DbManage.getInstance().saveBeanRTData(data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this.getActivity();
        Intent intent = new Intent(mCtx, DataService.class);
        ((Activity) mCtx).bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void startTest(){
        if (mDataService != null) {
            /*
             *      recPeroid   - 时间间隔范围(单位秒,30分钟)
             *      mediumtype  - 介质类型（1-LN2,2-LNG）
             *      expperiod   - 实验周期（单位秒 ，24小时）
             *      lng         - LNG 计算参数
             *      ln2         - LN2 计算参数
             *      validV      - 有效容积（来自试验参数）单位L
             */
            long recPeroid = 3 * 60l;
            int mediumtype = 1;
            long expperiod = 6 * 60l; //6分钟便于测试 ,应该一次实验室24小时
            BeanOperaParam ln2 = new BeanOperaParam(1.2555f, 808.61f, 1f, 1f);
            BeanOperaParam lng = new BeanOperaParam(0.676f, 422.53f, 1f, 1f);
            float validV = 40.5f;

            mDataService.startAcqData(recPeroid, mediumtype, expperiod, lng, ln2, validV);
        }

        Toast.makeText(mCtx, "开始第"+testProgress+"次试验", Toast.LENGTH_SHORT).show();
    }

    @Override
    void stopTest() {

    }

    @Override
    void endTest() {

    }

    @Override
    void testSuccess() {
        endTest();
        ToastHelper.showToast("试验成功完成");
        btnTestReport.setEnabled(true);
    }

    @Override
    public void chooseIntervalTimeDialog(){
        List<String> times = new ArrayList<>();
        for(int i = 1;i<61;i++){
            times.add(String.valueOf(i));
        }
        SpinnerController spinnerDevice = new SpinnerController(getActivity());
        spinnerDevice.showSpinnerDialog(times);
        spinnerDevice.setListener(new SpinnerController.SpinnerListener() {
            @Override
            public void selectResult(String date) {
                tvTimeInterval.setText(date);
            }
        });
    }

    @Override
    void countStaticTotalTime() {
        try {
            String startTime = tvStaticStartTime.getText().toString();
            Date date1 = DateUtil.StringToDate(startTime);
            String endTime = tvStaticEndTime.getText().toString();
            Date date2 = DateUtil.StringToDate(endTime);
            tvStaticTotalTime.setText(DateUtil.countTwoTime(date1.getTime(),date2.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    void startStaticTotalTime() {
        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                try {
                    String startTime = tvStaticStartTime.getText().toString();
                    Date date = DateUtil.StringToDate(startTime);
                    tvStaticTotalTime.setText(DateUtil.countTwoTime(date.getTime(),System.currentTimeMillis()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    void endStaticTotalTime() {
        RxTimerUtil.cancel();
    }

    @Override
    void startTestTotalTime() {
        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                try {
                    String startTime = tvTestStartTime.getText().toString();
                    Date date = DateUtil.StringToDate(startTime);
                    tvTestTotalTime.setText(DateUtil.countTwoTime(date.getTime(),System.currentTimeMillis()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    void endTestTotalTime() {
        RxTimerUtil.cancel();
    }

    private void testControll(Float staticEvaR){
        if(testProgress == 1){
            staticEvaR1 = staticEvaR;
            if(staticEvaR1 > 5){
                ToastHelper.showToast("试验不合格");
            }else {
                testProgress = 2;
                startTest();
            }
        }else if(testProgress == 2){
            float diff;
            staticEvaR2 = staticEvaR;
            if(staticEvaR2 > staticEvaR1){
                diff = (staticEvaR2 - staticEvaR1)/staticEvaR2;
            }else {
                diff = (staticEvaR1 - staticEvaR2)/staticEvaR1;
            }
            if(diff > 5){
                testProgress = 3;
                startTest();
            }else {
                testSuccess();
            }
        }else if(testProgress == 3){
            staticEvaR3 = staticEvaR;
            testSuccess();
        }
    }



}
