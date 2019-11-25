package com.ch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ch.base.base.BaseApplication;
import com.ch.bean.UserBean;
import com.ch.bean.UserBeanDao;
import com.ch.evaporationrate.R;;
import com.ch.view.AutoEditTextView;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.tv_login_name)
    AutoEditTextView tvLoginName;
    @BindView(R.id.tv_password)
    AutoEditTextView tvPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mImmersionBar = ImmersionBar.with(this)
                //解决软键盘与底部输入框冲突问题
                .keyboardEnable(true)
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true, 0.2f)
                //状态栏颜色，不写默认透明色
//                .statusBarColor(R.color.colorPrimary)
                //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .fitsSystemWindows(false);
        mImmersionBar.init();  //所有子类都将继承这些相同的属性
    }

    private void initData() {
//        UserBeanDao dao = BaseApplication.getDaoInstant().getUserBeanDao();
//        UserBean userBean = new UserBean();
//        userBean.setId(13413241l);
//        userBean.setType(0);
//        userBean.setAddress("address");
//        userBean.setName("name");
//        userBean.setPrice("100");
//        userBean.setSell_num(3);
//        dao.insert(userBean);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        startActivity(new Intent(this,MainPageActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

}
