package com.ch.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.ch.bean.ChartBean;
import com.ch.bean.CompositeIndexBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by loptop on 2017/6/2.
 */
public class LineChartManager {

    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYAxis;           //右侧Y轴 自定义XY轴值
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    public LineChartManager(LineChart lineChart) {
        this.lineChart = lineChart;
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();

        initChart(lineChart);
    }

    public void clear(){
        lineChart.clear();
    }
    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
//        lineChart.setDragEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        //是否有触摸事件
//        lineChart.setTouchEnabled(true);

        //设置XY轴动画效果
//        lineChart.animateY(500);
//        lineChart.animateX(500);
        Description description = new Description();
//        description.setText("需要展示的内容");
        description.setEnabled(false);
        lineChart.setDescription(description);


        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYAxis = lineChart.getAxisRight();

        xAxis.setDrawGridLines(false);
        rightYAxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(true);
        //设置Y轴网格线为虚线
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYAxis.setEnabled(false);

        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(50f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        //是否显示
        legend.setEnabled(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(3f);

        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }


    /**
     * 展示一条曲线 默认x轴
     *
     * @param yData    y轴的数据
     * @param lineName 曲线名称
     * @param color    曲线颜色
     */
    public void showOneLineChart(List<Float> yData, String lineName, int color) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < yData.size(); i++) {
            entries.add(new Entry(yData.get(i), yData.get(i)));
        }

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, lineName);
        // CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);

        LineData data = new LineData();
        data.addDataSet(lineDataSet);
        lineChart.setData(data);
    }

    /**
     * 注意 集合的长度一致，在此未做处理
     *
     * @param yDataList List<Integer> 代表一条曲线的数据  yDataList.size 代表曲线的条数
     * @param lineNames 曲线名称
     * @param colors    曲线颜色
     */
    public void showMultiNormalLineChart(List<List<Float>> yDataList, List<String> lineNames, List<Integer> colors) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < yDataList.size(); i++) {
            ArrayList<Entry> entries = new ArrayList<>();

            for (int j = 0; j < yDataList.get(i).size(); j++) {
                entries.add(new Entry(yDataList.get(i).get(j), yDataList.get(i).get(j)));
            }
            LineDataSet lineDataSet = new LineDataSet(entries, lineNames.get(i));
            initLineDataSet(lineDataSet, colors.get(i), LineDataSet.Mode.CUBIC_BEZIER);
            dataSets.add(lineDataSet);
        }
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
    }

    /**
     * 设置X轴的显示值
     *
     * @param min        x轴最小值
     * @param max        x轴最大值
     * @param labelCount x轴的分割数量
     */
    public void setXAxisData(float min, float max, int labelCount) {
        xAxis.setAxisMinimum(min);
        xAxis.setAxisMaximum(max);
        xAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    /**
     * 自定义的 X轴显示内容
     *
     * @param xAxisStr
     * @param labelCount x轴的分割数量
     */
    public void setXAxisData(final List<String> xAxisStr, int labelCount) {
        xAxis.setLabelCount(labelCount, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisStr.get((int) value % xAxisStr.size());
            }
        });
        lineChart.invalidate();
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxisData(float max, float min, int labelCount) {
        leftYAxis.setAxisMaximum(max);
        leftYAxis.setAxisMinimum(min);
        leftYAxis.setLabelCount(labelCount, false);

        rightYAxis.setAxisMaximum(max);
        rightYAxis.setAxisMinimum(min);
        rightYAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    /**
     * 自定义的 y轴显示内容
     *
     * @param yAxisStr
     * @param labelCount y轴的分割数量
     */
    public void setYAxisData(final List<String> yAxisStr, int labelCount) {
        xAxis.setLabelCount(labelCount, false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return yAxisStr.get((int) value % yAxisStr.size());
            }
        });
        lineChart.invalidate();
    }


    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHighLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine highLimit = new LimitLine(high, name);
        highLimit.setLineWidth(2f);
        highLimit.setTextSize(10f);
        highLimit.setLineColor(color);
        highLimit.setTextColor(color);
        leftYAxis.addLimitLine(highLimit);
        lineChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(float low, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine lowLimit = new LimitLine(low, name);
        lowLimit.setLineWidth(2f);
        lowLimit.setTextSize(10f);
        lowLimit.setLineColor(color);
        lowLimit.setTextColor(color);
        leftYAxis.addLimitLine(lowLimit);
        lineChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }


    /*****************以下方法无法通用，根据自己数据的不同进行相应的处理********************/
    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart1(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(3, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "%";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "%";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    /**
     * 添加曲线
     */
    public void addLine(List<CompositeIndexBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            CompositeIndexBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        lineChart.getLineData().addDataSet(lineDataSet);
        lineChart.invalidate();
    }

    /**
     * 重置某条曲线 position 从 0 开始
     */
    public void resetLine(int position, List<CompositeIndexBean> dataList, String name, int color) {
        LineData lineData = lineChart.getData();
        List<ILineDataSet> list = lineData.getDataSets();
        if (list.size() <= position) {
            return;
        }

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            CompositeIndexBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getRate());
            entries.add(entry);
        }

        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);

        lineData.getDataSets().set(position, lineDataSet);
        lineChart.invalidate();
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView(Context context,boolean show1, boolean show2, boolean show3, boolean show4, boolean show5, boolean show6, boolean show7,List<ChartBean> data1, List<ChartBean> data2, List<ChartBean> data3, List<ChartBean> data4, List<ChartBean> data5, List<ChartBean> data6, List<ChartBean> data7) {
        LineChartMarkView mv = new LineChartMarkView(context, xAxis.getValueFormatter(),show1, show2, show3, show4, show5, show6, show7,data1, data2, data3, data4, data5, data6, data7);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    public void showLineChart2(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(3, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "lx";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "lx";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart3(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart4(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart5(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart6(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart7(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    public void showLineChart0(final List<ChartBean> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartBean data = dataList.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(5,tradeDate.length());
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(true);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value  + "℃ ";
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        //LINEAR 折线图  CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        //线条自定义内容 放在这里
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "℃";
            }
        });

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }

    /**
     * 展示一条曲线 默认x轴
     *
     * @param yData1    y轴的数据
     * @param lineName 曲线名称
     * @param color    曲线颜色
     */
    public void showOneLineChart(List<Float> yData1,List<Float> yData2,List<Float> yData3,List<Float> yData4,List<Float> yData5,List<Float> yData6,List<Float> yData7, String lineName, int color) {
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        ArrayList<Entry> entries3 = new ArrayList<>();
        ArrayList<Entry> entries4 = new ArrayList<>();
        ArrayList<Entry> entries5 = new ArrayList<>();
        ArrayList<Entry> entries6 = new ArrayList<>();
        ArrayList<Entry> entries7 = new ArrayList<>();

        for (int i = 0; i < yData1.size(); i++) {
            entries1.add(new Entry(yData1.get(i), yData1.get(i)));
        }

        for (int i = 0; i < yData2.size(); i++) {
            entries2.add(new Entry(yData2.get(i), yData2.get(i)));
        }

        for (int i = 0; i < yData3.size(); i++) {
            entries3.add(new Entry(yData3.get(i), yData3.get(i)));
        }

        for (int i = 0; i < yData4.size(); i++) {
            entries4.add(new Entry(yData4.get(i), yData4.get(i)));
        }

        for (int i = 0; i < yData5.size(); i++) {
            entries5.add(new Entry(yData5.get(i), yData5.get(i)));
        }

        for (int i = 0; i < yData6.size(); i++) {
            entries6.add(new Entry(yData6.get(i), yData6.get(i)));
        }

        for (int i = 0; i < yData7.size(); i++) {
            entries7.add(new Entry(yData7.get(i), yData7.get(i)));
        }

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet1 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet2 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet3 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet4 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet5 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet6 = new LineDataSet(entries1, lineName);
        LineDataSet lineDataSet7 = new LineDataSet(entries1, lineName);
        // CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet1, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet2, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet3, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet4, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet5, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet6, color, LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet7, color, LineDataSet.Mode.CUBIC_BEZIER);

        LineData data = new LineData();
        data.addDataSet(lineDataSet1);
        data.addDataSet(lineDataSet2);
        data.addDataSet(lineDataSet3);
        data.addDataSet(lineDataSet4);
        data.addDataSet(lineDataSet5);
        data.addDataSet(lineDataSet6);
        data.addDataSet(lineDataSet7);
        lineChart.setData(data);
    }

    public void showLineChart0(final List<ChartBean> yData1,List<ChartBean> yData2,List<ChartBean> yData3,List<ChartBean> yData4,List<ChartBean> yData5,List<ChartBean> yData6,List<ChartBean> yData7,boolean show1, boolean show2, boolean show3, boolean show4, boolean show5, boolean show6, boolean show7) {
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        ArrayList<Entry> entries3 = new ArrayList<>();
        ArrayList<Entry> entries4 = new ArrayList<>();
        ArrayList<Entry> entries5 = new ArrayList<>();
        ArrayList<Entry> entries6 = new ArrayList<>();
        ArrayList<Entry> entries7 = new ArrayList<>();
        for (int i = 0; i < yData1.size(); i++) {
            ChartBean data = yData1.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries1.add(entry);
        }
        for (int i = 0; i < yData2.size(); i++) {
            ChartBean data = yData2.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries2.add(entry);
        }
        for (int i = 0; i < yData3.size(); i++) {
            ChartBean data = yData3.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries3.add(entry);
        }
        for (int i = 0; i < yData4.size(); i++) {
            ChartBean data = yData4.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries4.add(entry);
        }
        for (int i = 0; i < yData5.size(); i++) {
            ChartBean data = yData5.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries5.add(entry);
        }
        for (int i = 0; i < yData6.size(); i++) {
            ChartBean data = yData6.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries6.add(entry);
        }
        for (int i = 0; i < yData7.size(); i++) {
            ChartBean data = yData7.get(i);
            Entry entry = new Entry(i, (float) data.getValue());
            entries7.add(entry);
        }

        /******根据需求的不同 在此在次设置X Y轴的显示内容******/
        xAxis.setLabelCount(4, false);
        //设置是否绘制刻度
        xAxis.setDrawScale(false);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(16f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(yData1.isEmpty()){
                    return "";
                }
                String tradeDate = yData1.get((int) value % yData1.size()).getDate();
//                return DateUtil.formatDateToMD(tradeDate);
                return tradeDate.substring(0,tradeDate.length());
//                return "";
            }
        });

        leftYAxis.setLabelCount(8);
        leftYAxis.setDrawScale(false);
        leftYAxis.setDrawZeroLine(true); // draw a zero line
        leftYAxis.setZeroLineColor(Color.GRAY);
        leftYAxis.setZeroLineWidth(1f);
        leftYAxis.setAxisLineWidth(1f);
        leftYAxis.setAxisLineColor(Color.GRAY);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return value  + " ";
                return "";
            }
        });

        LineDataSet lineDataSet1 = new LineDataSet(entries1, "流量计");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "入口温度");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "入口压力");
        LineDataSet lineDataSet4 = new LineDataSet(entries4, "环境温度");
        LineDataSet lineDataSet5 = new LineDataSet(entries5, "大气压");
        LineDataSet lineDataSet6 = new LineDataSet(entries6, "湿度");
        LineDataSet lineDataSet7 = new LineDataSet(entries7, "累计流量");

        // CUBIC_BEZIER 圆滑曲线
        initLineDataSet(lineDataSet1, Color.parseColor("#3355ff"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet2, Color.parseColor("#17e5c3"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet3, Color.parseColor("#ffb2ff"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet4, Color.parseColor("#aaff00"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet5, Color.parseColor("#ffd400"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet6, Color.parseColor("#00d5ff"), LineDataSet.Mode.CUBIC_BEZIER);
        initLineDataSet(lineDataSet7, Color.parseColor("#f03200"), LineDataSet.Mode.CUBIC_BEZIER);

        //线条自定义内容 放在这里
        lineDataSet1.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat(".00");
                return value + "℃";
            }
        });

        LineData data = new LineData();
        if(show1){
            data.addDataSet(lineDataSet1);
        }
        if(show2){
            data.addDataSet(lineDataSet2);
        }
        if(show3){
            data.addDataSet(lineDataSet3);
        }
        if(show4){
            data.addDataSet(lineDataSet4);
        }
        if(show5){
            data.addDataSet(lineDataSet5);
        }
        if(show6){
            data.addDataSet(lineDataSet6);
        }
        if(show7){
            data.addDataSet(lineDataSet7);
        }
        lineChart.setData(data);
    }
}
