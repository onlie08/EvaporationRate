package com.ch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.ch.activity.HistoryActivity;
import com.ch.activity.ReportActivity;
import com.ch.adapter.FragmentDateListAdapter;
import com.ch.evaporationrate.R;
import com.deadline.statebutton.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DateFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.btn_search)
    StateButton btnSearch;
    @BindView(R.id.checkBox_id)
    CheckBox checkBoxId;
    @BindView(R.id.recycler_date_list)
    RecyclerView recyclerDateList;

    FragmentDateListAdapter fragmentDateListAdapter;

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
    }

    private void initData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            strings.add("CYZU155612" + i);
        }
        fragmentDateListAdapter = new FragmentDateListAdapter(strings);
        recyclerDateList.setAdapter(fragmentDateListAdapter);
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

    @OnClick({R.id.btn_search, R.id.checkBox_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
//                startActivity(new Intent(getActivity(),HistoryActivity.class));
                break;
            case R.id.checkBox_id:
//                startActivity(new Intent(getActivity(),ReportActivity.class));
                break;
        }
    }
}
