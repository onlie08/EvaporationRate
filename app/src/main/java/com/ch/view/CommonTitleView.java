package com.ch.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;
import com.deadline.statebutton.StateButton;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
            case R.id.img_person:
                break;
            case R.id.img_more:
                showPopwindow(imgMore);
                break;
            case R.id.img_back:
                break;
        }
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


        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(500f), LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText edit_standard = layout.findViewById(R.id.edit_standard);
        edit_standard.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edit_standard.setGravity(Gravity.TOP);
        edit_standard.setText("我的地方的范德萨发达打法的发达大打发打发打发大幅答复\\n的萨芬的发达放大大方点撒放大放大发的萨芬的萨芬的撒范德萨范德萨范德萨范德萨放大");
        edit_standard.setSingleLine(false);
        edit_standard.setHorizontallyScrolling(false);

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MaterialBaseTheme_AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dp2px(500f), LinearLayout.LayoutParams.WRAP_CONTENT);

        tv_standard.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        tv_standard.setGravity(Gravity.TOP);
        tv_standard.setText("我的地方的范德萨发达打法的发达大打发打发打发大幅答复\\n的萨芬的发达放大大方点撒放大放大发的萨芬的萨芬的撒范德萨范德萨范德萨范德萨放大");
        tv_standard.setSingleLine(false);
        tv_standard.setHorizontallyScrolling(false);

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
        layout.findViewById(R.id.tv_menu_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStandardDialog();
            }
        });
        layout.findViewById(R.id.tv_menu_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditStandardDialog();
            }
        });
        layout.findViewById(R.id.tv_menu_thire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        layout.findViewById(R.id.tv_menu_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        PopupWindow popupWindow = new PopupWindow(layout, DensityUtil.dp2px(150f), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setOnDismissListener(this);
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(view,0,DensityUtil.dp2px(8f));
        }
    }

}
