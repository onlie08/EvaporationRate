package com.ch.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ch.activity.ReportActivity;
import com.ch.bean.Parameter;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.DataService;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.AppPreferences;
import com.ch.utils.DateUtil;
import com.ch.utils.ToastHelper;
import com.ch.view.CommonDialog;
import com.ch.view.DateChooseController;
import com.ch.view.SpinnerController;
import com.cunoraz.gifview.library.GifView;
import com.deadline.statebutton.StateButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseProcessFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tv_test_medium)
    TextView tvTestMedium;
    @BindView(R.id.tv_flow_rate)
    TextView tvFlowRate;
    @BindView(R.id.tv_flow_total)
    TextView tvFlowTotal;
    @BindView(R.id.tv_weight_rate)
    TextView tvWeightRate;
    @BindView(R.id.tv_weight_total)
    TextView tvWeightTotal;
    @BindView(R.id.tv_time_interval)
    TextView tvTimeInterval;
    @BindView(R.id.tv_test_one_count)
    TextView tvTestOneCount;
    @BindView(R.id.tv_test_two_count)
    TextView tvTestTwoCount;
    @BindView(R.id.tv_collect_error)
    TextView tvCollectError;
    @BindView(R.id.tv_test_thire_count)
    TextView tvTestThireCount;
    @BindView(R.id.tv_test_one_result)
    TextView tvTestOneResult;
    @BindView(R.id.tv_test_two_result)
    TextView tvTestTwoResult;
    @BindView(R.id.tv_test_thire_result)
    TextView tvTestThireResult;
    @BindView(R.id.tv_collect_result)
    TextView tvCollectResult;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_prejudge)
    TextView tvPrejudge;
    @BindView(R.id.tv_pre_result)
    TextView tvPreResult;
    @BindView(R.id.tv_static_start_time)
    TextView tvStaticStartTime;
    @BindView(R.id.tv_static_end_time)
    TextView tvStaticEndTime;
    @BindView(R.id.tv_static_total_time)
    TextView tvStaticTotalTime;
    @BindView(R.id.tv_test_start_time)
    TextView tvTestStartTime;
    @BindView(R.id.tv_test_end_time)
    TextView tvTestEndTime;
    @BindView(R.id.tv_test_total_time)
    TextView tvTestTotalTime;
    @BindView(R.id.btn_static_end)
    StateButton btnStaticEnd;
    @BindView(R.id.btn_test_report)
    StateButton btnTestReport;
    @BindView(R.id.btn_test_start)
    StateButton btnTestStart;
    @BindView(R.id.btn_test_end)
    StateButton btnTestEnd;
    @BindView(R.id.btn_static_start)
    StateButton btnStaticStart;
    @BindView(R.id.btn_auto_heat)
    StateButton btnAutoHeat;
    @BindView(R.id.btn_unauto_heat)
    StateButton btnUnautoHeat;
    @BindView(R.id.tv_entrance_temperature)
    TextView tvEntranceTemperature;
    @BindView(R.id.tv_entrance_pressure)
    TextView tvEntrancePressure;
    @BindView(R.id.tv_flow_counter)
    TextView tvFlowCounter;
    @BindView(R.id.tv_monitor_temptrue)
    TextView tvMonitorTemptrue;
    @BindView(R.id.tv_envirmont_temptrue)
    TextView tvEnvirmontTemptrue;
    @BindView(R.id.tv_monitor_pressure)
    TextView tvMonitorPressure;
    @BindView(R.id.switch2)
    SwitchCompat switch2;
    @BindView(R.id.gif1)
    GifView gif1;
    @BindView(R.id.gif2)
    GifView gif2;

    public String TAG = BaseProcessFragment.class.getSimpleName();
    public Context mCtx; //xxg
    public Parameter parameter;

    private boolean staticAuto = false;
    public float staticEvaR1;
    public float staticEvaR2;
    public float staticEvaR3;
    public boolean suspend = false;
    public boolean staticPause = false;
    public int mTimeInterval = 30;

    public int staticTotal = 0;
    public int testTotal = 0;
    public DataService mDataService;//xxg
    /**
     * 1:试验合格
     * 2:试验不合格
     */
    public int testState = 0;
    /**
     * 1:试验1
     * 2:试验2
     * 3:试验3
     */
    public int testProgress = 0;

    abstract void startTest();

    abstract void stopTest();

    abstract void resumeTest();

    abstract void pauseTest();

    abstract void chooseIntervalTimeDialog();

    abstract void countStaticTotalTime();

    abstract void startStaticTotalTime();

    abstract void endStaticTotalTime();

    abstract void startTestTotalTime();

    abstract void endTestTotalTime();

    abstract void testSuccess();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //服务初始化时启动 //xxg
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_process, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {
        initAllState();
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mDataService.startHeat();
                }else {
                    mDataService.stopHeat();
                }
            }
        });
    }

    public void initAllState() {
        btnStaticStart.setEnabled(true);
        btnStaticEnd.setEnabled(true);
        btnTestStart.setEnabled(true);
        btnTestStart.setText("开始试验");
        btnTestEnd.setEnabled(true);
        btnTestReport.setEnabled(false);
        tvStaticStartTime.setText("");
        tvStaticEndTime.setText("");
        tvStaticTotalTime.setText("");
        tvTestStartTime.setText("");
        tvTestEndTime.setText("");
        tvTestTotalTime.setText("");
        tvPreResult.setVisibility(View.INVISIBLE);
        tvPreResult.setSelected(false);
        tvPreResult.setText("合格");
        tvPrejudge.setText("---");
        tvFlowRate.setText("---");
        tvFlowTotal.setText("---");

        tvWeightRate.setText("---");
        tvWeightTotal.setText("---");

        tvMonitorTemptrue.setText("---");
        tvEnvirmontTemptrue.setText("---");
        tvMonitorPressure.setText("---");

        tvAlarm.setSelected(false);

        tvTestOneResult.setVisibility(View.INVISIBLE);
        tvTestOneResult.setText("合格");
        tvTestOneResult.setSelected(false);

        tvTestTwoResult.setVisibility(View.INVISIBLE);
        tvTestTwoResult.setText("合格");
        tvTestTwoResult.setSelected(false);

        tvTestThireResult.setVisibility(View.INVISIBLE);
        tvTestThireResult.setText("合格");
        tvTestThireResult.setSelected(false);

        tvCollectResult.setVisibility(View.INVISIBLE);
        tvCollectResult.setText("合格");
        tvCollectResult.setSelected(false);

        tvTestOneCount.setText("");
        tvTestTwoCount.setText("");
        tvTestThireCount.setText("");
        tvCollectError.setText("");

        testProgress = 0;
        testState = 0;
        staticTotal = 0;
        testTotal = 0;
        endStaticTotalTime();
        endTestTotalTime();
    }

    private void initData() {
        String timeInterval = (String)AppPreferences.instance().get("timeInterval","30");
        tvTimeInterval.setText(timeInterval);
        mTimeInterval = Integer.parseInt(timeInterval);

        parameter = DbManage.getInstance().getParamter();
        if (null == parameter) {
            return;
        }
        setParameterDate(parameter);
    }

    private void setParameterDate(Parameter parameter) {
        if (null == parameter) {
            return;
        }
        tvTestMedium.setText(parameter.getMediumType());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.tv_time_interval, R.id.tv_static_start_time, R.id.tv_test_medium, R.id.tv_static_end_time, R.id.tv_test_start_time, R.id.tv_test_end_time, R.id.btn_static_end, R.id.btn_test_report, R.id.btn_test_start, R.id.btn_test_end, R.id.btn_static_start, R.id.btn_unauto_heat, R.id.btn_auto_heat, R.id.btn_entrance_temperature, R.id.btn_entrance_pressure, R.id.btn_flow_counter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_interval:
                if(testProgress>0){
                    ToastHelper.showToast("试验过程中，无法选择选择介质");
                    return;
                }
                chooseIntervalTimeDialog();
                break;
            case R.id.tv_test_medium:
                if(testProgress>0){
                    ToastHelper.showToast("试验过程中，无法选择选择介质");
                    return;
                }
                List<String> mediumType = new ArrayList<>();
                mediumType.add("LN2");
                mediumType.add("LNG");
                SpinnerController spinnerMedium = new SpinnerController(getActivity());
                spinnerMedium.showSpinnerDialog(mediumType);
                spinnerMedium.setListener(new SpinnerController.SpinnerListener() {
                    @Override
                    public void selectResult(String date) {
                        tvTestMedium.setText(date);
                        parameter.setMediumType(date);
                        DbManage.getInstance().saveParamter(parameter);
                        if("LN2".equals(parameter.getMediumType())){
                            mDataService.setMediumtype(1);
                        }else if("LNG".equals(parameter.getMediumType())){
                            mDataService.setMediumtype(2);
                        }
                    }
                });
                break;
            case R.id.tv_static_start_time:
                if (staticAuto) {
                    ToastHelper.showToast("自动计时中，无法选择时间");
                    return;
                }
                if (testProgress > 0) {
                    ToastHelper.showToast("试验过程中无法选择时间");
                    return;
                }
                DateChooseController startDate = new DateChooseController(getActivity());
                startDate.showChooseDateDialog();
                startDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(String date1) {
                        tvStaticStartTime.setText(date1);
                    }
                });
                break;
            case R.id.tv_static_end_time:
                if (TextUtils.isEmpty(tvStaticStartTime.getText().toString())) {
                    ToastHelper.showToast("请先选择开始静置时间");
                    return;
                }
                if (staticAuto) {
                    ToastHelper.showToast("自动计时中，无法选择时间");
                    return;
                }
                if (testProgress > 0) {
                    ToastHelper.showToast("试验过程中无法选择时间");
                    return;
                }
                DateChooseController endDate = new DateChooseController(getActivity());
                endDate.showChooseDateDialog();
                endDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(String date1) {
                        try {
                            String startTime = tvStaticStartTime.getText().toString();
                            Date dateStart = DateUtil.StringToDate(startTime);
                            Date dateEnd = DateUtil.StringToDate(date1);
                            if (dateEnd.getTime() < dateStart.getTime()) {
                                ToastHelper.showToast("结束时间不能早于开始时间，请重新选择");
                            } else {
                                tvStaticEndTime.setText(date1);
                                countStaticTotalTime();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.tv_test_start_time:
                break;
            case R.id.tv_test_end_time:
                break;
            case R.id.btn_test_report:
                Intent intent = new Intent();
                intent.putExtra("deviceId",parameter.getDeviceId());
                intent.setClass(getActivity(), ReportActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_static_start:
                if (testProgress > 0) {
                    ToastHelper.showToast("试验过程中无法开始静置");
                    return;
                }
                if(!TextUtils.isEmpty(tvTestEndTime.getText().toString())){
                    initAllState();
                    ToastHelper.showToast("已清除上次实验数据");
                    return;
                }
                if (null == parameter) {
                    ToastHelper.showToast("试验参数未设置，请设置后开始试验");
                } else {
                    if("开始静置".equals(btnStaticStart.getText().toString())){
                        btnStaticStart.setText("暂停静置");
                        if(staticAuto){
                            staticPause = false;
                            return;
                        }
                        staticAuto = true;
//                        if(TextUtils.isEmpty(tvStaticTotalTime.getText().toString()) && TextUtils.isEmpty(tvStaticEndTime.getText().toString())){
                            tvStaticEndTime.setText("");
                            tvStaticTotalTime.setText("");
                            tvStaticTotalTime.setText("00:00:00");
                            tvStaticStartTime.setText(DateUtil.getSystemDate1());
                            staticTotal = 0;
                            startStaticTotalTime();
//                        }

                    }else {
                        btnStaticStart.setText("开始静置");
                        staticPause = true;
                    }
                }
                break;
            case R.id.btn_static_end:
                if (testProgress > 0) {
                    ToastHelper.showToast("试验过程中无法结束静置");
                    return;
                }
                if(!TextUtils.isEmpty(tvTestEndTime.getText().toString())){
                    initAllState();
                    ToastHelper.showToast("已清除上次实验数据");
                    return;
                }
                tvStaticEndTime.setText(DateUtil.getSystemDate1());
                btnStaticStart.setText("开始静置");
                endStaticTotalTime();
                staticAuto = false;
                break;
            case R.id.btn_test_start:
                if (null == parameter) {
                    ToastHelper.showToast("试验参数未设置，请设置后开始试验");
                    return;
                }
                if(TextUtils.isEmpty(tvStaticTotalTime.getText().toString()) || TextUtils.isEmpty(tvStaticEndTime.getText().toString())){
                    ToastHelper.showToast("请先选择静置时间");
                    return;
                }
                if(!TextUtils.isEmpty(tvTestEndTime.getText().toString())){
                    initAllState();
                    ToastHelper.showToast("已清除上次实验数据");
                    return;
                }
                if ("开始试验".equals(btnTestStart.getText().toString())) {
                    if (testProgress == 0) {
                        tvTestStartTime.setText(DateUtil.getSystemDate1());
                        btnStaticStart.setEnabled(false);
                        btnStaticEnd.setEnabled(false);
                        endStaticTotalTime();
                        startTestTotalTime();
                        testProgress = 1;
                        startTest();
                        btnStaticStart.setEnabled(false);
                        btnStaticEnd.setEnabled(false);
                    } else {
                        suspend = false;
                        resumeTest();
                    }
                    btnTestStart.setText("暂停试验");
                } else {
                    btnTestStart.setText("开始试验");
                    pauseTest();
                    suspend = true;
                }
                break;
            case R.id.btn_test_end:
                if (TextUtils.isEmpty(tvTestStartTime.getText().toString())) {
                    ToastHelper.showToast("没有进行中的试验");
                    return;
                }
                final CommonDialog commonDialog = new CommonDialog(getActivity());
                commonDialog.setTitle("警告")
                        .setMessage("确定结束试验吗？")
                        .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
//                        stopTest();
//                        initAllState();
                        testSuccess();
                        endStaticTotalTime();
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onNegtiveClick() {
                        commonDialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btn_auto_heat:
                if(btnAutoHeat.isSelected()){
                    btnAutoHeat.setSelected(false);
                }else {
                    btnAutoHeat.setSelected(true);
                }
                break;
            case R.id.btn_unauto_heat:
                if(btnUnautoHeat.isSelected()){
                    btnUnautoHeat.setSelected(false);
                }else {
                    btnUnautoHeat.setSelected(true);
                }
                break;
            case R.id.btn_entrance_temperature:
                break;
            case R.id.btn_entrance_pressure:
                break;
            case R.id.btn_flow_counter:
                break;
        }
    }

    private boolean CheckTimeArrive() {
        return true;
    }

    public void setBeanRTDataDate(BeanRTData beanRTData) {
        if (null == beanRTData) {
            return;
        }
        tvFlowRate.setText(String.valueOf(beanRTData.getInstantFlow()));
        tvFlowTotal.setText(String.valueOf(beanRTData.getAccFlow()));

        tvWeightRate.setText(String.valueOf(beanRTData.getInstantQuality()));
        tvWeightTotal.setText(String.valueOf(beanRTData.getAccQuality()));

        tvMonitorTemptrue.setText(String.valueOf(beanRTData.getSurroundhumidity()));
        tvEnvirmontTemptrue.setText(String.valueOf(beanRTData.getSurroundtemperature()));
        tvMonitorPressure.setText(String.valueOf(beanRTData.getSurroundpressure()));

        tvEntrancePressure.setText(String.valueOf(beanRTData.getEnterpressure()));
        tvEntranceTemperature.setText(String.valueOf(beanRTData.getEntertemperature()));
        tvFlowCounter.setText(String.valueOf(beanRTData.getInstantFlow()));

        String alarmVaule = (String) AppPreferences.instance().get("alarmValue", "5");
        double alarm = Double.parseDouble(alarmVaule)/100;
        if (beanRTData.getConcentration() > alarm) {
            tvAlarm.setSelected(true);
        } else {
            tvAlarm.setSelected(false);
        }
        switch (beanRTData.getIswarm()){
            case 5:
                btnAutoHeat.setEnabled(false);
                stopGif1();
                stopGif2();
//                playGif1();
//                playGif2();
                break;
            case 4:
                btnAutoHeat.setEnabled(true);
                playGif1();
                playGif2();
                break;
        }

    }

    public void setEvaRDate(float staticEvaR) {
        double qualificate = Double.parseDouble(parameter.getQualificationRate());
        tvPrejudge.setText(String.valueOf(staticEvaR));
        tvPreResult.setVisibility(View.VISIBLE);
        if (staticEvaR > qualificate) {//替换成NER值
            tvPreResult.setSelected(true);
            tvPreResult.setText("不合格");
        } else {
            tvPreResult.setSelected(false);
            tvPreResult.setText("合格");
        }
    }

    public void playGif1(){
        gif1.setVisibility(View.VISIBLE);
    }
    public void stopGif1(){
        gif1.setVisibility(View.GONE);
    }
    public void playGif2(){
        gif2.setVisibility(View.VISIBLE);
    }
    public void stopGif2(){
        gif2.setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端显示 相当于调用了onPause();
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initData();
        }

    }
}
