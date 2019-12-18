package com.ch.service.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BeanRTData {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String deviceId;
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
    @Generated(hash = 1336201911)
    public BeanRTData(Long id, String deviceId, Float concentration,
            Float instantFlow, Float entertemperature, Float enterpressure,
            Float surroundtemperature, Float surroundhumidity,
            Float surroundpressure, Float accFlow, Float instantQuality,
            Float accQuality) {
        this.id = id;
        this.deviceId = deviceId;
        this.concentration = concentration;
        this.instantFlow = instantFlow;
        this.entertemperature = entertemperature;
        this.enterpressure = enterpressure;
        this.surroundtemperature = surroundtemperature;
        this.surroundhumidity = surroundhumidity;
        this.surroundpressure = surroundpressure;
        this.accFlow = accFlow;
        this.instantQuality = instantQuality;
        this.accQuality = accQuality;
    }
    @Generated(hash = 1912097834)
    public BeanRTData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Float getConcentration() {
        return this.concentration;
    }
    public void setConcentration(Float concentration) {
        this.concentration = concentration;
    }
    public Float getInstantFlow() {
        return this.instantFlow;
    }
    public void setInstantFlow(Float instantFlow) {
        this.instantFlow = instantFlow;
    }
    public Float getEntertemperature() {
        return this.entertemperature;
    }
    public void setEntertemperature(Float entertemperature) {
        this.entertemperature = entertemperature;
    }
    public Float getEnterpressure() {
        return this.enterpressure;
    }
    public void setEnterpressure(Float enterpressure) {
        this.enterpressure = enterpressure;
    }
    public Float getSurroundtemperature() {
        return this.surroundtemperature;
    }
    public void setSurroundtemperature(Float surroundtemperature) {
        this.surroundtemperature = surroundtemperature;
    }
    public Float getSurroundhumidity() {
        return this.surroundhumidity;
    }
    public void setSurroundhumidity(Float surroundhumidity) {
        this.surroundhumidity = surroundhumidity;
    }
    public Float getSurroundpressure() {
        return this.surroundpressure;
    }
    public void setSurroundpressure(Float surroundpressure) {
        this.surroundpressure = surroundpressure;
    }
    public Float getAccFlow() {
        return this.accFlow;
    }
    public void setAccFlow(Float accFlow) {
        this.accFlow = accFlow;
    }
    public Float getInstantQuality() {
        return this.instantQuality;
    }
    public void setInstantQuality(Float instantQuality) {
        this.instantQuality = instantQuality;
    }
    public Float getAccQuality() {
        return this.accQuality;
    }
    public void setAccQuality(Float accQuality) {
        this.accQuality = accQuality;
    }


}
