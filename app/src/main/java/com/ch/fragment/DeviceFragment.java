package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ch.bean.Sensor;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.ToastHelper;
import com.ch.view.DateChooseController;
import com.deadline.statebutton.StateButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btn_cancel)
    StateButton btnCancel;
    @BindView(R.id.btn_sure)
    StateButton btnSure;
    @BindView(R.id.edit_evaporationrate_type)
    EditText editEvaporationrateType;
    @BindView(R.id.edit_evaporationrate_num)
    EditText editEvaporationrateNum;
    @BindView(R.id.edit_laser_num)
    EditText editLaserNum;
    @BindView(R.id.edit_flowmeter_num)
    EditText editFlowmeterNum;
    @BindView(R.id.edit_temperature_num)
    EditText editTemperatureNum;
    @BindView(R.id.edit_air_pressure_num)
    EditText editAirPressureNum;
    @BindView(R.id.edit_pressure_num)
    EditText editPressureNum;
    @BindView(R.id.edit_humidity_num)
    EditText editHumidityNum;
    @BindView(R.id.edit_humidity_passed_date)
    EditText editHumidityPassedDate;
    @BindView(R.id.edit_laser_passed_date)
    EditText editLaserPassedDate;
    @BindView(R.id.edit_flowmeter_passed_date)
    EditText editFlowmeterPassedDate;
    @BindView(R.id.edit_temperature_passed_date)
    EditText editTemperaturePassedDate;
    @BindView(R.id.edit_air_pressure_passed_date)
    EditText editAirPressurePassedDate;
    @BindView(R.id.edit_pressure_passed_date)
    EditText editPressurePassedDate;
    @BindView(R.id.tv_laser_test_date)
    TextView tvLaserTestDate;
    @BindView(R.id.tv_flowmeter_test_date)
    TextView tvFlowmeterTestDate;
    @BindView(R.id.tv_temperature_test_date)
    TextView tvTemperatureTestDate;
    @BindView(R.id.tv_air_pressure_test_date)
    TextView tvAirPressureTestDate;
    @BindView(R.id.tv_pressure_test_date)
    TextView tvPressureTestDate;
    @BindView(R.id.tv_humidity_test_date)
    TextView tvHumidityTestDate;
    @BindView(R.id.edit_laser_type)
    EditText editLaserType;
    @BindView(R.id.edit_flowmeter_type)
    EditText editFlowmeterType;
    @BindView(R.id.edit_temperature_type)
    EditText editTemperatureType;
    @BindView(R.id.edit_air_pressure_type)
    EditText editAirPressureType;
    @BindView(R.id.edit_pressure_type)
    EditText editPressureType;
    @BindView(R.id.edit_humidity_type)
    EditText editHumidityType;
    @BindView(R.id.tv_humidity_test_state)
    TextView tvHumidityTestState;
    @BindView(R.id.tv_humidity_device_state)
    TextView tvHumidityDeviceState;
    @BindView(R.id.tv_humidity_value)
    TextView tvHumidityValue;
    @BindView(R.id.tv_laser_value)
    TextView tvLaserValue;
    @BindView(R.id.tv_flowmeter_value)
    TextView tvFlowmeterValue;
    @BindView(R.id.tv_temperature_value)
    TextView tvTemperatureValue;
    @BindView(R.id.tv_air_pressure_value)
    TextView tvAirPressureValue;
    @BindView(R.id.tv_pressure_value)
    TextView tvPressureValue;
    @BindView(R.id.tv_laser_device_state)
    TextView tvLaserDeviceState;
    @BindView(R.id.tv_flowmeter_device_state)
    TextView tvFlowmeterDeviceState;
    @BindView(R.id.tv_temperature_device_state)
    TextView tvTemperatureDeviceState;
    @BindView(R.id.tv_air_pressure_device_state)
    TextView tvAirPressureDeviceState;
    @BindView(R.id.tv_pressure_device_state)
    TextView tvPressureDeviceState;
    @BindView(R.id.tv_laser_test_state)
    TextView tvLaserTestState;
    @BindView(R.id.tv_flowmeter_test_state)
    TextView tvFlowmeterTestState;
    @BindView(R.id.tv_temperature_test_state)
    TextView tvTemperatureTestState;
    @BindView(R.id.tv_air_pressure_test_state)
    TextView tvAirPressureTestState;
    @BindView(R.id.tv_pressure_test_state)
    TextView tvPressureTestState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_device, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {

    }

    private void initData() {
        Sensor sensor = DbManage.getInstance().getSensor();
        if(null == sensor){
            return;
        }
        editLaserType.setText(sensor.getLaserType());
        editLaserNum.setText(sensor.getLaserNum());
        tvLaserTestDate.setText(sensor.getLaserTestDate());
        editLaserPassedDate.setText(sensor.getLaserPassedDate());

        editFlowmeterType.setText(sensor.getFlowmeterType());
        editFlowmeterNum.setText(sensor.getFlowmeterNum());
        tvFlowmeterTestDate.setText(sensor.getFlowmeterTestDate());
        editFlowmeterPassedDate.setText(sensor.getFlowmeterPassedDate());

        editTemperatureType.setText(sensor.getTemperatureType());
        editTemperatureNum.setText(sensor.getTemperatureNum());
        tvTemperatureTestDate.setText(sensor.getTemperatureTestDate());
        editTemperaturePassedDate.setText(sensor.getTemperaturePassedDate());

        editAirPressureType.setText(sensor.getAirPressureType());
        editAirPressureNum.setText(sensor.getAirPressureNum());
        tvAirPressureTestDate.setText(sensor.getAirPressureTestDate());
        editAirPressurePassedDate.setText(sensor.getAirPressurePassedDate());

        editPressureType.setText(sensor.getPressureType());
        editPressureNum.setText(sensor.getPressureNum());
        tvPressureTestDate.setText(sensor.getPressureTestDate());
        editPressurePassedDate.setText(sensor.getPressurePassedDate());

        editHumidityType.setText(sensor.getHumidityType());
        editHumidityNum.setText(sensor.getHumidityNum());
        tvHumidityTestDate.setText(sensor.getHumidityTestDate());
        editHumidityPassedDate.setText(sensor.getHumidityPassedDate());

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.POSTING)
    public void Event(BeanRTData beanRTData) {
        refreshRtData(beanRTData);
    }

    private void refreshRtData(BeanRTData beanRTData) {
        tvLaserValue.setText(beanRTData.getInstantFlow()+"");
        tvFlowmeterValue.setText(beanRTData.getInstantFlow()+"");
        tvTemperatureValue.setText(beanRTData.getEntertemperature()+"");
        tvAirPressureValue.setText(beanRTData.getSurroundpressure()+"");
        tvPressureValue.setText(beanRTData.getEnterpressure()+"");
        tvPressureValue.setText(beanRTData.getEnterpressure()+"");
        tvHumidityValue.setText(beanRTData.getSurroundhumidity()+"");
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
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.tv_laser_test_date, R.id.tv_flowmeter_test_date, R.id.tv_temperature_test_date, R.id.tv_air_pressure_test_date, R.id.tv_pressure_test_date, R.id.tv_humidity_test_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_sure:
                if(checkInputLegal()){
                    saveDateToDb();
                    ToastHelper.showToast("保存成功");
                }else {
                    ToastHelper.showToast("有未填写信息，请填写完整后提交");
                }
                break;
            case R.id.tv_laser_test_date:
                DateChooseController laserTestDate = new DateChooseController(getActivity());
                laserTestDate.chooseSingleDateDialog();
                laserTestDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvLaserTestDate.setText(date1);
                    }
                });
                break;
            case R.id.tv_flowmeter_test_date:
                DateChooseController flowmeterTestDate = new DateChooseController(getActivity());
                flowmeterTestDate.chooseSingleDateDialog();
                flowmeterTestDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvFlowmeterTestDate.setText(date1);
                    }
                });
                break;
            case R.id.tv_temperature_test_date:
                DateChooseController temperatureTestDate = new DateChooseController(getActivity());
                temperatureTestDate.chooseSingleDateDialog();
                temperatureTestDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvTemperatureTestDate.setText(date1);
                    }
                });
                break;
            case R.id.tv_air_pressure_test_date:
                DateChooseController airPressureTestDate = new DateChooseController(getActivity());
                airPressureTestDate.chooseSingleDateDialog();
                airPressureTestDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvAirPressureTestDate.setText(date1);
                    }
                });
                break;
            case R.id.tv_pressure_test_date:
                DateChooseController pressureTestDate = new DateChooseController(getActivity());
                pressureTestDate.chooseSingleDateDialog();
                pressureTestDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvPressureTestDate.setText(date1);
                    }
                });
                break;
            case R.id.tv_humidity_test_date:
                DateChooseController humidityDate = new DateChooseController(getActivity());
                humidityDate.chooseSingleDateDialog();
                humidityDate.setListener(new DateChooseController.DateChooseListener() {
                    @Override
                    public void dateResult(final String date1) {
                        tvHumidityTestDate.setText(date1);
                    }
                });
                break;
        }
    }

    private boolean checkInputLegal(){
//        if(TextUtils.isEmpty(editLaserType.getText().toString().trim()) || TextUtils.isEmpty(editLaserNum.getText().toString().trim()) ||TextUtils.isEmpty(tvLaserTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editLaserPassedDate.getText().toString().trim()) ||
//                TextUtils.isEmpty(editFlowmeterType.getText().toString().trim()) ||TextUtils.isEmpty(editFlowmeterNum.getText().toString().trim()) ||TextUtils.isEmpty(tvFlowmeterTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editFlowmeterPassedDate.getText().toString().trim()) ||
//                TextUtils.isEmpty(editTemperatureType.getText().toString().trim()) ||TextUtils.isEmpty(editTemperatureNum.getText().toString().trim()) ||TextUtils.isEmpty(tvTemperatureTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editTemperaturePassedDate.getText().toString().trim()) ||
//                TextUtils.isEmpty(editAirPressureType.getText().toString().trim()) ||TextUtils.isEmpty(editAirPressureNum.getText().toString().trim()) ||TextUtils.isEmpty(tvAirPressureTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editAirPressurePassedDate.getText().toString().trim()) ||
//                TextUtils.isEmpty(editPressureType.getText().toString().trim()) ||TextUtils.isEmpty(editPressureNum.getText().toString().trim()) ||TextUtils.isEmpty(tvPressureTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editPressurePassedDate.getText().toString().trim()) ||
//                TextUtils.isEmpty(editHumidityType.getText().toString().trim()) ||TextUtils.isEmpty(editHumidityNum.getText().toString().trim()) ||TextUtils.isEmpty(tvHumidityTestDate.getText().toString().trim()) ||TextUtils.isEmpty(editHumidityPassedDate.getText().toString().trim())){
//            return false;
//        }
        return true;
    }

    private void saveDateToDb() {
        if(null == DbManage.getInstance().getParamter()){
            return;
        }
        Sensor sensor = new Sensor();
        sensor.setDeviceId(DbManage.getInstance().getParamter().getDeviceId());
        sensor.setLaserType(editLaserType.getText().toString().trim());
        sensor.setLaserNum(editLaserNum.getText().toString().trim());
        sensor.setLaserTestDate(tvLaserTestDate.getText().toString().trim());
        sensor.setLaserPassedDate(editLaserPassedDate.getText().toString().trim());

        sensor.setFlowmeterType(editFlowmeterType.getText().toString().trim());
        sensor.setFlowmeterNum(editFlowmeterNum.getText().toString().trim());
        sensor.setFlowmeterTestDate(tvFlowmeterTestDate.getText().toString().trim());
        sensor.setFlowmeterPassedDate(editFlowmeterPassedDate.getText().toString().trim());

        sensor.setTemperatureType(editTemperatureType.getText().toString().trim());
        sensor.setTemperatureNum(editTemperatureNum.getText().toString().trim());
        sensor.setTemperatureTestDate(tvTemperatureTestDate.getText().toString().trim());
        sensor.setTemperaturePassedDate(editTemperaturePassedDate.getText().toString().trim());

        sensor.setAirPressureType(editAirPressureType.getText().toString().trim());
        sensor.setAirPressureNum(editAirPressureNum.getText().toString().trim());
        sensor.setAirPressureTestDate(tvAirPressureTestDate.getText().toString().trim());
        sensor.setAirPressurePassedDate(editAirPressurePassedDate.getText().toString().trim());

        sensor.setPressureType(editPressureType.getText().toString().trim());
        sensor.setPressureNum(editPressureNum.getText().toString().trim());
        sensor.setPressureTestDate(tvPressureTestDate.getText().toString().trim());
        sensor.setPressurePassedDate(editPressurePassedDate.getText().toString().trim());

        sensor.setHumidityType(editHumidityType.getText().toString().trim());
        sensor.setHumidityNum(editHumidityNum.getText().toString().trim());
        sensor.setHumidityTestDate(tvHumidityTestDate.getText().toString().trim());
        sensor.setHumidityPassedDate(editHumidityPassedDate.getText().toString().trim());

        DbManage.getInstance().saveSensor(sensor);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端显示 相当于调用了onPause();
            if(checkInputLegal()){
                saveDateToDb();
            }
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            initData();
        }

    }
}
