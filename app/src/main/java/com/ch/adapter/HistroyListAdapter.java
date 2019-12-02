package com.ch.adapter;

import com.ch.evaporationrate.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HistroyListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistroyListAdapter(List<String> data) {
        super(R.layout.history_list_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if(helper.getLayoutPosition()%2==1){
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorAccentPrimary);
        }else {
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorWhite);
        }
//        helper.setText(R.id.fragment_date_list_item,item);
    }

}
