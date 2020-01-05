package com.ch.service.dataacq;

import android.util.Log;

import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;
import com.ch.service.serialport.SerialPortIO;
import com.ch.utils.NumberUtil;
import com.ch.utils.RandomDataUtil;

import java.util.Date;
import java.util.Random;

public class DataReader {
    protected SerialPortIO mSPManager = new SerialPortIO();

    private int mMediumtype;//介质类型
    private BeanOperaParam mLNGParam;   //LNG运算参数
    private BeanOperaParam mLN2Param;   //LN2运算参数

    public Float mAccFlow = 0.0f;
    public Float mAccQuality = 0.0f;

    protected int mAcqStatus = 0;   //  采集状态 0-未实验  1-开始实验 2-暂停实验


    public void sendCMD(byte cmd)
    {
        mSPManager.sendCMDToDevice(cmd);
    }


    //设置数据采集的状态
    public void setAcqStatus(int value)
    {
        mAcqStatus = value;
    }

    public void resetData()
    {
        mAccFlow = 0.0f;
        mAccQuality = 0.0f;
    }

    public void setParam(int mediatype,BeanOperaParam lng,BeanOperaParam ln2)
    {
        mMediumtype = mediatype;
        mLNGParam = lng;
        mLN2Param = ln2;
    }

    //数据读取回调
    protected OnDataReaderCB mDataReaderCB;
    public interface OnDataReaderCB{
        public void revData(BeanRTData data);
    }

    public int initReader(OnDataReaderCB handler)
    {
        mDataReaderCB = handler;
        //初始化串口
        int ret = mSPManager.initPort();
        //设置回调
        mSPManager.setOnSPDataCB(new SerialPortIO.OnSPDataCB() {
            @Override
            public void revData(String data) {
                //异常数据直接返回

                if(data==null||data.length()<=0)
                {
                    return;
                }

                //十六进制字符串转换为 ascii码字符
                String s = data;
                StringBuilder sb = new StringBuilder(s.length() / 2);
                for (int i = 0; i < s.length(); i+=2) {
                    String hex = "" + s.charAt(i) + s.charAt(i+1);
                    int ival = Integer.parseInt(hex, 16);
                    sb.append((char) ival);
                }
                String string = sb.toString();
                Log.d("SP test", "revData: "+string);

                //解析数据

                String[] arrStrData = string.split(",");
                if(arrStrData==null||arrStrData.length<8)
                {
                    return;
                }
                Float curFlow;
                BeanRTData retData = new BeanRTData();
                try {
                    curFlow = Float.parseFloat(arrStrData[0]);
                    retData.setInstantFlow(curFlow);
                    retData.setEnterpressure(NumberUtil.stayDigit(Float.parseFloat(arrStrData[1]),2));
                    retData.setEntertemperature(NumberUtil.stayDigit(Float.parseFloat(arrStrData[2]),2));
                    retData.setSurroundpressure(Float.parseFloat(arrStrData[3]));
                    retData.setSurroundhumidity(NumberUtil.stayDigit(Float.parseFloat(arrStrData[4]),0));
                    retData.setSurroundtemperature(Float.parseFloat(arrStrData[5]));
                    retData.setConcentration(NumberUtil.stayDigit(Float.parseFloat(arrStrData[6]),1));
                    retData.setIswarm(Integer.parseInt(arrStrData[8]));
                }catch (Exception e){
                    return;
                }
                Float curQuality =0.01f;

                float density = 0.0f;
                if(mMediumtype==1){
                    //ln2
                    density = mLN2Param.getGasDensity();
                }else{
                    //lng
                    density = mLNGParam.getGasDensity();
                }
                curQuality = (curFlow)*density/1000f;
                retData.setInstantQuality(NumberUtil.stayDigit(curQuality,6));

                //开始实验才开始
                if(mAcqStatus==1)
                {
                    mAccFlow+=(curFlow/60); //流量转换为每秒进行累加
                    if(mAccFlow<1)
                    {
                        mAccFlow =  NumberUtil.stayDigit(mAccFlow,2);
                    }else{
                        mAccFlow =  NumberUtil.stayDigit(mAccFlow,2);
                    }
                    retData.setAccFlow(mAccFlow);

                    mAccQuality+=curQuality/60;
                    /*
                    if(mAccQuality<1)
                    {
                        mAccQuality =  NumberUtil.stayDigit(mAccQuality,2);
                    }else{
                        mAccQuality =  NumberUtil.stayDigit(mAccQuality,2);
                    }
                    */
                    retData.setAccQuality(mAccQuality);
                }else if(mAcqStatus==2){
                    retData.setAccFlow(mAccFlow);
                    retData.setAccQuality(mAccQuality);
                }else{
                    retData.setAccFlow(0.0f);
                    retData.setAccQuality(0.0f);
                }
                retData.setAcqTime(new Date());
                mDataReaderCB.revData(retData);
            }
        });
        return ret;
    }

    /**
     * 判断串口是否打开 0-没有打开 1-已经打开
     * @return
     */
    public int isSerialPortOpen()
    {
        if(mSPManager!=null)
        {
            return mSPManager.serialPortHelper.isOpenDevice()?1:0;
        }
        return 0;
    }

    /**
     * 从串口读取真实数据
     * @return
     */
    public void getDataReal()
    {
        mSPManager.getSerialPortData();
    }

    /**
     * 模拟方式读取数据
     * @return
     */
    public BeanRTData getDataSimulate()
    {
        BeanRTData retData = new BeanRTData();

        try {
            retData.setConcentration(RandomDataUtil.nextDouble(0.0f,1.0f));

            Float curFlow = RandomDataUtil.nextDouble(1.0f,60.0f)/60f;
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
