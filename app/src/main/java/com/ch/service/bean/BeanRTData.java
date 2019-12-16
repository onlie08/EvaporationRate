package com.ch.service.bean;

public class BeanRTData {

    public Float concentration;//浓度（报警用）
    public Float instantFlow;//瞬时流量
    public Float entertemperature;//入口温度
    public Float enterpressure;//入口压力

    public Float surroundtemperature;//环境温度
    public Float surroundhumidity;//环境湿度
    public Float surroundpressure;//环境压力

    public Float accFlow;//累计流量
    public Float instantQuality;//瞬时质量
    public Float accQuality;    //累计质量

    @Override
    public String toString() {

        return this.concentration+"-"+this.instantFlow+"-"+this.entertemperature+"-"+this.enterpressure+"-"+
                this.surroundtemperature+"-"+this.surroundhumidity+"-"+this.surroundpressure+"-"+
                this.accFlow+"-"+this.instantQuality+"-"+this.accQuality;
    }

    public Float getInstantFlow() {
        return instantFlow;
    }

    public void setInstantFlow(Float instantFlow) {
        this.instantFlow = instantFlow;
    }

    public Float getAccFlow() {
        return accFlow;
    }

    public void setAccFlow(Float accFlow) {
        this.accFlow = accFlow;
    }

    public Float getInstantQuality() {
        return instantQuality;
    }

    public void setInstantQuality(Float instantQuality) {
        this.instantQuality = instantQuality;
    }

    public Float getAccQuality() {
        return accQuality;
    }

    public void setAccQuality(Float accQuality) {
        this.accQuality = accQuality;
    }

    public Float getConcentration() {
        return concentration;
    }

    public void setConcentration(Float concentration) {
        this.concentration = concentration;
    }

    public Float getEntertemperature() {
        return entertemperature;
    }

    public void setEntertemperature(Float entertemperature) {
        this.entertemperature = entertemperature;
    }

    public Float getEnterpressure() {
        return enterpressure;
    }

    public void setEnterpressure(Float enterpressure) {
        this.enterpressure = enterpressure;
    }

    public Float getSurroundtemperature() {
        return surroundtemperature;
    }

    public void setSurroundtemperature(Float surroundtemperature) {
        this.surroundtemperature = surroundtemperature;
    }

    public Float getSurroundhumidity() {
        return surroundhumidity;
    }

    public void setSurroundhumidity(Float surroundhumidity) {
        this.surroundhumidity = surroundhumidity;
    }

    public Float getSurroundpressure() {
        return surroundpressure;
    }

    public void setSurroundpressure(Float surroundpressure) {
        this.surroundpressure = surroundpressure;
    }
}
