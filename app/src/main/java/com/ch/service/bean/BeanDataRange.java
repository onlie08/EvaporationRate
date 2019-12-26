package com.ch.service.bean;

public class BeanDataRange {

    public Float[] flowRang;    //流量计量程 L/min
    public Float[] enterTemp;   //入口温度 ℃
    public Float[] enterPress;  //入口压力kpa
    public Float[] surroundTemp;    //环境温度
    public Float[] surroundPress;   //大气压力
    public Float[] humidity;        //湿度

    public BeanDataRange()
    {
        flowRang[0] = 0f; flowRang[1] = 250f;
        enterTemp[0] = 0f; enterTemp[1] = 250f;
        enterPress[0] = -200f; enterPress[1] = 50f;
        surroundTemp[0] = -40f; surroundTemp[1] = 100f;
        surroundPress[0] = 0f; surroundPress[1] = 110f;
        humidity[0] = 0f; humidity[1] = 100f;
    }

    public Float[] getFlowRang() {
        return flowRang;
    }

    public void setFlowRang(Float[] flowRang) {
        this.flowRang = flowRang;
    }

    public Float[] getEnterTemp() {
        return enterTemp;
    }

    public void setEnterTemp(Float[] enterTemp) {
        this.enterTemp = enterTemp;
    }

    public Float[] getEnterPress() {
        return enterPress;
    }

    public void setEnterPress(Float[] enterPress) {
        this.enterPress = enterPress;
    }

    public Float[] getSurroundTemp() {
        return surroundTemp;
    }

    public void setSurroundTemp(Float[] surroundTemp) {
        this.surroundTemp = surroundTemp;
    }

    public Float[] getSurroundPress() {
        return surroundPress;
    }

    public void setSurroundPress(Float[] surroundPress) {
        this.surroundPress = surroundPress;
    }

    public Float[] getHumidity() {
        return humidity;
    }

    public void setHumidity(Float[] humidity) {
        this.humidity = humidity;
    }
}
