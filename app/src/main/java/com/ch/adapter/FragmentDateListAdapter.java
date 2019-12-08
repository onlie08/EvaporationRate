package com.ch.adapter;

import android.content.Intent;
import android.view.View;

import com.ch.activity.HistoryActivity;
import com.ch.activity.ReportActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import com.ch.evaporationrate.R;

public class FragmentDateListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FragmentDateListAdapter(List<String> data) {
        super(R.layout.fragment_date_list_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if(helper.getLayoutPosition() == 1 || helper.getLayoutPosition() == 3 || helper.getLayoutPosition() == 5|| helper.getLayoutPosition() == 7|| helper.getLayoutPosition() == 9){
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
                mContext.startActivity(new Intent(mContext,ReportActivity.class));
            }
        });
//        helper.setText(R.id.fragment_date_list_item,item);
    }

}
