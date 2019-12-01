package com.ch.view;

import android.content.Context;
import android.view.View;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;

import butterknife.ButterKnife;

public class CommonSearchView extends ViewController<String> {

    public CommonSearchView(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_search;
    }

    @Override
    protected void onCreatedView(View view) {
        initView();
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onBindView(String data) {
        initData(data);

    }

    private void initView() {
    }

    private void initData(String data) {
    }
}

