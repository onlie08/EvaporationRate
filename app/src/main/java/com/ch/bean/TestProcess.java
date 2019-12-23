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
    private String deviceId;//设备编号
    private String staticStartTime;//静置开始时间
    private String staticEndTime;//静置结束时间
    private String staticTotalTime;//总静置时间
    private String testStartTime;//试验开始时间
    private String testEndTime;//试验结束时间
    private String testTotalTime;//总试验时间
    private String acquisitionError; //两次采集误差
    private String evaporationRateOne;//试验1蒸发率
    private String evaporationRateTwo;//试验2蒸发率
    private String evaporationRateThire;//试验3蒸发率
    private String evaporationRateFinal;//最终蒸发率结果
    private int testProcess; //试验进度
    @Generated(hash = 989472994)
    public TestProcess(Long id, String deviceId, String staticStartTime,
            String staticEndTime, String staticTotalTime, String testStartTime,
            String testEndTime, String testTotalTime, String acquisitionError,
            String evaporationRateOne, String evaporationRateTwo,
            String evaporationRateThire, String evaporationRateFinal,
            int testProcess) {
        this.id = id;
        this.deviceId = deviceId;
        this.staticStartTime = staticStartTime;
        this.staticEndTime = staticEndTime;
        this.staticTotalTime = staticTotalTime;
        this.testStartTime = testStartTime;
        this.testEndTime = testEndTime;
        this.testTotalTime = testTotalTime;
        this.acquisitionError = acquisitionError;
        this.evaporationRateOne = evaporationRateOne;
        this.evaporationRateTwo = evaporationRateTwo;
        this.evaporationRateThire = evaporationRateThire;
        this.evaporationRateFinal = evaporationRateFinal;
        this.testProcess = testProcess;
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
    public String getStaticTotalTime() {
        return this.staticTotalTime;
    }
    public void setStaticTotalTime(String staticTotalTime) {
        this.staticTotalTime = staticTotalTime;
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
    public String getTestTotalTime() {
        return this.testTotalTime;
    }
    public void setTestTotalTime(String testTotalTime) {
        this.testTotalTime = testTotalTime;
    }
    public String getAcquisitionError() {
        return this.acquisitionError;
    }
    public void setAcquisitionError(String acquisitionError) {
        this.acquisitionError = acquisitionError;
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
    public String getEvaporationRateFinal() {
        return this.evaporationRateFinal;
    }
    public void setEvaporationRateFinal(String evaporationRateFinal) {
        this.evaporationRateFinal = evaporationRateFinal;
    }
    public int getTestProcess() {
        return this.testProcess;
    }
    public void setTestProcess(int testProcess) {
        this.testProcess = testProcess;
    }
    
}
