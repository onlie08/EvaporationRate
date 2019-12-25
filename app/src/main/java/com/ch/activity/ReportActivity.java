package com.ch.activity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ch.bean.Parameter;
import com.ch.bean.Sensor;
import com.ch.bean.TestProcess;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.utils.DateUtil;
import com.ch.utils.ToastHelper;
import com.deadline.statebutton.StateButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.tv_checkout_company)
    TextView tvCheckoutCompany;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceId;
    @BindView(R.id.tv_report_time)
    TextView tvReportTime;
    @BindView(R.id.btn_export)
    StateButton btnExport;
    @BindView(R.id.btn_printing)
    StateButton btnPrinting;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_device_num)
    TextView tvDeviceNum;
    @BindView(R.id.tv_test_device_num)
    TextView tvTestDeviceNum;
    @BindView(R.id.tv_test_date)
    TextView tvTestDate;
    @BindView(R.id.tv_use_device_company)
    TextView tvUseDeviceCompany;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_madein_date)
    TextView tvMadeinDate;
    @BindView(R.id.tv_effective_volume)
    TextView tvEffectiveVolume;
    @BindView(R.id.tv_mediu_type)
    TextView tvMediuType;
    @BindView(R.id.tv_measurement_volume)
    TextView tvMeasurementVolume;
    @BindView(R.id.tv_design_standard)
    TextView tvDesignStandard;
    @BindView(R.id.tv_license_no)
    TextView tvLicenseNo;
    @BindView(R.id.tv_evaporationrate_info)
    TextView tvEvaporationrateInfo;
    @BindView(R.id.tv_flowmeter_info)
    TextView tvFlowmeterInfo;
    @BindView(R.id.tv_temperature_info)
    TextView tvTemperatureInfo;
    @BindView(R.id.tv_pressure_info)
    TextView tvPressureInfo;
    @BindView(R.id.tv_humidity_info)
    TextView tvHumidityInfo;
    @BindView(R.id.tv_air_pressure_info)
    TextView tvAirPressureInfo;
    @BindView(R.id.tv_sensor_state)
    TextView tvSensorState;
    @BindView(R.id.tv_laser_info)
    TextView tvLaserInfo;
    @BindView(R.id.tv_test_method)
    TextView tvTestMethod;
    @BindView(R.id.tv_test_type1)
    TextView tvTestType1;
    @BindView(R.id.tv_static_time_info)
    TextView tvStaticTimeInfo;
    @BindView(R.id.tv_full_rate)
    TextView tvFullRate;
    @BindView(R.id.tv_liquidfilling_date_info)
    TextView tvLiquidfillingDateInfo;
    @BindView(R.id.tv_test_start_time)
    TextView tvTestStartTime;
    @BindView(R.id.tv_after_test_device_state)
    TextView tvAfterTestDeviceState;
    @BindView(R.id.tv_test_end_time)
    TextView tvTestEndTime;
    @BindView(R.id.tv_average_pressure)
    TextView tvAveragePressure;
    @BindView(R.id.tv_average_tempture)
    TextView tvAverageTempture;
    @BindView(R.id.tv_average_evaporation)
    TextView tvAverageEvaporation;
    @BindView(R.id.tv_evaporationrate_final)
    TextView tvEvaporationrateFinal;
    @BindView(R.id.tv_evaporationrate_one)
    TextView tvEvaporationrateOne;
    @BindView(R.id.tv_evaporationrate_two)
    TextView tvEvaporationrateTwo;
    @BindView(R.id.tv_two_erro)
    TextView tvTwoErro;
    @BindView(R.id.tv_evaporationrate_thire)
    TextView tvEvaporationrateThire;
    @BindView(R.id.tv_ner)
    TextView tvNer;
    @BindView(R.id.tv_test_standard)
    TextView tvTestStandard;
    @BindView(R.id.tv_test_requirement)
    TextView tvTestRequirement;
    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.tv_test_date1)
    TextView tvTestDate1;
    @BindView(R.id.tv_next_test_date)
    TextView tvNextTestDate;
    @BindView(R.id.tv_test_person)
    TextView tvTestPerson;
    @BindView(R.id.tv_verify_person)
    TextView tvVerifyPerson;
    @BindView(R.id.tv_chapter)
    ConstraintLayout tvChapter;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    public Parameter parameter;
    public Sensor sensor;
    public TestProcess testProcess;
    public String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_new);
        ButterKnife.bind(this);
        deviceId = getIntent().getStringExtra("deviceId");
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        if(TextUtils.isEmpty(deviceId)){
            return;
        }
        parameter = DbManage.getInstance().queryParamter(deviceId);
        if (null != parameter) {
            setParameterDate(parameter);
        }
        sensor = DbManage.getInstance().querySensor(deviceId);
        if (null != sensor) {
            setSensor(sensor);
        }
        testProcess = DbManage.getInstance().queryTestProcess(deviceId);
        if (null != testProcess) {
            setTestProcess(testProcess);
        }
    }

    private void setTestProcess(TestProcess testProcess) {
//        tvTestDeviceNum.setText(isNotNull(testProcess.getDeviceId()) ? testProcess.getDeviceId():"---");
        tvEvaporationrateOne.setText(isNotNull(testProcess.getEvaporationRateOne()) ? testProcess.getEvaporationRateOne():"---");
        tvEvaporationrateTwo.setText(isNotNull(testProcess.getEvaporationRateTwo()) ? testProcess.getEvaporationRateTwo():"---");
        tvEvaporationrateThire.setText(isNotNull(testProcess.getEvaporationRateThire()) ? testProcess.getEvaporationRateThire():"---");
        tvTwoErro.setText(isNotNull(testProcess.getAcquisitionError()) ? testProcess.getAcquisitionError():"---");
        tvEvaporationrateFinal.setText(isNotNull(testProcess.getEvaporationRateFinal()) ? testProcess.getEvaporationRateFinal():"---");
        tvStaticTimeInfo.setText(testProcess.getStaticStartTime() + " 至 " + testProcess.getStaticEndTime());
        tvTestDate.setText(testProcess.getTestStartTime() + " 至 " + testProcess.getTestEndTime());
        tvTestStartTime.setText(testProcess.getTestStartTime());
        tvTestEndTime.setText(isNotNull(testProcess.getTestEndTime()) ? testProcess.getTestEndTime():"---");

        tvEvaporationrateFinal.setText(isNotNull(testProcess.getEvaporationRateFinal()) ? testProcess.getEvaporationRateFinal():"---");
        tvEvaporationrateFinal.setText(isNotNull(testProcess.getEvaporationRateFinal()) ? testProcess.getEvaporationRateFinal():"---");
        tvAveragePressure.setText(testProcess.getSurroundpressure()+"");
        tvAverageTempture.setText(testProcess.getSurroundtemperature()+"");
        tvAverageEvaporation.setText(testProcess.getSurroundhumidity()+"");
    }

    private void setSensor(Sensor sensor) {
        String evaporationrateInfo = sensor.getEvaporationRateType()+sensor.getEvaporationRateNum();
        if(!TextUtils.isEmpty(evaporationrateInfo)){
            tvEvaporationrateInfo.setText(evaporationrateInfo);
        }
        String flowmeterInfo = sensor.getFlowmeterType()+sensor.getFlowmeterNum();
        if(!TextUtils.isEmpty(flowmeterInfo)){
            tvFlowmeterInfo.setText(flowmeterInfo);
        }
        String temperatureInfo = sensor.getTemperatureType() + sensor.getTemperatureNum();
        if(!TextUtils.isEmpty(temperatureInfo)){
            tvTemperatureInfo.setText(temperatureInfo);
        }
        String pressureInfo = sensor.getPressureType() + sensor.getPressureNum();
        if(!TextUtils.isEmpty(pressureInfo)){
            tvPressureInfo.setText(pressureInfo);
        }
        String humidityInfo = sensor.getHumidityType() + sensor.getHumidityNum();
        if(!TextUtils.isEmpty(humidityInfo)){
            tvHumidityInfo.setText(humidityInfo);
        }
        String airPressureInfo = sensor.getAirPressureType() + sensor.getAirPressureNum();
        if(!TextUtils.isEmpty(airPressureInfo)){
            tvAirPressureInfo.setText(airPressureInfo);
        }
        String laserInfo = sensor.getLaserType() + sensor.getLaserNum();
        if(!TextUtils.isEmpty(laserInfo)){
            tvLaserInfo.setText(laserInfo);
        }

    }

    private void setParameterDate(Parameter parameter) {
        tvCheckoutCompany.setText(isNotNull(parameter.getCheckoutCompany()) ? parameter.getCheckoutCompany():"---");
        tvDeviceId.setText(isNotNull(parameter.getDeviceId()) ? parameter.getDeviceId():"---");
        tvReportTime.setText(DateUtil.getSystemDate());
        tvDeviceNum.setText(isNotNull(parameter.getDeviceId()) ? parameter.getDeviceId():"---");
        tvTestDeviceNum.setText("---");
        tvUseDeviceCompany.setText(isNotNull(parameter.getUseDeviceCompany()) ? parameter.getUseDeviceCompany():"---");
        tvDeviceType.setText(isNotNull(parameter.getDeviceType()) ? parameter.getDeviceType():"---");
        tvDeviceName.setText(isNotNull(parameter.getDeviceName()) ? parameter.getDeviceName():"---");
        tvMadeinDate.setText(isNotNull(parameter.getMadeinDate()) ? parameter.getMadeinDate():"---");
        tvEffectiveVolume.setText(isNotNull(parameter.getEffectiveVolume()) ? parameter.getEffectiveVolume():"---");
        tvMediuType.setText(isNotNull(parameter.getMediumType()) ? parameter.getMediumType():"---");
        tvTestType1.setText(isNotNull(parameter.getMediumType()) ? parameter.getMediumType():"---");
        tvMeasurementVolume.setText(isNotNull(parameter.getMeasurementVolume()) ? parameter.getMeasurementVolume():"---");
        tvDesignStandard.setText(isNotNull(parameter.getDesignStandard()) ? parameter.getDesignStandard():"---");
        tvLicenseNo.setText(isNotNull(parameter.getLicenseNo()) ? parameter.getLicenseNo():"---");
        tvFullRate.setText(isNotNull(parameter.getFullnessRate()) ? parameter.getFullnessRate():"---");
        tvNer.setText(isNotNull(parameter.getQualificationRate()) ? parameter.getQualificationRate():"---");
        tvTestStandard.setText(isNotNull(parameter.getDesignStandard()) ? parameter.getDesignStandard():"---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @OnClick({R.id.btn_export, R.id.btn_printing, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_export:
                progressBar2.setVisibility(View.VISIBLE);
                tvTip.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pdfModel();
                    }
                }).start();
                break;
            case R.id.btn_printing:
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private boolean isNotNull(String text){
        if(TextUtils.isEmpty(text)){
            return false;
        }
        return true;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            progressBar2.setVisibility(View.GONE);
            tvTip.setVisibility(View.GONE);
            ToastHelper.showLongToast("Pdf报告成功生成，已保存到本地");
            return false;
        }
    });

    private void pdfModel(){
        PdfDocument document = new PdfDocument();
        // ll_model是一个LinearLayout
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(scrollView2.getWidth(),scrollView2.getChildAt(0).getHeight(),2).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        scrollView2.draw(page.getCanvas());
//        Canvas canvas = page.getCanvas();
//        canvas.translate(0, -scrollView2.getHeight());
//        scrollView2.draw(canvas);
        document.finishPage(page);

        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "/001evaporation/"+parameter.getDeviceId()+"_report.pdf";
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }else {
                file.createNewFile();//创建文件
            }
            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream(file);
            document.writeTo(outputStream);
            document.close();
            handler.sendEmptyMessage(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void pdfInterviewContent(PdfDocument document) {
////        // 一页pdf的高度
////        int onePageHeight = tv_content.getLineHeight() * 30;
//        int onePageHeight = scrollView2.getChildAt(0).getHeight() * 30;
//        // TextView中总共有多少行
//        int lineCount = tv_content.getLineCount();
//        // 计算这个TextView需要分成多少页
//        int pdfCount = 3;
//        for (int i = 0; i < pdfCount; i++) {
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(tv_content.getWidth(), onePageHeight + 120, 1)
//                    .setContentRect(new Rect(0, 60, tv_content.getWidth(), onePageHeight + 60))
//                    .create();
//            PdfDocument.Page page = document.startPage(pageInfo);
//            Canvas canvas = page.getCanvas();
//            canvas.translate(0, -onePageHeight * i);
//            tv_content.draw(canvas);
//            document.finishPage(page);
//        }
//        File file = new File(getPdfFilePath(pdfName));
//        FileOutputStream outputStream = new FileOutputStream(file);
//        try {
//            document.writeTo(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        document.close();
//    }
}
