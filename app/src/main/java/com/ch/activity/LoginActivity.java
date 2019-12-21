package com.ch.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ch.bean.User;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.AppPreferences;
import com.ch.utils.ToastHelper;
import com.eftimoff.androipathview.PathView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.cb_rember)
    CheckBox cbRember;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.pathview)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestPermission();
        initView();
        initData();
    }


    private void initView() {
        pathView.setFillAfter(true);
        pathView.useNaturalColors();

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
        pathView.getPathAnimator().delay(100).duration(0).listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
            @Override
            public void onAnimationEnd() {
//                jump();
            }
        }).start();

    }

    private void initData() {
        String username = (String) AppPreferences.instance().get("username", "");
        String pwd = (String) AppPreferences.instance().get("pwd", "");
        boolean rember = (boolean) AppPreferences.instance().get("rember", false);
        editName.setText(username);
        editPwd.setText(pwd);
        cbRember.setChecked(rember);

        boolean dbinit = (boolean) AppPreferences.instance().get("dbInit", false);
//        boolean dbinit = false;
        if(!dbinit){
            AppPreferences.instance().put("dbInit", true);
            User user = new User();
            user.setName("superadmin");
            user.setPassword("123456");
            user.setRole(0);
            DbManage.getInstance().saveUser(user);

            User user1 = new User();
            user1.setName("admin");
            user1.setPassword("123456");
            user1.setRole(1);
            DbManage.getInstance().saveUser(user1);

            User user2 = new User();
            user2.setName("root");
            user2.setPassword("123456");
            user2.setRole(2);
            DbManage.getInstance().saveUser(user2);
        }

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if(TextUtils.isEmpty(editName.getEditableText().toString())){
            ToastHelper.showToast("用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(editPwd.getEditableText().toString())){
            ToastHelper.showToast("密码不能为空");
            return;
        }
        requestLogin(editName.getEditableText().toString(),editPwd.getEditableText().toString());
    }

    private void requestLogin(String username, String pwd) {
        User user = DbManage.getInstance().queryUser(username);
        if(null == user){
            ToastHelper.showToast("用户不存在");
        }else {
            if(user.getPassword().equals(pwd)){
                AppPreferences.instance().put("username",username);
                if(cbRember.isChecked()){
                    AppPreferences.instance().put("rember",true);
                    AppPreferences.instance().put("pwd",pwd);
                }else {
                    AppPreferences.instance().put("rember",false);
                    AppPreferences.instance().put("pwd","");
                }
                startActivity(new Intent(this, MainPageActivity.class));
                finish();
            }else {
                ToastHelper.showToast("密码错误");
            }
        }
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


    private static String[] PERMISSIONS_REQUEST = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkPermission(Manifest.permission.READ_PHONE_STATE, Process.myPid(), Process.myUid())
                    != PackageManager.PERMISSION_GRANTED || this.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid())
                    != PackageManager.PERMISSION_GRANTED || this.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Process.myPid(), Process.myUid())
                    != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS_REQUEST, 1);
            } else {
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
            }
        }
    }
}
