package com.ch.service.dataacq;

import com.ch.service.bean.BeanRTData;
import com.ch.utils.RandomDataUtil;

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

            Float curFlow = RandomDataUtil.nextDouble(100.0f,400.0f);
            retData.setInstantFlow(curFlow);
            retData.setEntertemperature(RandomDataUtil.nextDouble(-210.0f,-180.0f));
            retData.setEnterpressure(RandomDataUtil.nextDouble(0.01f,0.05f));
            retData.setSurroundtemperature(RandomDataUtil.nextDouble(0f,40f));
            retData.setSurroundhumidity(RandomDataUtil.nextDouble(0.65f,0.85f));
            retData.setSurroundpressure(RandomDataUtil.nextDouble(101.325f,101.425f));

            mAccFlow+=curFlow;
            retData.setAccFlow(mAccFlow);

            Float curQuality = RandomDataUtil.nextDouble(0.02f,0.04f);
            retData.setInstantQuality(curQuality);
            mAccQuality+=curQuality;
            retData.setAccQuality(mAccQuality);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return retData;
    }
}
