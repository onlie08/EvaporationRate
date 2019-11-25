package com.ch.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ch.adapter.CommonListAdapter;
import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonListView extends ViewController<String> {
    CommonListAdapter commonListAdapter;
    @BindView(R.id.list_common)
    RecyclerView listCommon;

    public CommonListView(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_list;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
        initView();
    }

    @Override
    protected void onBindView(String data) {
        initData(data);

    }

    private void initView() {
        listCommon.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData(String data) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("CYZU155612" + i);
        }
        commonListAdapter = new CommonListAdapter(strings);
        listCommon.setAdapter(commonListAdapter);
    }
}
