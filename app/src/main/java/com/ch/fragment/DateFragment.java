package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ch.adapter.FragmentDateListAdapter;
import com.ch.bean.TestProcess;
import com.ch.db.DbManage;
import com.ch.evaporationrate.R;
import com.ch.utils.ToastHelper;
import com.ch.view.CommonDialog;
import com.deadline.statebutton.StateButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DateFragment extends Fragment {
    @BindView(R.id.btn_search)
    StateButton btnSearch;
    @BindView(R.id.checkBox_id)
    CheckBox checkBoxId;
    @BindView(R.id.recycler_date_list)
    RecyclerView recyclerDateList;
    @BindView(R.id.btn_next_page)
    StateButton btnNextPage;
    @BindView(R.id.btn_pre_page)
    StateButton btnPrePage;
    @BindView(R.id.btn_delect)
    StateButton btnDelect;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;
    @BindView(R.id.edit_device_num)
    EditText editDeviceNum;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceId;
    @BindView(R.id.tv_page_num)
    TextView tvPageNum;
    @BindView(R.id.tv_page_count)
    TextView tvPageCount;

    Unbinder unbinder;
    private FragmentDateListAdapter fragmentDateListAdapter;
    private List<TestProcess> allTestProcess = new ArrayList<>();
    private HashMap<Long, Boolean> hashMap;
    private int offset = 0;
    private int totalNum = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initData();
        return root;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端显示 相当于调用了onPause();
            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            initData();
        }

    }

    private void initView() {
        recyclerDateList.setLayoutManager(new LinearLayoutManager(getContext()));
        editDeviceNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString().trim())) {
                            initData();
                }
            }
        });

    }

    private void queryDevice(String s) {
        allTestProcess = DbManage.getInstance().queryLikeTestProcess(s);
        loadListDate();
    }

    private void loadListDate() {
        fragmentDateListAdapter = new FragmentDateListAdapter(allTestProcess);
        fragmentDateListAdapter.setListener(new FragmentDateListAdapter.checkedListener() {
            @Override
            public void itemChecked(HashMap<Long, Boolean> checked) {
                hashMap = checked;
                boolean allCheck = true;
                for (Map.Entry<Long, Boolean> entry : hashMap.entrySet()) {
                    if(!entry.getValue()){
                        checkBoxId.setChecked(false);
                        allCheck = false;
                    }
                }
                if(allCheck){
                    checkBoxId.setChecked(true);
                }
            }
        });
        recyclerDateList.setAdapter(fragmentDateListAdapter);
    }

    private void initData() {
        allTestProcess = DbManage.getInstance().queryAllTestProcess(offset);
        totalNum = DbManage.getInstance().queryTestProcessNum();
        loadListDate();
        if(allTestProcess.size() == 0){
            tvPageCount.setText("0");
        }else {
            int count = totalNum/10 + 1;
            tvPageCount.setText(count + "");
        }
        tvPageNum.setText((offset+1) +"");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.btn_next_page, R.id.btn_pre_page, R.id.btn_delect, R.id.tv_select_time, R.id.checkBox_id,R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next_page:
                if(offset < totalNum/10){
                    offset++;
                    initData();
                }
                break;
            case R.id.btn_pre_page:
                if(offset>0){
                    offset--;
                }
                initData();
                break;
            case R.id.btn_delect:
                if(null == hashMap){
                    ToastHelper.showLongToast("请选择试验数据");
                    return;
                }
                boolean isSelect = false;
                for (Map.Entry<Long, Boolean> entry : hashMap.entrySet()) {
                    if(entry.getValue()){
                        isSelect = true;
                    }
                }
                if(isSelect){
                    final CommonDialog commonDialog = new CommonDialog(getActivity());
                    commonDialog.setTitle("提示")
                            .setMessage("是否删除选中数据？")
                            .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                                @Override
                                public void onPositiveClick() {
                                    for (Map.Entry<Long, Boolean> entry : hashMap.entrySet()) {
                                        if(entry.getValue()){
                                            DbManage.getInstance().delectTestProcess(entry.getKey());
                                        }
                                    }
                                    initData();
                                    commonDialog.dismiss();
                                }

                                @Override
                                public void onNegtiveClick() {
                                    commonDialog.dismiss();
                                }
                            }).show();

                }else {
                    ToastHelper.showLongToast("请选择试验数据");
                }
                break;
            case R.id.tv_select_time:
                break;
            case R.id.checkBox_id:
                if(!checkBoxId.isChecked()){
                    checkBoxId.setChecked(false);
                    for(TestProcess testProcess : allTestProcess){
                        testProcess.setChecked(false);
                    }
                    loadListDate();
                }else {
                    checkBoxId.setChecked(true);
                    for(TestProcess testProcess : allTestProcess){
                        testProcess.setChecked(true);
                    }
                    loadListDate();
                }
                break;
            case R.id.btn_search:
                if(TextUtils.isEmpty(editDeviceNum.getText().toString().trim())){
                    ToastHelper.showLongToast("请输入容器编号");
                    return;
                }
                queryDevice(editDeviceNum.getText().toString().trim());
                break;
        }
    }

}
