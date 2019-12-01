package com.ch.view;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ch.base.base.ViewController;
import com.ch.evaporationrate.R;
import com.ch.utils.ToastHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonSpinnerViewController extends ViewController<List<String>> {
    @BindView(R.id.spinner_common)
    AppCompatSpinner spinnerCommon;
    private List<String> items;
    //定义一个ArrayAdapter适配器作为spinner的数据适配器
    private ArrayAdapter<String> adapter;

    private SpinnerChangeListener listener;

    public void setListener(SpinnerChangeListener listener) {
        this.listener = listener;
    }

    public void setSelect(int pos) {
        spinnerCommon.setSelection(pos);
    }

    public interface SpinnerChangeListener{
        void spinnerChoose(String item, int pos);
    }

    public CommonSpinnerViewController(Context context) {
        super(context);
    }

    @Override
    protected int resLayoutId() {
        return R.layout.common_spinner;
    }

    @Override
    protected void onCreatedView(View view) {
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        //为spinner绑定监听器，这里我们使用匿名内部类的方式实现监听器
        spinnerCommon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(null != adapter){
                    listener.spinnerChoose(adapter.getItem(position),position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ToastHelper.showLongToast("请选择");
            }
        });
    }

    @Override
    protected void onBindView(List<String> data) {
        items = data;
        initData();
    }

    private void initData() {
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,items);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinnerCommon.setAdapter(adapter);
    }

}
