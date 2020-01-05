package com.ch.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ch.bean.TestProcess;
import com.ch.db.DbManage;
import com.ch.service.DataService;
import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.AppPreferences;
import com.ch.utils.DateUtil;
import com.ch.utils.RxStaticTotalTime;
import com.ch.utils.RxTestTotalTime;
import com.ch.utils.ToastHelper;
import com.ch.view.CommonDialog;
import com.ch.view.SpinnerController;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProcessFragment extends BaseProcessFragment{
    private BeanRTData finalResult;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Data service", "---service connected");

            float methaneDensity = (float)AppPreferences.instance().get("methaneDensity",0.676f);
            float lngDensity = (float)AppPreferences.instance().get("lngDensity",422.53f);
            float lngHeatConstant = (float)AppPreferences.instance().get("lngHeatConstant",1f);
            float lngCorrectConstant = (float)AppPreferences.instance().get("lngCorrectConstant",1f);

            float n2Density = (float)AppPreferences.instance().get("n2Density",1.2555f);
            float ln2Density = (float)AppPreferences.instance().get("ln2Density",808.61f);
            float ln2HeatConstant = (float)AppPreferences.instance().get("ln2HeatConstant",1f);
            float ln2CorrectConstant = (float)AppPreferences.instance().get("ln2CorrectConstant",1f);

            int mediumtype = 1;
            if("LN2".equals(parameter.getMediumType())){
                mediumtype = 1;
            }else if("LNG".equals(parameter.getMediumType())){
                mediumtype = 2;
            }
            BeanOperaParam ln2 = new BeanOperaParam(n2Density, ln2Density, ln2HeatConstant, ln2CorrectConstant);
            BeanOperaParam lng = new BeanOperaParam(methaneDensity, lngDensity, lngHeatConstant, lngCorrectConstant);

            mDataService = ((DataService.LocalBinder) iBinder).getService();
            mDataService.startAcqData(mediumtype,lng,ln2);

            mDataService.addOnDataCallback(new DataService.OnDataCallback() {
                @Override
                public void revRealTimeData(BeanRTData data) {
                    setBeanRTDataDate(data);

//                    if(testProgress>0){
                        EventBus.getDefault().postSticky(data);
//                    }
                    Log.d("Data service", "---activity get data:" + new Gson().toJson(data));
                }

                @Override
                public void revCalResult(BeanRTData data,Float testEvaR, Float staticEvaR) {
                    setEvaRDate(staticEvaR);
                    Log.d("Data service", "---calculate data:" + testEvaR + "--" + staticEvaR);
                }

                @Override
                public void revAcqTimeData(BeanRTData data) {
//                    EventBus.getDefault().postSticky(data);
                    saveDateToDB(data);
                    Log.d("Data service", "---revAcqTimeData data:" + new Gson().toJson(data));
                }

                @Override
                public void experimentOver(BeanRTData data, Float testEvaR, Float staticEvaR) {
                    Log.d("Data service", "---experimentOver data:" + new Gson().toJson(data) + "testEvaR:"+testEvaR+" staticEvaR:"+staticEvaR);
                    finalResult = data;
                    testControll(staticEvaR);
                }

                @Override
                public void getErrors(int value) {

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

    private void saveTestProcessToDB(){
        if(null == parameter){
            return;
        }
        TestProcess testProcess = new TestProcess();
        testProcess.setDeviceId(parameter.getDeviceId());
        testProcess.setStaticStartTime(tvStaticStartTime.getText().toString());
        testProcess.setStaticEndTime(tvStaticEndTime.getText().toString());
        testProcess.setStaticTotalTime(tvStaticTotalTime.getText().toString());
        testProcess.setTestStartTime(tvTestStartTime.getText().toString());
        testProcess.setTestEndTime(tvTestEndTime.getText().toString());
        testProcess.setTestTotalTime(tvTestTotalTime.getText().toString());
        testProcess.setEvaporationRateOne(tvTestOneCount.getText().toString());
        testProcess.setEvaporationRateTwo(tvTestTwoCount.getText().toString());
        testProcess.setEvaporationRateThire(tvTestThireCount.getText().toString());
        testProcess.setAcquisitionError(tvCollectError.getText().toString());

        if(!TextUtils.isEmpty(tvTestOneCount.getText().toString())){
            testProcess.setEvaporationRateFinal(tvTestOneCount.getText().toString());
        }
        if(!TextUtils.isEmpty(tvTestTwoCount.getText().toString())){
            testProcess.setEvaporationRateFinal(tvTestTwoCount.getText().toString());
        }
        if(!TextUtils.isEmpty(tvTestThireCount.getText().toString())){
            testProcess.setEvaporationRateFinal(tvTestThireCount.getText().toString());
        }
        testProcess.setTestProcess(testProgress);
        if(null != finalResult){
            testProcess.setSurroundhumidity(finalResult.getSurroundhumidity());
            testProcess.setSurroundpressure(finalResult.getSurroundpressure());
            testProcess.setSurroundtemperature(finalResult.getSurroundtemperature());
        }

        if(testState == 1){
            testProcess.setIsPass(true);
        }else if(testState == 2){
            testProcess.setIsPass(false);
        }
        DbManage.getInstance().saveTestProcess(testProcess);
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
            float methaneDensity = (float)AppPreferences.instance().get("methaneDensity",0.676f);
            float lngDensity = (float)AppPreferences.instance().get("lngDensity",422.53f);
            float lngHeatConstant = (float)AppPreferences.instance().get("lngHeatConstant",1f);
            float lngCorrectConstant = (float)AppPreferences.instance().get("lngCorrectConstant",1f);

            float n2Density = (float)AppPreferences.instance().get("n2Density",1.2555f);
            float ln2Density = (float)AppPreferences.instance().get("ln2Density",808.61f);
            float ln2HeatConstant = (float)AppPreferences.instance().get("ln2HeatConstant",1f);
            float ln2CorrectConstant = (float)AppPreferences.instance().get("ln2CorrectConstant",1f);

            long recPeroid = mTimeInterval * 60l;
            int mediumtype = 1;
            if("LN2".equals(parameter.getMediumType())){
                mediumtype = 1;
            }else if("LNG".equals(parameter.getMediumType())){
                mediumtype = 2;
            }
            long expperiod = 2 * 60l; //6分钟便于测试 ,应该一次实验室24小时
            BeanOperaParam ln2 = new BeanOperaParam(n2Density, ln2Density, ln2HeatConstant, ln2CorrectConstant);
            BeanOperaParam lng = new BeanOperaParam(methaneDensity, lngDensity, lngHeatConstant, lngCorrectConstant);
            float validV = Float.parseFloat(parameter.getEffectiveVolume())*1.0f;
            mDataService.startTest(recPeroid, mediumtype, expperiod, lng, ln2, validV,testProgress);
        }

        Toast.makeText(mCtx, "开始第"+testProgress+"次试验", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    void stopTest() {
        mDataService.stopTest();
        EventBus.getDefault().postSticky(true);
    }

    @Override
    void resumeTest() {
        mDataService.resumeAcqData();
    }

    @Override
    void pauseTest() {
        mDataService.pauseAcqData();
    }

    @Override
    void testSuccess() {
        stopTest();
//        ToastHelper.showToast("试验成功完成");
        btnTestReport.setEnabled(true);
        btnTestStart.setEnabled(true);
        btnTestStart.setText("开始试验");
        btnStaticStart.setText("开始静置");
        btnTestEnd.setEnabled(true);
        btnStaticStart.setEnabled(true);
        btnStaticEnd.setEnabled(true);
        tvTestEndTime.setText(DateUtil.getSystemDate1());
        RxTestTotalTime.cancel();
        testProgress = 0;
        saveTestProcessToDB();
    }

    void testFail() {
//        saveTestProcessToDB();
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
                mTimeInterval = Integer.parseInt(date);
                AppPreferences.instance().put("timeInterval",date);
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
        RxStaticTotalTime.interval(1000, new RxStaticTotalTime.IRxNext() {
            @Override
            public void doNext(long number) {
                if(!staticPause){
                    staticTotal++;
                    tvStaticTotalTime.setText(DateUtil.countTime(1000l * staticTotal));
                }
            }
        });

    }

    @Override
    void endStaticTotalTime() {
        staticTotal = 0;
        RxStaticTotalTime.cancel();
    }

    @Override
    void startTestTotalTime() {
        RxTestTotalTime.interval(1000, new RxTestTotalTime.IRxNext() {
            @Override
            public void doNext(long number) {
                if(!suspend){
                    testTotal++;
                    tvTestTotalTime.setText(DateUtil.countTime(1000l * testTotal));
                }
            }
        });
    }

    @Override
    void endTestTotalTime() {
        testTotal = 0;
        RxTestTotalTime.cancel();
    }

    private void testControll(Float staticEvaR){
        double qualificate = Double.parseDouble(parameter.getQualificationRate());

        if(testProgress == 1){
            staticEvaR1 = staticEvaR;
            tvTestOneCount.setText(""+staticEvaR);
            tvTestOneResult.setVisibility(View.VISIBLE);
            if(staticEvaR1 > qualificate){
//                initAllState();
                testState = 2;
                ToastHelper.showToast("第一次试验不合格");
                final CommonDialog commonDialog = new CommonDialog(getActivity());
                commonDialog.setTitle("提示")
                            .setMessage("试验不合格！请检查设备后重新开始试验").setSingle(true)
                            .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                                @Override
                                public void onPositiveClick() {
//                                    initAllState();
                                    testFail();
                                    commonDialog.dismiss();
                                }

                                @Override
                                public void onNegtiveClick() {
                                    commonDialog.dismiss();
                                }
                            }).show();

                tvTestOneResult.setText("不合格");
                tvTestOneResult.setSelected(true);
                endTestTotalTime();
                testSuccess();
            }else {
                testProgress = 2;
                startTest();
                tvTestOneResult.setText("合格");
                tvTestOneResult.setSelected(false);
            }

        }else if(testProgress == 2){
            float diff;
            staticEvaR2 = staticEvaR;
            tvTestTwoCount.setText(""+staticEvaR);
            if(staticEvaR2 > staticEvaR1){
                diff = (staticEvaR2 - staticEvaR1)/staticEvaR2 *100;
            }else {
                diff = (staticEvaR1 - staticEvaR2)/staticEvaR1*100;
            }
            tvCollectError.setText(diff+"");
            tvTestTwoResult.setVisibility(View.VISIBLE);
            tvCollectResult.setVisibility(View.VISIBLE);
            Log.d("Data service", "---service testControll"+diff);
            if(staticEvaR2 > qualificate){
                tvTestTwoResult.setText("不合格");
                tvTestTwoResult.setSelected(true);
            }else {
                tvTestTwoResult.setText("合格");
                tvTestTwoResult.setSelected(false);
            }
            if(diff > 5){
                testProgress = 3;
                startTest();
                ToastHelper.showToast("第二次试验不合格");

                tvCollectResult.setText("不合格");
                tvCollectResult.setSelected(true);

            }else {
                testState = 1;

                tvCollectResult.setText("合格");
                tvCollectResult.setSelected(false);
                testSuccess();
            }
        }else if(testProgress == 3){
            staticEvaR3 = staticEvaR;
            tvTestThireCount.setText(""+staticEvaR);
            tvTestThireResult.setVisibility(View.VISIBLE);
            if(staticEvaR3 > qualificate){
                testState = 2;
                testProgress = 2;
                ToastHelper.showToast("第三次试验不合格");
                tvTestThireResult.setText("不合格");
                tvTestThireResult.setSelected(true);
            }else {
                testState = 1;
                tvTestThireResult.setText("合格");
                tvTestThireResult.setSelected(false);
            }
            testSuccess();
        }
    }



}
