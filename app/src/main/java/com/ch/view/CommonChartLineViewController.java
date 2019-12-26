package com.ch.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
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

public class CommonChartLineViewController extends ViewController<List<ChartBean>> {

    @BindView(R.id.common_chart_line)
    LineChart mChart;

    private String TAG = CommonChartLineViewController.class.getSimpleName();
    private LineChartManager lineChartManager1;
    private int chartType = 0;

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
        if(data1.size() == 0){
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

    public void loadData(List<ChartBean> data1,List<ChartBean> data2,List<ChartBean> data3,List<ChartBean> data4,List<ChartBean> data5,List<ChartBean> data6,List<ChartBean> data7) {
        lineChartManager1.showLineChart0(data1,data2,data3,data4,data5,data6,data7, "大气压", getContext().getResources().getColor(R.color.blue));
        //设置曲线填充色 以及 MarkerView
//        Drawable drawable = getContext().getResources().getDrawable(R.drawable.fade_blue);
//        lineChartManager1.setChartFillDrawable(drawable);
        lineChartManager1.setMarkerView(getContext());
    }

}
