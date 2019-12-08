package com.ch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import com.ch.evaporationrate.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.constraint_welcome_view)
    ConstraintLayout constraintWelcomeView;
    @BindView(R.id.card_login_view)
    CardView cardLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
//        UserBeanDao dao = BaseApplication.getDaoInstant().getUserBeanDao();
//        UserBean userBean = new UserBean();
//        userBean.setAddress("address");
//        userBean.setName("name");
//        dao.insert(userBean);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fadeOut(constraintWelcomeView);
            }
        }, 1500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fadeIn(cardLoginView);
            }
        }, 1500);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
//        startActivity(new Intent(this, HistoryActivity.class));
        startActivity(new Intent(this, MainPageActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void fadeIn(View view, float startAlpha, float endAlpha, long duration) {
        if (view.getVisibility() == View.VISIBLE) return;
        view.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public void fadeIn(View view) {
        fadeIn(view, 0F, 1F, 500);
        view.setEnabled(true);
    }

    public void fadeOut(View view) {
        if (view.getVisibility() != View.VISIBLE) return;
        view.setEnabled(false);
        Animation animation = new AlphaAnimation(1F, 0F);
        animation.setDuration(500);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }
}
