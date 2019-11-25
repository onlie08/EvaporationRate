package com.ch.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonBottomView extends ViewController<List<String>> {
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.tv_monitor)
    TextView tvMonitor;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.constraint_bottom)
    ConstraintLayout constraintBottom;

    public CommonBottomView(Context context) {
        super(context);
    }

    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClicked(int pos);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_bottom;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        tvHome.setSelected(true);
    }

    @Override
    protected void onBindView(List<String> data) {
        initData();
    }

    private void initData() {
    }

    @OnClick({R.id.tv_home, R.id.tv_alarm, R.id.tv_monitor, R.id.tv_device, R.id.tv_person})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                setItemClick(0);
                tvHome.setSelected(true);
                tvAlarm.setSelected(false);
                tvMonitor.setSelected(false);
                tvDevice.setSelected(false);
                tvPerson.setSelected(false);
                break;
            case R.id.tv_alarm:
                setItemClick(1);
                tvHome.setSelected(false);
                tvAlarm.setSelected(true);
                tvMonitor.setSelected(false);
                tvDevice.setSelected(false);
                tvPerson.setSelected(false);
                break;
            case R.id.tv_monitor:
                setItemClick(2);
                tvHome.setSelected(false);
                tvAlarm.setSelected(false);
                tvMonitor.setSelected(true);
                tvDevice.setSelected(false);
                tvPerson.setSelected(false);
                break;
            case R.id.tv_device:
                setItemClick(3);
                tvHome.setSelected(false);
                tvAlarm.setSelected(false);
                tvMonitor.setSelected(false);
                tvDevice.setSelected(true);
                tvPerson.setSelected(false);
                break;
            case R.id.tv_person:
                setItemClick(4);
                tvHome.setSelected(false);
                tvAlarm.setSelected(false);
                tvMonitor.setSelected(false);
                tvDevice.setSelected(false);
                tvPerson.setSelected(true);
                break;
        }
    }

    private void setItemClick(int pos){
        if(listener != null){
            listener.onItemClicked(pos);
        }
    }

}
