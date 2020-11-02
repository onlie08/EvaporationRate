package com.ch.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonBatteryView extends ViewController<String> {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.img_battery)
    ImageView imgBattery;
    @BindView(R.id.img_charge)
    ImageView imgCharge;
    @BindView(R.id.tv_battery_left)
    TextView tvBatteryLeft;

    public CommonBatteryView(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_battery;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onDestoryView(View view) {

    }

    @Override
    protected void onBindView(String data) {
        getContext().registerReceiver(mBatteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }

    private void refresh(int left) {
        if(left > 10){
            imgBattery.setImageResource(R.mipmap.common_battery_normal);
        }else {
            imgBattery.setImageResource(R.mipmap.common_battery_low);
        }
        tvBatteryLeft.setText(left+"");

    }

    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int level=arg1.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int scale=arg1.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int levelPercent = (int)(((float)level / scale) * 100);
            refresh(levelPercent);
            Log.i(TAG, "level：" + level + "scale：" + scale + "levelPercent：" + levelPercent);

            int status = arg1.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            refresh(isCharging);
        }
    };

    private void refresh(boolean isCharging) {
        if(isCharging){
            imgCharge.setVisibility(View.VISIBLE);
        }else {
            imgCharge.setVisibility(View.GONE);
        }

    }
}
