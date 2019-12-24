package com.ch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.deadline.statebutton.StateButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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

    private void initView() {
        recyclerDateList.setLayoutManager(new LinearLayoutManager(getContext()));
        RxTextView.textChanges(editDeviceNum)
                .skip(0)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return new StringBuilder(charSequence).reverse().toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(TextUtils.isEmpty(s)){
                            initData();
                        }else {
                            queryDevice(s);
                        }
                    }
                });


    }

    private void queryDevice(String s) {
        allTestProcess = DbManage.getInstance().queryLikeTestProcess(s);
        fragmentDateListAdapter.notifyDataSetChanged();
    }

    private void loadListDate(){
        fragmentDateListAdapter = new FragmentDateListAdapter(allTestProcess);
        recyclerDateList.setAdapter(fragmentDateListAdapter);
    }

    private void initData() {
        allTestProcess = DbManage.getInstance().queryAllTestProcess();
        loadListDate();
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


    @OnClick({R.id.btn_next_page, R.id.btn_pre_page, R.id.btn_delect, R.id.tv_select_time, R.id.checkBox_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next_page:
                break;
            case R.id.btn_pre_page:
                break;
            case R.id.btn_delect:
                break;
            case R.id.tv_select_time:
                break;
            case R.id.checkBox_id:
                break;
        }
    }
}
