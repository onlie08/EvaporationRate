package com.ch.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TestProcess {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String deviceId;
    private String staticStartTime;
    private String staticEndTime;
    private String testStartTime;
    private String testEndTime;
    private String evaporationRateOne;
    private String evaporationRateTwo;
    private String evaporationRateThire;
    @Generated(hash = 943864046)
    public TestProcess(Long id, String deviceId, String staticStartTime,
            String staticEndTime, String testStartTime, String testEndTime,
            String evaporationRateOne, String evaporationRateTwo,
            String evaporationRateThire) {
        this.id = id;
        this.deviceId = deviceId;
        this.staticStartTime = staticStartTime;
        this.staticEndTime = staticEndTime;
        this.testStartTime = testStartTime;
        this.testEndTime = testEndTime;
        this.evaporationRateOne = evaporationRateOne;
        this.evaporationRateTwo = evaporationRateTwo;
        this.evaporationRateThire = evaporationRateThire;
    }
    @Generated(hash = 1871413968)
    public TestProcess() {
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
    public String getStaticStartTime() {
        return this.staticStartTime;
    }
    public void setStaticStartTime(String staticStartTime) {
        this.staticStartTime = staticStartTime;
    }
    public String getStaticEndTime() {
        return this.staticEndTime;
    }
    public void setStaticEndTime(String staticEndTime) {
        this.staticEndTime = staticEndTime;
    }
    public String getTestStartTime() {
        return this.testStartTime;
    }
    public void setTestStartTime(String testStartTime) {
        this.testStartTime = testStartTime;
    }
    public String getTestEndTime() {
        return this.testEndTime;
    }
    public void setTestEndTime(String testEndTime) {
        this.testEndTime = testEndTime;
    }
    public String getEvaporationRateOne() {
        return this.evaporationRateOne;
    }
    public void setEvaporationRateOne(String evaporationRateOne) {
        this.evaporationRateOne = evaporationRateOne;
    }
    public String getEvaporationRateTwo() {
        return this.evaporationRateTwo;
    }
    public void setEvaporationRateTwo(String evaporationRateTwo) {
        this.evaporationRateTwo = evaporationRateTwo;
    }
    public String getEvaporationRateThire() {
        return this.evaporationRateThire;
    }
    public void setEvaporationRateThire(String evaporationRateThire) {
        this.evaporationRateThire = evaporationRateThire;
    }

    
}
