package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.bean.ChartBean;
import com.ch.bean.Parameter;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.DateUtil;
import com.ch.utils.RxChartTimerUtil;
import com.ch.view.CommonChartLineViewController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChartFragment extends Fragment {
    Unbinder unbinder;
    CommonChartLineViewController commonChartLineViewController;
    @BindView(R.id.ln_chart)
    LinearLayout lnChart;
    @BindView(R.id.tv_test_medium)
    TextView tvTestMedium;
    @BindView(R.id.tv_flow_total5)
    TextView tvFlowTotal5;
    @BindView(R.id.tv_weight_total)
    TextView tvWeightTotal;
    @BindView(R.id.tv_flow_total4)
    TextView tvFlowTotal4;
    @BindView(R.id.tv_flow_total3)
    TextView tvFlowTotal3;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceId;

    private Parameter parameter;
    private List<BeanRTData> beanRTDataList = new ArrayList<>();
    private BeanRTData curBeanRTData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {
        commonChartLineViewController = new CommonChartLineViewController(getContext());
        commonChartLineViewController.attachRoot(lnChart);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.POSTING)
    public void Event(BeanRTData beanRTData) {
        refreshRtData(beanRTData);
        curBeanRTData = beanRTData;
    }

    private void refreshChart() {
        List<ChartBean> chartBeans1 = new ArrayList<>();
        List<ChartBean> chartBeans2 = new ArrayList<>();
        List<ChartBean> chartBeans3 = new ArrayList<>();
        List<ChartBean> chartBeans4 = new ArrayList<>();
        List<ChartBean> chartBeans5 = new ArrayList<>();
        List<ChartBean> chartBeans6 = new ArrayList<>();
        List<ChartBean> chartBeans7 = new ArrayList<>();
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getInstantFlow());
            chartBeans1.add(chartBean);
        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getEntertemperature());
            chartBeans2.add(chartBean);
        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getEnterpressure());
            chartBeans3.add(chartBean);
        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getSurroundtemperature());
            chartBeans4.add(chartBean);
        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getSurroundpressure());
            chartBeans5.add(chartBean);
        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getSurroundhumidity()*100);
            chartBeans6.add(chartBean);

        }
        for (int i = 0; i < beanRTDataList.size(); i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate(DateUtil.dateFormat(beanRTDataList.get(i).getAcqTime()));
            chartBean.setValue(beanRTDataList.get(i).getSurroundpressure());
//            chartBean.setValue(beanRTDataList.get(i).getAccFlow());
            chartBeans7.add(chartBean);
        }

        commonChartLineViewController.fillData(chartBeans1);
        commonChartLineViewController.loadData(chartBeans1,chartBeans2,chartBeans3,chartBeans4,chartBeans5,chartBeans6,chartBeans7);
    }

    private void refreshRtData(BeanRTData beanRTData) {
        tvFlowTotal5.setText(beanRTData.getInstantFlow()+"");
        tvFlowTotal4.setText(beanRTData.getAccFlow()+"");
        tvFlowTotal3.setText(beanRTData.getInstantQuality()+"");
        tvWeightTotal.setText(beanRTData.getAccQuality()+"");
    }

    private void initData() {
        parameter = DbManage.getInstance().getParamter();
        if(null != parameter){
            tvDeviceId.setText(parameter.getDeviceId());
            tvTestMedium.setText(parameter.getMediumType());
        }

        RxChartTimerUtil.interval(60*1000, new RxChartTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                if(beanRTDataList.size()>60){
                    beanRTDataList.remove(0);
                }
                beanRTDataList.add(curBeanRTData);
                refreshChart();
            }
        });
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
        commonChartLineViewController.detachedRoot();
        unbinder.unbind();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        RxChartTimerUtil.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
