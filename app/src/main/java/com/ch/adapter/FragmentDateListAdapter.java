package com.ch.adapter;

import android.content.Intent;
import android.view.View;

import com.ch.activity.HistoryActivity;
import com.ch.activity.ReportActivity;
import com.ch.bean.TestProcess;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import com.ch.evaporationrate.R;

public class FragmentDateListAdapter extends BaseQuickAdapter<TestProcess, BaseViewHolder> {

    public FragmentDateListAdapter(List<TestProcess> data) {
        super(R.layout.fragment_date_list_item, data);
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
                mContext.startActivity(new Intent(mContext,HistoryActivity.class));
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
        helper.setText(R.id.tv_result,"合格");
//        helper.setText(R.id.tv_result,item.getEvaporationRateFinal());
    }

}
