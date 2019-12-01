package com.ch.adapter;

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
//        helper.setText(R.id.fragment_date_list_item,item);
    }

}
