package com.ch.adapter;

import com.ch.evaporationrate.R;;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CommonListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommonListAdapter(List<String> data) {
            super(R.layout.common_list_item, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_device,item);
        }

}
