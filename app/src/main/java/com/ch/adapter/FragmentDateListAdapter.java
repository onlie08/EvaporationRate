package com.ch.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ch.activity.HistoryActivity;
import com.ch.activity.ReportActivity;
import com.ch.bean.TestProcess;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ch.evaporationrate.R;

public class FragmentDateListAdapter extends BaseQuickAdapter<TestProcess, BaseViewHolder> {
    private HashMap<Long,Boolean> hashMap = new HashMap<>();

    private checkedListener listener;

    public void setListener(checkedListener listener) {
        this.listener = listener;
    }

    public interface checkedListener {
        void itemChecked(HashMap<Long,Boolean> hashMap);
    }

    public FragmentDateListAdapter(List<TestProcess> data) {
        super(R.layout.fragment_date_list_item, data);
        for(TestProcess testProcess : data){
            hashMap.put(testProcess.getId(),testProcess.isChecked());
        }
    }
    @Override
    protected void convert(BaseViewHolder helper, final TestProcess item) {

        if(helper.getLayoutPosition()%2 != 0){
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorAccentPrimary);
        }else {
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorWhite);
        }
        helper.getView(R.id.tv_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,HistoryActivity.class);
                intent.putExtra("deviceId",item.getDeviceId());
                mContext.startActivity(intent);
            }
        });
        helper.getView(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext,ReportActivity.class);
                intent.putExtra("deviceId",item.getDeviceId());
                mContext.startActivity(intent);
            }
        });
        helper.setText(R.id.tv_device_num,item.getDeviceId());
        helper.setText(R.id.tv_time,item.getTestEndTime());
        helper.setText(R.id.tv_result,item.getIsPass() ? "合格" : "不合格");
        helper.setText(R.id.tv_id,helper.getPosition()+"");

        CheckBox checkBox = helper.getView(R.id.checkbox);
        if(item.isChecked()){
            checkBox.setChecked(true);
            listener.itemChecked(hashMap);
        }else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hashMap.put(item.getId(),true);
                }else {
                    hashMap.put(item.getId(),false);
                }
                listener.itemChecked(hashMap);
            }
        });

//        helper.setText(R.id.tv_result,item.getEvaporationRateFinal());
    }

}
