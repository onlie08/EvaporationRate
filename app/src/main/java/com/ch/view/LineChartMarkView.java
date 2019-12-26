package com.ch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

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
    private IAxisValueFormatter xAxisValueFormatter;
    DecimalFormat df = new DecimalFormat("0.00");

    public LineChartMarkView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.layout_markview);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvValue0 = (TextView) findViewById(R.id.tv_value0);
        tvValue1 = (TextView) findViewById(R.id.tv_value1);
        tvValue2 = (TextView) findViewById(R.id.tv_value2);
        tvValue3 = (TextView) findViewById(R.id.tv_value3);
        tvValue4 = (TextView) findViewById(R.id.tv_value4);
        tvValue5 = (TextView) findViewById(R.id.tv_value5);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Chart chart = getChartView();
        if (chart instanceof LineChart) {
            LineData lineData = ((LineChart) chart).getLineData();
            //获取到图表中的所有曲线
            List<ILineDataSet> dataSetList = lineData.getDataSets();
            for (int i = 0; i < dataSetList.size(); i++) {
                LineDataSet dataSet = (LineDataSet) dataSetList.get(i);
                //获取到曲线的所有在Y轴的数据集合，根据当前X轴的位置 来获取对应的Y轴值
                float y = dataSet.getValues().get((int) e.getX()).getY();
                if (i == 0) {
                    tvValue0.setText(dataSet.getLabel() + "：" + y + "L/Min");
                }
                if (i == 1) {
                    tvValue1.setText(dataSet.getLabel() + "：" + y + "℃");
                }
                if (i == 2) {
                    tvValue2.setText(dataSet.getLabel() + "：" + y + "KPa");
                }
                if (i == 3) {
                    tvValue3.setText(dataSet.getLabel() + "：" + y + "℃");
                }
                if (i == 4) {
                    tvValue4.setText(dataSet.getLabel() + "：" + y + "KPa");
                }
                if (i == 5) {
                    tvValue5.setText(dataSet.getLabel() + "：" + y + "%");
                }
            }
            tvDate.setText("时间："+xAxisValueFormatter.getFormattedValue(e.getX(), null));
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
