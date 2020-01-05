package com.ch.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ch.base.base.ViewController;
import com.ch.bean.ChartBean;
import com.ch.evaporationrate.R;
import com.ch.utils.ToastHelper;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonChartLineViewController extends ViewController<List<ChartBean>> {

    @BindView(R.id.common_chart_line)
    LineChart mChart;
    @BindView(R.id.tv_chart_type1)
    TextView tvChartType1;
    @BindView(R.id.tv_chart_type2)
    TextView tvChartType2;
    @BindView(R.id.tv_chart_type3)
    TextView tvChartType3;
    @BindView(R.id.tv_chart_type4)
    TextView tvChartType4;
    @BindView(R.id.tv_chart_type5)
    TextView tvChartType5;
    @BindView(R.id.tv_chart_type6)
    TextView tvChartType6;
    @BindView(R.id.tv_chart_type7)
    TextView tvChartType7;

    private String TAG = CommonChartLineViewController.class.getSimpleName();
    private LineChartManager lineChartManager1;
    private int chartType = 0;
    private List<ChartBean> mData1;
    private List<ChartBean> mData2;
    private List<ChartBean> mData3;
    private List<ChartBean> mData4;
    private List<ChartBean> mData5;
    private List<ChartBean> mData6;
    private List<ChartBean> mData7;
    private List<ChartBean> rData1;
    private List<ChartBean> rData2;
    private List<ChartBean> rData3;
    private List<ChartBean> rData4;
    private List<ChartBean> rData5;
    private List<ChartBean> rData6;
    private List<ChartBean> rData7;
    private boolean mShow1 = true;
    private boolean mShow2= true;
    private boolean mShow3= true;
    private boolean mShow4= true;
    private boolean mShow5= true;
    private boolean mShow6= true;
    private boolean mShow7= true;

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public CommonChartLineViewController(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_chart_line;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onBindView(List<ChartBean> data1) {
        if (data1.size() == 0) {
            ToastHelper.showToast("暂无有效数据");
            return;
        }
        lineChartManager1 = new LineChartManager(mChart);
        mChart.clear();
        initData(data1);
        mChart.invalidate();
    }

    private void initData(List<ChartBean> data1) {

    }

    public void loadData(List<ChartBean> data1, List<ChartBean> data2, List<ChartBean> data3, List<ChartBean> data4, List<ChartBean> data5, List<ChartBean> data6, List<ChartBean> data7) {
        mData1 = data1;
        mData2 = data2;
        mData3 = data3;
        mData4 = data4;
        mData5 = data5;
        mData6 = data6;
        mData7 = data7;
        refreshChart();
    }

    @OnClick({R.id.tv_chart_type1, R.id.tv_chart_type2, R.id.tv_chart_type3, R.id.tv_chart_type4, R.id.tv_chart_type5, R.id.tv_chart_type6, R.id.tv_chart_type7})
    public void onViewClicked(View view) {
        if(null == lineChartManager1){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_chart_type1:
                if(tvChartType1.isSelected()){
                    tvChartType1.setSelected(false);
                    mShow1 = true;
                }else {
                    tvChartType1.setSelected(true);
                    mShow1 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type2:
                if(tvChartType2.isSelected()){
                    tvChartType2.setSelected(false);
                    mShow2 = true;
                }else {
                    tvChartType2.setSelected(true);
                    mShow2 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type3:
                if(tvChartType3.isSelected()){
                    tvChartType3.setSelected(false);
                    mShow3 = true;
                }else {
                    tvChartType3.setSelected(true);
                    mShow3 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type4:
                if(tvChartType4.isSelected()){
                    tvChartType4.setSelected(false);
                    mShow4 = true;
                }else {
                    tvChartType4.setSelected(true);
                    mShow4 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type5:
                if(tvChartType5.isSelected()){
                    tvChartType5.setSelected(false);
                    mShow5 = true;
                }else {
                    tvChartType5.setSelected(true);
                    mShow5 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type6:
                if(tvChartType6.isSelected()){
                    tvChartType6.setSelected(false);
                    mShow6 = true;
                }else {
                    tvChartType6.setSelected(true);
                    mShow6 = false;
                }
                refreshChart();
                break;
            case R.id.tv_chart_type7:
                if(tvChartType7.isSelected()){
                    tvChartType7.setSelected(false);
                    mShow7 = true;
                }else {
                    tvChartType7.setSelected(true);
                    mShow7 = false;
                }
                refreshChart();
                break;
        }
    }

    private void refreshChart(){
        mChart.clear();
        if(null == lineChartManager1){
            return;
        }
        lineChartManager1.showLineChart0(mData1, mData2,mData3, mData4, mData5, mData6, mData7, mShow1, mShow2, mShow3, mShow4, mShow5, mShow6, mShow7);
        lineChartManager1.setMarkerView(getContext(), mShow1, mShow2, mShow3, mShow4, mShow5, mShow6, mShow7,rData1, rData2, rData3, rData4, rData5, rData6, rData7);
    }

    public void setRealVaule(List<ChartBean> data1, List<ChartBean> data2, List<ChartBean> data3, List<ChartBean> data4, List<ChartBean> data5, List<ChartBean> data6, List<ChartBean> data7){
        rData1 = data1;
        rData2 = data2;
        rData3 = data3;
        rData4 = data4;
        rData5 = data5;
        rData6 = data6;
        rData7 = data7;
    }

    public void reset() {
        mChart.clear();
        mChart.invalidate();
    }
}
