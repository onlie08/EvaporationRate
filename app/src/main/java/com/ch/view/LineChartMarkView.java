package com.ch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.ch.bean.ChartBean;
import com.ch.evaporationrate.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by xhu_ww on 2018/6/1.
 * description:
 */
public class LineChartMarkView extends MarkerView {

    private TextView tvDate;
    private TextView tvValue0;
    private TextView tvValue1;
    private TextView tvValue2;
    private TextView tvValue3;
    private TextView tvValue4;
    private TextView tvValue5;
    private TextView tvValue6;

    private List<ChartBean> rData1;
    private List<ChartBean> rData2;
    private List<ChartBean> rData3;
    private List<ChartBean> rData4;
    private List<ChartBean> rData5;
    private List<ChartBean> rData6;
    private List<ChartBean> rData7;

    private IAxisValueFormatter xAxisValueFormatter;
    DecimalFormat df = new DecimalFormat("#.00");

    public LineChartMarkView(Context context, IAxisValueFormatter xAxisValueFormatter,boolean show1, boolean show2, boolean show3, boolean show4, boolean show5, boolean show6, boolean show7,List<ChartBean> data1, List<ChartBean> data2, List<ChartBean> data3, List<ChartBean> data4, List<ChartBean> data5, List<ChartBean> data6, List<ChartBean> data7) {
        super(context, R.layout.layout_markview);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvValue0 = (TextView) findViewById(R.id.tv_value0);
        tvValue1 = (TextView) findViewById(R.id.tv_value1);
        tvValue2 = (TextView) findViewById(R.id.tv_value2);
        tvValue3 = (TextView) findViewById(R.id.tv_value3);
        tvValue4 = (TextView) findViewById(R.id.tv_value4);
        tvValue5 = (TextView) findViewById(R.id.tv_value5);
        tvValue6 = (TextView) findViewById(R.id.tv_value6);
        if(!show1){
            tvValue0.setVisibility(GONE);
        }
        if(!show2){
            tvValue1.setVisibility(GONE);
        }
        if(!show3){
            tvValue2.setVisibility(GONE);
        }
        if(!show4){
            tvValue3.setVisibility(GONE);
        }
        if(!show5){
            tvValue4.setVisibility(GONE);
        }
        if(!show6){
            tvValue5.setVisibility(GONE);
        }
        if(!show7){
            tvValue6.setVisibility(GONE);
        }
        rData1 = data1;
        rData2 = data2;
        rData3 = data3;
        rData4 = data4;
        rData5 = data5;
        rData6 = data6;
        rData7 = data7;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Chart chart = getChartView();
        if (chart instanceof LineChart) {
            LineData lineData = ((LineChart) chart).getLineData();
            //获取到图表中的所有曲线
            List<ILineDataSet> dataSetList = lineData.getDataSets();
            DecimalFormat df = new DecimalFormat("#.00");
            for (int i = 0; i < dataSetList.size(); i++) {
                LineDataSet dataSet = (LineDataSet) dataSetList.get(i);
                //获取到曲线的所有在Y轴的数据集合，根据当前X轴的位置 来获取对应的Y轴值
                float y = dataSet.getValues().get((int) e.getX()).getY();

                if ("流量计".equals(dataSet.getLabel())) {
                    tvValue0.setText(dataSet.getLabel() + "：" + df.format(rData1.get((int) e.getX()).getValue()) + "L/Min");
                }
                if ("入口温度".equals(dataSet.getLabel())) {
                    tvValue1.setText(dataSet.getLabel() + "：" + df.format(rData2.get((int) e.getX()).getValue()) + "℃");
                }
                if ("入口压力".equals(dataSet.getLabel())) {
                    tvValue2.setText(dataSet.getLabel() + "：" + df.format(rData3.get((int) e.getX()).getValue()) + "KPa");
                }
                if ("环境温度".equals(dataSet.getLabel())) {
                    tvValue3.setText(dataSet.getLabel() + "：" + df.format(rData4.get((int) e.getX()).getValue()) + "℃");
                }
                if ("大气压".equals(dataSet.getLabel())) {
                    tvValue4.setText(dataSet.getLabel() + "：" + df.format(rData5.get((int) e.getX()).getValue()) + "KPa");
                }
                if ("湿度".equals(dataSet.getLabel())) {
                    tvValue5.setText(dataSet.getLabel() + "：" + df.format(rData6.get((int) e.getX()).getValue()) + "%");
                }
                if ("累计流量".equals(dataSet.getLabel())) {
                    tvValue6.setText(dataSet.getLabel() + "：" + df.format(rData7.get((int) e.getX()).getValue()) + "L");
                }
            }
            tvDate.setText(xAxisValueFormatter.getFormattedValue(e.getX(), null));
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
