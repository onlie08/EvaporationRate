package com.ch.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ch.adapter.HistroyListAdapter;
import com.ch.evaporationrate.R;
import com.deadline.statebutton.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {
    HistroyListAdapter histroyListAdapter;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_export)
    StateButton btnExport;
    @BindView(R.id.btn_printing)
    StateButton btnPrinting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_new);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("CYZU155612" + i);
        }
        histroyListAdapter = new HistroyListAdapter(strings);
        recyclerHistory.setAdapter(histroyListAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @OnClick({R.id.btn_export, R.id.btn_printing, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_export:
                break;
            case R.id.btn_printing:
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
