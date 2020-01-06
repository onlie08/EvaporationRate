package com.ch.activity;

import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ch.adapter.HistroyListAdapter;
import com.ch.bean.Parameter;
import com.ch.bean.Sensor;
import com.ch.bean.TestProcess;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.AppPreferences;
import com.ch.utils.BrightnessTools;
import com.ch.utils.DateUtil;
import com.ch.utils.ToastHelper;
import com.deadline.statebutton.StateButton;
import com.why.project.poilib.PoiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {
    HistroyListAdapter histroyListAdapter;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_export)
    StateButton btnExport;
    @BindView(R.id.btn_printing)
    StateButton btnPrinting;

    public Parameter parameter;
    public Sensor sensor;
    public TestProcess testProcess;
    public List<BeanRTData> beanRTDatas;
    public String deviceId;
    @BindView(R.id.tv_equipment_num)
    TextView tvEquipmentNum;
    @BindView(R.id.tv_temperature_info)
    TextView tvTemperatureInfo;
    @BindView(R.id.tv_test_date)
    TextView tvTestDate;
    @BindView(R.id.tv_liquidfilling_date_info)
    TextView tvLiquidfillingDateInfo;
    @BindView(R.id.tv_flowmeter_info)
    TextView tvFlowmeterInfo;
    @BindView(R.id.tv_air_pressure_info)
    TextView tvAirPressureInfo;
    @BindView(R.id.tv_test_address)
    TextView tvTestAddress;
    @BindView(R.id.tv_recorder)
    TextView tvRecorder;
    @BindView(R.id.tv_checker)
    TextView tvChecker;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.tv_tip)
    TextView tvTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_new);
        ButterKnife.bind(this);
        int bright = (int)AppPreferences.instance().get("bright",255);
        BrightnessTools.setBrightness(this,bright);
        deviceId = getIntent().getStringExtra("deviceId");
        initView();
        initData();
    }

    private void initView() {
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        tvRecorder.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvRecorder.getPaint().setAntiAlias(true);//抗锯齿
        tvChecker.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChecker.getPaint().setAntiAlias(true);//抗锯齿
//        tvRecorder.setText(Html.fromHtml("<u>" + "      " + "</u>"));
//        tvChecker.setText(Html.fromHtml("<u>" + "      " + "</u>"));
    }

    private void initData() {
        if (TextUtils.isEmpty(deviceId)) {
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
        beanRTDatas = DbManage.getInstance().queryBeanRTData(deviceId);
        if (null != beanRTDatas) {
            histroyListAdapter = new HistroyListAdapter(beanRTDatas);
            recyclerHistory.setAdapter(histroyListAdapter);
        }
    }

    private void setParameterDate(Parameter parameter) {
        if (!TextUtils.isEmpty(parameter.getTestAddress())) {
            tvTestAddress.setText(parameter.getTestAddress());
        }
        if (!TextUtils.isEmpty(parameter.getLiquidFillingEndDate())) {
            tvLiquidfillingDateInfo.setText(parameter.getLiquidFillingEndDate());
        }
        if (!TextUtils.isEmpty(parameter.getTestDate())) {
            tvTestDate.setText(parameter.getTestDate());
        }
    }

    private void setTestProcess(TestProcess testProcess) {

    }

    private void setSensor(Sensor sensor) {
        String airPressureInfo = sensor.getAirPressureType() + sensor.getAirPressureNum();
        if (!TextUtils.isEmpty(airPressureInfo)) {
            tvAirPressureInfo.setText(airPressureInfo);
        }
        String flowmeterInfo = sensor.getFlowmeterType() + sensor.getFlowmeterNum();
        if (!TextUtils.isEmpty(flowmeterInfo)) {
            tvFlowmeterInfo.setText(flowmeterInfo);
        }
        String temperatureInfo = sensor.getTemperatureType() + sensor.getTemperatureNum();
        if (!TextUtils.isEmpty(temperatureInfo)) {
            tvTemperatureInfo.setText(temperatureInfo);
        }
        String evaporationrateInfo = sensor.getEvaporationRateType() + sensor.getEvaporationRateNum();
        if (!TextUtils.isEmpty(evaporationrateInfo)) {
            tvEquipmentNum.setText(evaporationrateInfo);
        }
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
                saveWord();
                handler.sendEmptyMessage(0);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pdfModel();
//                    }
//                }).start();
                break;
            case R.id.btn_printing:
                ToastHelper.showLongToast("未找到打印设备");
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void pdfModel(){
        PdfDocument document = new PdfDocument();
        // ll_model是一个LinearLayout
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(scroll.getWidth(),scroll.getChildAt(0).getHeight(),1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        scroll.draw(page.getCanvas());
        document.finishPage(page);

        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "/001evaporation/"+parameter.getDeviceId()+"_"+DateUtil.getSystemDate()+"_history.pdf";
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


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            progressBar2.setVisibility(View.GONE);
            tvTip.setVisibility(View.GONE);
            ToastHelper.showLongToast("Pdf报告成功生成，已保存到本地");
            return false;
        }
    });

    private void saveWord(){
        try {
            InputStream templetDocStream = getAssets().open("静态蒸发率检测记录.doc");
            String targetDocPath = Environment.getExternalStorageDirectory() + File.separator + "001evaporation/"+ DateUtil.getSystemDate1()+"_"+parameter.getDeviceId()+"_静态蒸发率检测记录.doc";

            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("$deviceNum$",  null != sensor ?  sensor.getEvaporationRateNum() : "");
            dataMap.put("$tempType$", sensor.getTemperatureType());
            dataMap.put("$tempNum$", sensor.getTemperatureNum());
            dataMap.put("$testDate$", testProcess.getTestStartTime());
            dataMap.put("$testAddress$", parameter.getTestAddress());
            dataMap.put("$pressType$", sensor.getPressureType());
            dataMap.put("$pressNum$", sensor.getPressureNum());
            dataMap.put("$flowType$", sensor.getFlowmeterType());
            dataMap.put("$flowNum$", sensor.getFlowmeterNum());
            dataMap.put("$fillEndDate$", parameter.getLiquidFillingEndDate());

            PoiUtils.writeToDoc(templetDocStream,targetDocPath,dataMap);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){

        }
    }
}
