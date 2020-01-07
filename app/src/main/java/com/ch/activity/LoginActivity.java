package com.ch.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ch.base.base.BaseApplication;
import com.ch.bean.User;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.AppPreferences;
import com.ch.utils.BrightnessTools;
import com.ch.utils.DateUtil;
import com.ch.utils.ToastHelper;
import com.eftimoff.androipathview.PathView;
import com.why.project.poilib.PoiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ch.base.base.BaseApplication.loginTime;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestPermission();
        initView();
        initData();
        int bright = (int)AppPreferences.instance().get("bright",255);
        BrightnessTools.setBrightness(this,bright);
    }


    private void initView() {
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
                BaseApplication.pwd = pwd;
                if(cbRember.isChecked()){
                    AppPreferences.instance().put("rember",true);
                    AppPreferences.instance().put("pwd",pwd);
                }else {
                    AppPreferences.instance().put("rember",false);
                    AppPreferences.instance().put("pwd","");
                }
                loginTime = DateUtil.getSystemDate();
//                saveWord();
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
//    private void saveWord(){
//        try {
//            InputStream templetDocStream = getAssets().open("储罐静态蒸发率试验报告.doc");
//            String targetDocPath = Environment.getExternalStorageDirectory() + File.separator + "001evaporation/"+ DateUtil.getSystemDate1()+"_储罐静态蒸发率试验报告.doc";
//
//            Map<String, String> dataMap = new HashMap<String, String>();
//            dataMap.put("$testDate$", "201910101011010");
//            String standard = (String)AppPreferences.instance().get("standard","GB/T18443.5-2010 《真空绝热深冷设备性能试验方法 第5部分：静态蒸发率测量》、\n《NB/T47059-冷冻液化气体罐式集装箱》");
//
//            dataMap.put("$testStandard$", standard);
//            dataMap.put("$deviceNum$", "201910101011010");
//            dataMap.put("$standardRate$", "201910101011010");
//            dataMap.put("$testRate$", "201910101011010");
//            dataMap.put("$testDeviceNum$", "201910101011010");
//            dataMap.put("$testStartDate$", "201910101011010");
//            dataMap.put("$testEndDate$", "201910101011010");
//            dataMap.put("$fillEndDate$", "201910101011010");
//            dataMap.put("$fillRate$", "201910101011010");
//            dataMap.put("$tmpVaule$", "201910101011010");
//            dataMap.put("$pressVaule$", "201910101011010");
//            dataMap.put("$volVaule$", "201910101011010");
//            dataMap.put("$rateVaule$", "201910101011010"+"%");
//            dataMap.put("$evaVaule$", "201910101011010");
//
//            PoiUtils.writeToDoc(templetDocStream,targetDocPath,dataMap);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
