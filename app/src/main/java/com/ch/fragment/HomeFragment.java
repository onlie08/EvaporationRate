package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ch.bean.ChartBean;
import com.ch.evaporationrate.R;;
import com.ch.utils.ToastHelper;
import com.ch.view.CommonChartLineViewController;
import com.ch.view.CommonSpinnerViewController;
import com.ch.view.CommonUserFliterView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.ln_spinner_date)
    LinearLayout lnSpinnerDate;
    @BindView(R.id.ln_chart)
    LinearLayout lnChart;
    @BindView(R.id.ln_spinner_monitor)
    LinearLayout lnSpinnerMonitor;
    @BindView(R.id.ln_chart_monitor)
    LinearLayout lnChartMonitor;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.img_device_total)
    ImageView imgDeviceTotal;
    @BindView(R.id.img_device_normal)
    ImageView imgDeviceNormal;
    @BindView(R.id.img_device_onlien)
    ImageView imgDeviceOnlien;
    @BindView(R.id.img_device_offline)
    ImageView imgDeviceOffline;
    @BindView(R.id.tv_device_total)
    TextView tvDeviceTotal;
    @BindView(R.id.tv_device_normal)
    TextView tvDeviceNormal;
    @BindView(R.id.tv_device_onlien)
    TextView tvDeviceOnlien;
    @BindView(R.id.tv_device_offline)
    TextView tvDeviceOffline;

    private CommonChartLineViewController alarmChart;
    private CommonSpinnerViewController alarmSpinner;

    private CommonChartLineViewController monitorChart;
    private CommonSpinnerViewController monitorSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {

        alarmChart = new CommonChartLineViewController(getContext());
        alarmChart.attachRoot(lnChart);

        monitorChart = new CommonChartLineViewController(getContext());
        monitorChart.attachRoot(lnChartMonitor);

        alarmSpinner = new CommonSpinnerViewController(getContext());
        alarmSpinner.attachRoot(lnSpinnerDate);
        alarmSpinner.setListener(new CommonSpinnerViewController.SpinnerChangeListener() {
            @Override
            public void spinnerChoose(String item, int pos) {
                ToastHelper.showToast(item);
            }
        });

        monitorSpinner = new CommonSpinnerViewController(getContext());
        monitorSpinner.attachRoot(lnSpinnerMonitor);
        monitorSpinner.setListener(new CommonSpinnerViewController.SpinnerChangeListener() {
            @Override
            public void spinnerChoose(String item, int pos) {
                ToastHelper.showToast(item);
            }
        });
    }

    private void initData() {
        List<ChartBean> chartBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setDate("20192019110" + i);
            chartBean.setValue(10 + Math.random() * 10);
            chartBeans.add(chartBean);
        }

        List<String> strings = new ArrayList<>();
        strings.add("最近7天  ");
        strings.add("最近一个月");
        strings.add("最近三个月");

        alarmChart.fillData(chartBeans);
        monitorChart.fillData(chartBeans);
        alarmSpinner.fillData(strings);
        monitorSpinner.fillData(strings);
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
        alarmChart.detachedRoot();
        alarmSpinner.detachedRoot();
        monitorChart.detachedRoot();
        monitorSpinner.detachedRoot();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @OnClick({R.id.img_user, R.id.img_message, R.id.img_device_total, R.id.img_device_normal, R.id.img_device_onlien, R.id.img_device_offline, R.id.tv_device_total, R.id.tv_device_normal, R.id.tv_device_onlien, R.id.tv_device_offline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_user:
                CommonUserFliterView commonUserFliterView = new CommonUserFliterView(getActivity());
                commonUserFliterView.showUserFliter();
                break;
            case R.id.img_message:
                break;
            case R.id.img_device_total:
                break;
            case R.id.img_device_normal:
                break;
            case R.id.img_device_onlien:
                break;
            case R.id.img_device_offline:
                break;
            case R.id.tv_device_total:
                break;
            case R.id.tv_device_normal:
                break;
            case R.id.tv_device_onlien:
                break;
            case R.id.tv_device_offline:
                break;
        }
    }
}
