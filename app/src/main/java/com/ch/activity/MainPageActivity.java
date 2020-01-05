package com.ch.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ch.evaporationrate.R;
import com.ch.fragment.ChartFragment;
import com.ch.fragment.DateFragment;
import com.ch.fragment.DeviceFragment;
import com.ch.fragment.ParameterFragment;
import com.ch.fragment.ProcessFragment;
import com.ch.utils.AppPreferences;
import com.ch.utils.BrightnessTools;
import com.ch.view.CommonBottomView;
import com.ch.view.CommonTitleView;
import com.gyf.barlibrary.ImmersionBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

;

public class MainPageActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    LinearLayout navigation;
    @BindView(R.id.framePage)
    FrameLayout framePage;
    @BindView(R.id.ln_title)
    LinearLayout lnTitle;


    CommonBottomView commonBottomView;
    CommonTitleView commonTitleView;
    private int lastIndex;
    List<Fragment> mFragments;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int bright = (int)AppPreferences.instance().get("bright",255);
        BrightnessTools.setBrightness(this,bright);
        initView();
        initData();
    }

    private void initView() {

        commonBottomView = new CommonBottomView(this);
        commonBottomView.attachRoot(navigation);
        commonBottomView.setListener(new CommonBottomView.ItemClickListener() {
            @Override
            public void onItemClicked(int pos) {
                setFragmentPosition(pos);
            }
        });

        commonTitleView = new CommonTitleView(this);
        commonTitleView.attachRoot(lnTitle);
        commonTitleView.setListener(new CommonTitleView.ItemClickListener() {
            @Override
            public void onItemClicked(int pos) {
                setFragmentPosition(pos);
            }
        });

        String dbDir=android.os.Environment.getExternalStorageDirectory().toString();
        dbDir += "/001evaporation";//数据库所在目录
        //判断目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        if(!dirFile.exists())
            dirFile.mkdirs();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new ProcessFragment());
        mFragments.add(new ParameterFragment());
        mFragments.add(new ChartFragment());
        mFragments.add(new DeviceFragment());
        mFragments.add(new DateFragment());
        // 初始化展示MessageFragment
        setFragmentPosition(0);

    }

    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.framePage, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();

        switch (position){
            case 0:
                commonTitleView.fillData("试验过程");
                break;
            case 1:
                commonTitleView.fillData("试验参数");
                break;
            case 2:
                commonTitleView.fillData("曲线");
                break;
            case 3:
                commonTitleView.fillData("设备状态");
                break;
            case 4:
                commonTitleView.fillData("数据记录");
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonBottomView.detachedRoot();
        commonTitleView.detachedRoot();
    }
}
