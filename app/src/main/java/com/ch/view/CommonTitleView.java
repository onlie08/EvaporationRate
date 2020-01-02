package com.ch.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ch.activity.LoginActivity;
import com.ch.base.base.BaseApplication;
import com.ch.base.base.ViewController;
import com.ch.bean.User;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.utils.AppPreferences;
import com.ch.utils.BrightnessTools;
import com.ch.utils.DateUtil;
import com.ch.utils.PhoneNetUtil;
import com.ch.utils.RxTimerUtil;
import com.ch.utils.ToastHelper;
import com.deadline.statebutton.StateButton;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CommonTitleView extends ViewController<String> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ln_battery)
    LinearLayout lnBattery;
    @BindView(R.id.ln_net)
    LinearLayout lnNet;
    @BindView(R.id.img_exit)
    ImageView imgExit;
    @BindView(R.id.img_person)
    ImageView imgPerson;
    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_back)
    ImageView imgBack;

    CommonNetView commonNetView;
    CommonBatteryView commonBatteryView;

    public CommonTitleView(Context context) {
        super(context);
    }

    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.img_exit, R.id.img_person, R.id.img_more, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_exit:
                showExitDialog();
                break;
            case R.id.img_person:
                showPersonDialog();
                break;
            case R.id.img_more:
                showPopwindow(imgMore);
                break;
            case R.id.img_back:
                break;
        }
    }

    private void showPersonDialog() {
        View layout = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.dialog_person_layout, null);
        TextView tv_change_pwd = layout.findViewById(R.id.tv_change_pwd);
        TextView tv_logout = layout.findViewById(R.id.tv_logout);
        final PopupWindow popupWindow = new PopupWindow(layout, DensityUtil.dp2px(300f), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setOnDismissListener(this);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(imgPerson,0,0);
        }
        tv_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showChangePwdDialog();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(),LoginActivity.class));
                ((Activity) getContext()).finish();
            }
        });
    }

    private void showChangePwdDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_change_pwd, null);
        StateButton btn_cancel = layout.findViewById(R.id.btn_cancel);
        final StateButton btn_sure = layout.findViewById(R.id.btn_sure);
        final EditText edit_old_pwd = layout.findViewById(R.id.edit_old_pwd);
        final EditText edit_new_pwd = layout.findViewById(R.id.edit_new_pwd);
        final EditText edit_new_pwd_again = layout.findViewById(R.id.edit_new_pwd_again);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(500f), LinearLayout.LayoutParams.WRAP_CONTENT);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edit_old_pwd.getText().toString())){
                    ToastHelper.showLongToast("旧密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(edit_new_pwd.getText().toString().trim())){
                    ToastHelper.showLongToast("新密码不能为空");
                    return;
                }
                if(!edit_new_pwd.getText().toString().trim().equals(edit_new_pwd_again.getText().toString().trim())){
                    ToastHelper.showLongToast("两次密码不一致");
                    return;
                }
                if(!BaseApplication.pwd.equals(edit_old_pwd.getText().toString().trim())){
                    ToastHelper.showLongToast("旧密码不正确，请检查");
                    return;
                }
                User user = new User();
                String name = (String) AppPreferences.instance().get("username","");
                if("superadmin".equals(name)){
                    user.setRole(0);
                }else if("admin".equals(name)){
                    user.setRole(1);
                }else if("root".equals(name)){
                    user.setRole(2);
                }
                user.setName(name);
                user.setPassword(edit_new_pwd.getText().toString().trim());
                DbManage.getInstance().saveUser(user);
                AppPreferences.instance().put("rember",false);
                AppPreferences.instance().put("pwd","");
                dialog.dismiss();
            }
        });
    }

    private void showExitDialog() {
        final CommonDialog dialog = new CommonDialog(getContext());

        dialog.setMessage("您确定要退出系统吗")
                .setTitle("提示")
                .setSingle(true).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                ((Activity)getContext()).finish();
            }

            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
            }
        }).show();
    }

    public interface ItemClickListener {
        void onItemClicked(int pos);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_title;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
        initView();
    }

    @Override
    protected void onDestoryView(View view){
        commonNetView.detachedRoot();
        commonBatteryView.detachedRoot();
    }

    private void initView() {
        commonNetView = new CommonNetView(getContext());
        commonNetView.attachRoot(lnNet);
        commonNetView.fillData("开始检测信号");

        commonBatteryView = new CommonBatteryView(getContext());
        commonBatteryView.attachRoot(lnBattery);
        commonBatteryView.fillData("开始检测电量");

        RxTimerUtil.interval(1000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                tvTime.setText(DateUtil.getSystemDate());
            }
        });
    }

    @Override
    protected void onBindView(String data) {
        tvTitle.setText(data);
    }

    private void initData() {
    }


    private void setItemClick(int pos) {
        if (listener != null) {
            listener.onItemClicked(pos);
        }
    }

    private void showEditStandardDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_edit_standard, null);

        final EditText edit_standard = layout.findViewById(R.id.edit_standard);
        edit_standard.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//        edit_standard.setGravity(Gravity.TOP);
        String standard = (String)AppPreferences.instance().get("standard","GB/T18443.5-2010 《真空绝热深冷设备性能试验方法 第5部分：静态蒸发率测量》、\n《NB/T47059-冷冻液化气体罐式集装箱》");
        edit_standard.setText(standard);
        edit_standard.setSingleLine(false);
        edit_standard.setHorizontallyScrolling(false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(500f), LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView img_close = layout.findViewById(R.id.img_close);
        StateButton btn_sure = layout.findViewById(R.id.btn_sure);
        StateButton btn_cancel = layout.findViewById(R.id.btn_cancel);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AppPreferences.instance().put("standard",edit_standard.getText().toString().trim());
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showStandardDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_standard, null);
        ImageView img_close = layout.findViewById(R.id.img_close);
        StateButton btn_sure = layout.findViewById(R.id.btn_sure);
        TextView tv_standard = layout.findViewById(R.id.tv_standard);

        tv_standard.setText("GB/T18443.5-2010 《真空绝热深冷设备性能试验方法 第5部分：静态蒸发率测量》、\n《NB/T47059-冷冻液化气体罐式集装箱》");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(500f), LinearLayout.LayoutParams.WRAP_CONTENT);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showPopwindow(View view){
        View layout = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.popwindow_standard, null);
        final PopupWindow popupWindow = new PopupWindow(layout, DensityUtil.dp2px(250f), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setOnDismissListener(this);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(view,0,0);
        }
        layout.findViewById(R.id.tv_menu_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showStandardDialog();
            }
        });
        layout.findViewById(R.id.tv_menu_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showEditStandardDialog();
            }
        });
        layout.findViewById(R.id.tv_menu_thire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showParamSettingDialog();
            }
        });
        layout.findViewById(R.id.tv_menu_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showSettingDialog();
            }
        });
    }

    private void showSettingDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_setting, null);
        ImageView img_close = layout.findViewById(R.id.img_close);
        SwitchCompat switch1 = layout.findViewById(R.id.switch1);
        boolean netEnable = (boolean)AppPreferences.instance().get("netEnable",true);
        switch1.setChecked(netEnable);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppPreferences.instance().put("netEnable",b);
                PhoneNetUtil.getInstance(getContext()).setMobileDataState(getContext(),b);
            }
        });
        SeekBar progressBar = layout.findViewById(R.id.progressBar);
        int bright = (int)AppPreferences.instance().get("bright",255);
        String alarmVaule = (String) AppPreferences.instance().get("alarmValue", "5");
        progressBar.setProgress(bright);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                BrightnessTools.setBrightness((Activity) getContext(),i);
                AppPreferences.instance().put("bright",i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        builder.setCancelable(false);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(600f), LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText edit_alarm_num = layout.findViewById(R.id.edit_alarm_num);
        edit_alarm_num.setText(alarmVaule);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.instance().put("alarmValue",edit_alarm_num.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void showParamSettingDialog(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View layout = inflater.inflate(R.layout.dialog_param_setting, null);
        ImageView img_close = layout.findViewById(R.id.img_close);
        final EditText edit_methane_density = layout.findViewById(R.id.edit_methane_density);
        final EditText edit_lng_density = layout.findViewById(R.id.edit_lng_density);
        final EditText edit_lng_heat_constant = layout.findViewById(R.id.edit_lng_heat_constant);
        final EditText edit_n2_density = layout.findViewById(R.id.edit_n2_density);
        final EditText edit_ln2_density = layout.findViewById(R.id.edit_ln2_density);
        final EditText edit_ln2_heat_constant = layout.findViewById(R.id.edit_ln2_heat_constant);
        final EditText edit_ln2_correct_constant = layout.findViewById(R.id.edit_ln2_correct_constant);
        final EditText edit_lng_correct_constant = layout.findViewById(R.id.edit_lng_correct_constant);

        float methaneDensity = (float)AppPreferences.instance().get("methaneDensity",0.676f);
        float lngDensity = (float)AppPreferences.instance().get("lngDensity",422.53f);
        float lngHeatConstant = (float)AppPreferences.instance().get("lngHeatConstant",1f);
        float lngCorrectConstant = (float)AppPreferences.instance().get("lngCorrectConstant",1f);

        float n2Density = (float)AppPreferences.instance().get("n2Density",1.2555f);
        float ln2Density = (float)AppPreferences.instance().get("ln2Density",808.61f);
        float ln2HeatConstant = (float)AppPreferences.instance().get("ln2HeatConstant",1f);
        float ln2CorrectConstant = (float)AppPreferences.instance().get("ln2CorrectConstant",1f);

        edit_methane_density.setText(methaneDensity+"");
        edit_lng_density.setText(lngDensity+"");
        edit_lng_heat_constant.setText(lngHeatConstant+"");
        edit_lng_correct_constant.setText(lngCorrectConstant+"");

        edit_n2_density.setText(n2Density+"");
        edit_ln2_density.setText(ln2Density+"");
        edit_ln2_heat_constant.setText(ln2HeatConstant+"");
        edit_ln2_correct_constant.setText(ln2CorrectConstant+"");


        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.instance().put("methaneDensity",Float.parseFloat(edit_methane_density.getText().toString().trim()));
                AppPreferences.instance().put("lngDensity",Float.parseFloat(edit_lng_density.getText().toString().trim()));
                AppPreferences.instance().put("lngHeatConstant",Float.parseFloat(edit_lng_heat_constant.getText().toString().trim()));
                AppPreferences.instance().put("lngCorrectConstant",Float.parseFloat(edit_lng_correct_constant.getText().toString().trim()));

                AppPreferences.instance().put("n2Density",Float.parseFloat(edit_n2_density.getText().toString().trim()));
                AppPreferences.instance().put("ln2Density",Float.parseFloat(edit_ln2_density.getText().toString().trim()));
                AppPreferences.instance().put("ln2HeatConstant",Float.parseFloat(edit_ln2_heat_constant.getText().toString().trim()));
                AppPreferences.instance().put("ln2CorrectConstant",Float.parseFloat(edit_ln2_correct_constant.getText().toString().trim()));
                dialog.dismiss();
            }
        });
    }
}
