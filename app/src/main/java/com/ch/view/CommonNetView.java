package com.ch.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;
import com.ch.utils.PhoneNetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonNetView extends ViewController<String> {
    @BindView(R.id.img_net)
    ImageView imgNet;

    public CommonNetView(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_net;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onDestoryView(View view){

    }

    @Override
    protected void onBindView(String data) {
        checkAndStart();
    }

    private void refresh(int pos){
        switch (pos){
            case 0:
                imgNet.setImageResource(R.mipmap.common_net_zero);
                break;
            case 1:
                imgNet.setImageResource(R.mipmap.common_net_one);
                break;
            case 2:
                imgNet.setImageResource(R.mipmap.common_net_two);
                break;
            case 3:
                imgNet.setImageResource(R.mipmap.common_net_thire);
                break;
            case 4:
                imgNet.setImageResource(R.mipmap.common_net_four);
                break;
            case 5:
                imgNet.setImageResource(R.mipmap.common_net_five);
                break;
        }

    }

    public void checkAndStart(){
        getSignalLevel();
    }

    private void getSignalLevel(){
        PhoneNetUtil.getInstance(getContext()).setPhoneNetLevelListener(new PhoneNetUtil.PhoneNetLevelListener() {
            @Override
            public void onNetLevel(int level) {
                Log.i("caohai","信号格数："+level);
                refresh(level);
            }
        });
    }
}
