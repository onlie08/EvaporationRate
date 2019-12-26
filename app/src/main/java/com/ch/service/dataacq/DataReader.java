package com.ch.service.dataacq;

import com.ch.service.bean.BeanRTData;
import com.ch.utils.RandomDataUtil;

import java.util.Date;
import java.util.Random;

public class DataReader {

    public Float mAccFlow = 0.0f;
    public Float mAccQuality = 0.0f;

    public void resetData()
    {
        mAccFlow = 0.0f;
    }
    public BeanRTData getData()
    {
        BeanRTData retData = new BeanRTData();

        try {
            retData.setConcentration(RandomDataUtil.nextDouble(0.0f,1.0f));


            Float curFlow = RandomDataUtil.nextDouble(1.0f,60.0f);
            retData.setInstantFlow(curFlow);
            retData.setEntertemperature(RandomDataUtil.nextDouble(0f,50.0f));
            retData.setEnterpressure(RandomDataUtil.nextDouble(1f,100f));
            retData.setSurroundtemperature(RandomDataUtil.nextDouble(0f,40f));
            retData.setSurroundhumidity(RandomDataUtil.nextDouble(0.65f,0.85f));
            retData.setSurroundpressure(RandomDataUtil.nextDouble(90.325f,101.425f));
            retData.setIswarm(new Random().nextInt(1));
             /*
            //测试数据 结果 a0=0.000115  a20=0.0001177
            Float curFlow = 52F; //30 L/Min
            retData.setInstantFlow(curFlow);
            retData.setEntertemperature(20f);
            retData.setEnterpressure(1f); //kpa
            retData.setSurroundtemperature(6f);
            retData.setSurroundhumidity(RandomDataUtil.nextDouble(0.65f,0.85f));
            retData.setSurroundpressure(90f);
*/
            mAccFlow+=curFlow;
            retData.setAccFlow(mAccFlow);

            Float curQuality = RandomDataUtil.nextDouble(0.02f,0.04f);
            retData.setInstantQuality(curQuality);
            mAccQuality+=curQuality;
            retData.setAccQuality(mAccQuality);
            retData.setAcqTime(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return retData;
    }
}
