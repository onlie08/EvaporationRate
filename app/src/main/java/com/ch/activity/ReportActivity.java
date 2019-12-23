package com.ch.activity;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ch.bean.Parameter;
import com.ch.bean.Sensor;
import com.ch.bean.TestProcess;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.utils.DateUtil;
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

    public Parameter parameter;
    public Sensor sensor;
    public TestProcess testProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_new);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        parameter = DbManage.getInstance().getParamter();
        if (null != parameter) {
            setParameterDate(parameter);
        }
        sensor = DbManage.getInstance().getSensor();
        if (null != sensor) {
            setSensor(sensor);
        }
        testProcess = DbManage.getInstance().getTestProcess();
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
    }

    private void setSensor(Sensor sensor) {

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
                pdfModel();
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

    private void saveReport(){
        PdfDocument document = new PdfDocument();//1, 建立PdfDocument
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                scrollView2.getMeasuredWidth(), scrollView2.getMeasuredHeight(), 1).create();//2
        PdfDocument.Page page = document.startPage(pageInfo);
        scrollView2.draw(page.getCanvas());//3
        document.finishPage(page);//4
        document.close();//5
        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "/001evaporation/"+"table.pdf";
            File e = new File(path);
            if (e.exists()) {
                e.delete();
            }else {
            }
            document.writeTo(new FileOutputStream(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pdfModel(){
        PdfDocument document = new PdfDocument();
        // ll_model是一个LinearLayout
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(scrollView2.getWidth(),scrollView2.getHeight(),1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        scrollView2.draw(page.getCanvas());
        document.finishPage(page);

        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "/001evaporation/"+"table.pdf";
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
