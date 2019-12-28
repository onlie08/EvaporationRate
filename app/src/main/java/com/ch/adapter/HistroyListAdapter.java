package com.ch.adapter;

import com.ch.evaporationrate.R;
import com.ch.service.bean.BeanRTData;
import com.ch.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HistroyListAdapter extends BaseQuickAdapter<BeanRTData, BaseViewHolder> {

    public HistroyListAdapter(List<BeanRTData> data) {
        super(R.layout.history_list_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, BeanRTData item) {
        if(helper.getLayoutPosition()%2==1){
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorAccentPrimary);
        }else {
            helper.getView(R.id.constraint_item).setBackgroundResource(R.color.colorWhite);
        }
        helper.setText(R.id.tv_time,DateUtil.dateFormat(item.getAcqTime()));
        helper.setText(R.id.tv_flowmeter,item.getInstantFlow()+"");
        helper.setText(R.id.tv_pressure,item.getSurroundpressure()+"");
        helper.setText(R.id.tv_envir_temp,item.getSurroundtemperature()+"");
        helper.setText(R.id.tv_flowmeter_tmp,item.getEntertemperature()+"");
        helper.setText(R.id.tv_flowmeter_pressure,item.getEnterpressure()+"");
        helper.setText(R.id.tv_temp,item.getSurroundhumidity()+"");
    }

}
