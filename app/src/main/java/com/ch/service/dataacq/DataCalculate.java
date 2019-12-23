package com.ch.service.dataacq;

import com.ch.service.bean.BeanOperaParam;
import com.ch.service.bean.BeanRTData;

import java.util.List;

public class DataCalculate {

    /**
     * 计算测试蒸发率
     */
    public static Float[] CalTestEvaRate(int type, List<BeanRTData> lstData,BeanOperaParam ln2,BeanOperaParam lng,float validV)
    {
        Float[] res = new Float[]{0f,0f};
        Float a0 = 0.0f;
        if(type==1)
        {   //1-LN2
            //平均流量，一个数据1秒钟
            Float qm = 0.0f;
            Float avgFlow = 0.0f;
            Float accFlow = 0.0f;
            Float avgAcqPress=0.0f;
            Float accAcqPress = 0.0f;
            Float avgEnviPress=0.0f;
            Float accEnviPress = 0.0f;

            Float avgEnviTemp=0.0f;
            Float accEnviTemp = 0.0f;
            for(int i=0;i<lstData.size();i++)
            {
                accFlow += lstData.get(i).getInstantFlow();
                accEnviPress += lstData.get(i).getSurroundpressure();
                accEnviTemp += lstData.get(i).getSurroundtemperature();
                accAcqPress += lstData.get(i).getEnterpressure();
            }
            avgAcqPress = accAcqPress/lstData.size() + 0.101325f*1000; //日平均压力=平均入口压力+ 0.101325
            avgFlow = accFlow/lstData.size();
            avgEnviPress = accEnviPress/lstData.size();
            avgEnviTemp = accEnviTemp/lstData.size() + 273.15f;

            qm = (avgFlow/1000) * lstData.size()  * ln2.getGasDensity();
            Float cf = ln2.getCorrecFactor();

            Float[] liquidParam = DataCalConstant.getStardardPressInfoLn2();
            Float p1 = liquidParam[2];

            a0 = (qm * cf)/(p1 * validV) ;   //测试蒸发率
            a0 *= 100;
            float a20 = 0.0f; //静态蒸发率


            Float h = DataCalConstant.getParamInfoLn2ByEnviPress(avgEnviPress);   //实验环境压力下液体汽化潜热
            Float hfg = liquidParam[4]; //标准气压汽化潜热
            Float Ts = liquidParam[0];  //标准气压液体温度
            Float T1 = avgEnviTemp;     //平均环境温度
            Float T2 = DataCalConstant.getParamInfoLn2ByAcqPress(avgAcqPress);

            a20 = (float)( a0 * (h/hfg) * (0.7 * ((293.15f-Ts)/(T1 - T2)) + 0.3* ((Math.pow(293.15,4)-Math.pow(Ts,4))/(Math.pow(T1,4)-Math.pow(T2,4)))));

            res[0] = a0;
            res[1] = a20;
            return res;
        }else{
            //2-LNG
            //平均流量，一个数据1秒钟
            Float qm = 0.0f;
            Float avgFlow = 0.0f;
            Float accFlow = 0.0f;
            Float avgAcqPress=0.0f;
            Float accAcqPress = 0.0f;
            Float avgEnviPress=0.0f;
            Float accEnviPress = 0.0f;

            Float avgEnviTemp=0.0f;
            Float accEnviTemp = 0.0f;
            for(int i=0;i<lstData.size();i++)
            {
                accFlow += lstData.get(i).getInstantFlow();
                accEnviPress += lstData.get(i).getSurroundpressure();
                accEnviTemp += lstData.get(i).getSurroundtemperature();
                accAcqPress += lstData.get(i).getEnterpressure();
            }
            avgAcqPress = accAcqPress/lstData.size() + 0.101325f*1000; //日平均压力=平均入口压力+ 0.101325
            avgFlow = accFlow/lstData.size();
            avgEnviPress = accEnviPress/lstData.size();
            avgEnviTemp = accEnviTemp/lstData.size() + 273.15f;

            qm = (avgFlow/1000) * lstData.size()  * lng.getGasDensity();
            Float cf = lng.getCorrecFactor();

            Float[] liquidParam = DataCalConstant.getStardardPressInfoLNG();
            Float p1 = liquidParam[2];

            a0 =  (qm * cf)/(p1 * validV);   //测试蒸发率
            a0 *= 100;
            float a20 = 0.0f; //静态蒸发率
            Float h = DataCalConstant.getParamInfoLngByEnviPress(avgEnviPress);   //实验环境压力下液体汽化潜热
            Float hfg = liquidParam[4]; //标准气压汽化潜热
            Float Ts = liquidParam[0];  //标准气压液体温度
            Float T1 = avgEnviTemp;     //平均环境温度
            Float T2 = DataCalConstant.getParamInfoLngByAcqPress(avgAcqPress);

            a20 = (float)( a0 * (h/hfg) * (0.7 * ((293.15f-Ts)/(T1 - T2)) + 0.3* ((Math.pow(293.15,4)-Math.pow(Ts,4))/(Math.pow(T1,4)-Math.pow(T2,4)))));

            res[0] = a0;
            res[1] = a20;
            return res;
        }
    }
}
