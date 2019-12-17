package com.ch.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.bean.Parameter;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.DataService;
import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.DateUtil;
import com.ch.utils.ToastHelper;
import com.ch.view.CommonDialog;
import com.ch.view.SpinnerController;
import com.deadline.statebutton.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProcessFragment extends Fragment {
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
    @BindView(R.id.btn_entrance_temperature)
    StateButton btnEntranceTemperature;
    @BindView(R.id.btn_entrance_pressure)
    StateButton btnEntrancePressure;
    @BindView(R.id.btn_flow_counter)
    StateButton btnFlowCounter;
    @BindView(R.id.tv_monitor_temptrue)
    TextView tvMonitorTemptrue;
    @BindView(R.id.tv_envirmont_temptrue)
    TextView tvEnvirmontTemptrue;
    @BindView(R.id.tv_monitor_pressure)
    TextView tvMonitorPressure;
    @BindView(R.id.switch2)
    SwitchCompat switch2;
    private Context mCtx; //xxg
    private Parameter parameter;

    private DataService mDataService;//xxg
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Data service", "---service connected");

            mDataService = ((DataService.LocalBinder) iBinder).getService();

            mDataService.addOnDataCallback(new DataService.OnDataCallback() {
                @Override
                public void revRealTimeData(BeanRTData data) {
                    setBeanRTDataDate(data);
                    Log.d("Data service", "---activity get data:" + data.toString());
                }

                @Override
                public void revCalResult(Float testEvaR, Float staticEvaR) {
                    setEvaRDate(testEvaR,staticEvaR);
                    Log.d("Data service", "---calculate data:" + testEvaR + "--" + staticEvaR);
                }

                @Override
                public void revAcqTimeData(BeanRTData data) {
                    setBeanRTDataDate(data);
                    Log.d("Data service", "---revAcqTimeData data:" + data.toString());
                }

                @Override
                public void experimentOver(BeanRTData data, Float testEvaR, Float staticEvaR) {

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Data service", "---service disconnected");
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //服务初始化时启动 //xxg
        mCtx = this.getActivity();
        Intent intent = new Intent(mCtx, DataService.class);
        ((Activity) mCtx).bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_process, container, false);
        unbinder = ButterKnife.bind(this, root);
        root.findViewById(R.id.btn_test_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                Toast.makeText(mCtx, "test", Toast.LENGTH_SHORT).show();

            }
        });
        initView();
        initData();
        return root;
    }

    private void initView() {
        btnStaticStart.setEnabled(true);
        btnStaticEnd.setEnabled(false);
        btnTestStart.setEnabled(false);
        btnTestEnd.setEnabled(false);
        btnTestReport.setEnabled(false);
    }

    private void initData() {
        parameter = DbManage.getInstance().getParamter();
        if (null == parameter) {
            return;
        }
        setParameterDate(parameter);
    }

    private void setParameterDate(Parameter parameter){
        if(null == parameter){
            return;
        }
        tvTestMedium.setText(parameter.getMediumType());
    }

    private void setBeanRTDataDate(BeanRTData beanRTData){
        if(null == beanRTData){
            return;
        }
        tvFlowRate.setText(String.valueOf(beanRTData.getInstantFlow()));
        tvFlowTotal.setText(String.valueOf(beanRTData.getAccFlow()));

        tvWeightRate.setText(String.valueOf(beanRTData.getInstantQuality()));
        tvWeightTotal.setText(String.valueOf(beanRTData.getAccQuality()));

        tvMonitorTemptrue.setText(String.valueOf(beanRTData.getSurroundhumidity()));
        tvEnvirmontTemptrue.setText(String.valueOf(beanRTData.getSurroundtemperature()));
        tvMonitorPressure.setText(String.valueOf(beanRTData.getSurroundpressure()));

        btnEntrancePressure.setText("入口压力: "+String.valueOf(beanRTData.getEnterpressure())+"KPa");
        btnEntranceTemperature.setText("入口温度: "+String.valueOf(beanRTData.getEntertemperature())+"℃");
        btnFlowCounter.setText("流量计: "+String.valueOf(beanRTData.getInstantFlow())+"L/min");

        if(beanRTData.getConcentration() > 5){
            tvAlarm.setSelected(true);
        }else {
            tvAlarm.setSelected(false);
        }
    }

    private void setEvaRDate(float testEvaR, float staticEvaR){
        tvPrejudge.setText(String.valueOf(testEvaR));
        if(testEvaR > 5){
            tvPreResult.setSelected(true);
            tvPreResult.setText("不合格");
        }else {
            tvPreResult.setSelected(false);
            tvPreResult.setText("合格");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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


    @OnClick({R.id.tv_time_interval, R.id.tv_static_start_time, R.id.tv_static_end_time, R.id.tv_static_total_time, R.id.tv_test_start_time, R.id.tv_test_end_time, R.id.tv_test_total_time, R.id.btn_static_end, R.id.btn_test_report, R.id.btn_test_start, R.id.btn_test_end, R.id.btn_static_start, R.id.btn_auto_heat, R.id.btn_entrance_temperature, R.id.btn_entrance_pressure, R.id.btn_flow_counter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_interval:
                chooseIntervalTimeDialog();
                break;
            case R.id.tv_static_start_time:
                break;
            case R.id.tv_static_end_time:
                break;
            case R.id.tv_static_total_time:
                break;
            case R.id.tv_test_start_time:
                break;
            case R.id.tv_test_end_time:
                break;
            case R.id.tv_test_total_time:
                break;
            case R.id.btn_test_report:
                break;
            case R.id.btn_test_start:
                if (null == parameter) {
                    ToastHelper.showToast("试验参数未设置，请设置后开始试验");
                }else {
                    btnStaticStart.setEnabled(false);
                    btnStaticEnd.setEnabled(false);
                    btnTestStart.setEnabled(false);
                    btnTestEnd.setEnabled(true);
                    tvTestStartTime.setText(DateUtil.getSystemDate1());
                }
                break;
            case R.id.btn_test_end:
                if(CheckTimeArrive()){
                    btnStaticStart.setEnabled(false);
                    btnStaticEnd.setEnabled(false);
                    btnTestStart.setEnabled(false);
                    btnTestEnd.setEnabled(false);
                    btnTestReport.setEnabled(true);
                    tvTestEndTime.setText(DateUtil.getSystemDate1());
                }else {
                    final CommonDialog commonDialog = new CommonDialog(getActivity());
                    commonDialog.setMessage("试验时间未达标准，结束试验会删除此次试验数据，确定结束试验吗？");
                    commonDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            btnStaticStart.setEnabled(true);
                            btnStaticEnd.setEnabled(false);
                            btnTestStart.setEnabled(false);
                            btnTestEnd.setEnabled(false);
                            tvStaticStartTime.setText("");
                            tvStaticEndTime.setText("");
                            tvStaticTotalTime.setText("");
                            tvTestStartTime.setText("");
                            tvTestEndTime.setText("");
                            tvTestTotalTime.setText("");
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onNegtiveClick() {
                            commonDialog.dismiss();
                        }
                    });
                    commonDialog.show();
                }
                break;
            case R.id.btn_static_start:
                if (null == parameter) {
                    ToastHelper.showToast("试验参数未设置，请设置后开始试验");
                }else {
                    btnStaticStart.setEnabled(false);
                    btnStaticEnd.setEnabled(true);
                    tvStaticStartTime.setText(DateUtil.getSystemDate1());
                }
                break;
            case R.id.btn_static_end:
                if(CheckTimeArrive()){
                    btnStaticStart.setEnabled(false);
                    btnStaticEnd.setEnabled(false);
                    btnTestStart.setEnabled(true);
                    tvStaticEndTime.setText(DateUtil.getSystemDate1());
                }else {
                    final CommonDialog commonDialog = new CommonDialog(getActivity());
                    commonDialog.setMessage("静置时间未达标准，结束静置将无法进行试验，确定结束静置吗？");
                    commonDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            btnStaticStart.setEnabled(true);
                            btnStaticEnd.setEnabled(false);
                            tvStaticStartTime.setText("");
                            tvStaticEndTime.setText("");
                            tvStaticTotalTime.setText("");
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onNegtiveClick() {
                            commonDialog.dismiss();
                        }
                    });
                    commonDialog.show();
                }

                break;
            case R.id.btn_auto_heat:
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

    private void chooseIntervalTimeDialog(){
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

}
