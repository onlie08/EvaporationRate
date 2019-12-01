package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ch.bean.ChartBean;
import com.ch.evaporationrate.R;
import com.ch.view.CommonChartLineViewController;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        commonChartLineViewController.fillData(chartBeans);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
