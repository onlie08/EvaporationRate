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

import com.ch.evaporationrate.R;
import com.ch.view.CommonListView;
import com.ch.view.CommonSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MonitorFragment extends Fragment {
    Unbinder unbinder;

    CommonSearchView commonSearchView;
    CommonListView commonListView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ln_search)
    LinearLayout lnSearch;
    @BindView(R.id.ln_list)
    LinearLayout lnList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_monitor, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }

    private void initView() {
        tvTitle.setText("监控");

        commonSearchView = new CommonSearchView(getContext());
        commonSearchView.attachRoot(lnSearch);

        commonListView = new CommonListView(getContext());
        commonListView.attachRoot(lnList);
    }

    private void initData() {
        commonListView.fillData("");
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
        commonSearchView.detachedRoot();
        commonListView.detachedRoot();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
