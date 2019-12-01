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
    @BindView(R.id.view_mine)
    View viewMine;
    @BindView(R.id.view_shanghai)
    TextView viewShanghai;
    @BindView(R.id.cl_shanghai)
    ConstraintLayout clShanghai;
    @BindView(R.id.view_shenzhen)
    View viewShenzhen;
    @BindView(R.id.cl_shenzhen)
    ConstraintLayout clShenzhen;
    @BindView(R.id.view_gem)
    View viewGem;
    @BindView(R.id.cl_gem)
    ConstraintLayout clGem;
    @BindView(R.id.tv_chart_type)
    TextView tvChartType;

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
    protected void onBindView(List<ChartBean> data) {
        if(data.size() == 0){
            ToastHelper.showToast("暂无有效数据");
            return;
        }
        lineChartManager1 = new LineChartManager(mChart);
        mChart.clear();
        initData(data);
        mChart.invalidate();
    }

    private void initData(List<ChartBean> data) {
        switch (getChartType()){
            case 0:
                tvChartType.setText("空气温度");
                lineChartManager1.showLineChart0(data, "空气温度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"空气温度");
                break;
            case 1:
                tvChartType.setText("空气湿度");
                lineChartManager1.showLineChart1(data, "空气湿度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"空气湿度");
                break;
            case 2:
                tvChartType.setText("光照强度");
                lineChartManager1.showLineChart2(data, "光照强度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"光照强度");
                break;
            case 3:
                tvChartType.setText("二氧化碳浓度");
                lineChartManager1.showLineChart3(data, "二氧化碳浓度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"二氧化碳浓度");
                break;
            case 4:
                tvChartType.setText("二氧化硫浓度");
                lineChartManager1.showLineChart4(data, "二氧化硫浓度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"二氧化硫浓度");
                break;
            case 5:
                tvChartType.setText("氨气浓度");
                lineChartManager1.showLineChart5(data, "氨气浓度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"氨气浓度");
                break;
            case 6:
                tvChartType.setText("硫化氢");
                lineChartManager1.showLineChart6(data, "硫化氢", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"硫化氢");
                break;
            case 7:
                tvChartType.setText("甲烷浓度");
                lineChartManager1.showLineChart7(data, "甲烷浓度", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"甲烷浓度");
                break;
            case 8:
                tvChartType.setText("体温");
                lineChartManager1.showLineChart7(data, "体温", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"体温");
                break;
            case 9:
                tvChartType.setText("运动量");
                lineChartManager1.showLineChart7(data, "运动量", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"运动量");
                break;
            case 10:
                tvChartType.setText("脉搏");
                lineChartManager1.showLineChart7(data, "脉搏", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"脉搏");
                break;
            case 11:
                tvChartType.setText("日采食量");
                lineChartManager1.showLineChart7(data, "日采食量", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"日采食量");
                break;
            case 12:
                tvChartType.setText("日粮比例");
                lineChartManager1.showLineChart7(data, "日粮比例", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"日粮比例");
                break;
            case 13:
                tvChartType.setText("日饮水量");
                lineChartManager1.showLineChart7(data, "日饮水量", getContext().getResources().getColor(R.color.blue));
                Log.i(TAG,"日饮水量");
                break;
        }


        //设置曲线填充色 以及 MarkerView
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.fade_blue);
        lineChartManager1.setChartFillDrawable(drawable);
        lineChartManager1.setMarkerView(getContext());
    }

}
