package com.ch.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Sensor {
    @Id(autoincrement = true)
    private long id;

    private String deviceId;

    private String evaporationRateType;
    private String evaporationRateNum;

    private String laserType;
    private String laserNum;
    private String laserTestDate;
    private String laserPassedDate;
    private String laserTestState;
    private String laserDeviceState;

    private String flowmeterType;
    private String flowmeterNum;
    private String flowmeterTestDate;
    private String flowmeterPassedDate;
    private String flowmeterTestState;
    private String flowmeterDeviceState;

    private String temperatureType;
    private String temperatureNum;
    private String temperatureTestDate;
    private String temperaturePassedDate;
    private String temperatureTestState;
    private String temperatureDeviceState;


    private String airPressureType;
    private String airPressureNum;
    private String airPressureTestDate;
    private String airPressurePassedDate;
    private String airPressureTestState;
    private String airPressureDeviceState;

    private String pressureType;
    private String pressureNum;
    private String pressureTestDate;
    private String pressurePassedDate;
    private String pressureTestState;
    private String pressureDeviceState;

    private String humidityType;
    private String humidityNum;
    private String humidityTestDate;
    private String humidityPassedDate;
    private String humidityTestState;
    private String humidityDeviceState;
    @Generated(hash = 1127665594)
    public Sensor(long id, String deviceId, String evaporationRateType,
            String evaporationRateNum, String laserType, String laserNum,
            String laserTestDate, String laserPassedDate, String laserTestState,
            String laserDeviceState, String flowmeterType, String flowmeterNum,
            String flowmeterTestDate, String flowmeterPassedDate,
            String flowmeterTestState, String flowmeterDeviceState,
            String temperatureType, String temperatureNum,
            String temperatureTestDate, String temperaturePassedDate,
            String temperatureTestState, String temperatureDeviceState,
            String airPressureType, String airPressureNum,
            String airPressureTestDate, String airPressurePassedDate,
            String airPressureTestState, String airPressureDeviceState,
            String pressureType, String pressureNum, String pressureTestDate,
            String pressurePassedDate, String pressureTestState,
            String pressureDeviceState, String humidityType, String humidityNum,
            String humidityTestDate, String humidityPassedDate,
            String humidityTestState, String humidityDeviceState) {
        this.id = id;
        this.deviceId = deviceId;
        this.evaporationRateType = evaporationRateType;
        this.evaporationRateNum = evaporationRateNum;
        this.laserType = laserType;
        this.laserNum = laserNum;
        this.laserTestDate = laserTestDate;
        this.laserPassedDate = laserPassedDate;
        this.laserTestState = laserTestState;
        this.laserDeviceState = laserDeviceState;
        this.flowmeterType = flowmeterType;
        this.flowmeterNum = flowmeterNum;
        this.flowmeterTestDate = flowmeterTestDate;
        this.flowmeterPassedDate = flowmeterPassedDate;
        this.flowmeterTestState = flowmeterTestState;
        this.flowmeterDeviceState = flowmeterDeviceState;
        this.temperatureType = temperatureType;
        this.temperatureNum = temperatureNum;
        this.temperatureTestDate = temperatureTestDate;
        this.temperaturePassedDate = temperaturePassedDate;
        this.temperatureTestState = temperatureTestState;
        this.temperatureDeviceState = temperatureDeviceState;
        this.airPressureType = airPressureType;
        this.airPressureNum = airPressureNum;
        this.airPressureTestDate = airPressureTestDate;
        this.airPressurePassedDate = airPressurePassedDate;
        this.airPressureTestState = airPressureTestState;
        this.airPressureDeviceState = airPressureDeviceState;
        this.pressureType = pressureType;
        this.pressureNum = pressureNum;
        this.pressureTestDate = pressureTestDate;
        this.pressurePassedDate = pressurePassedDate;
        this.pressureTestState = pressureTestState;
        this.pressureDeviceState = pressureDeviceState;
        this.humidityType = humidityType;
        this.humidityNum = humidityNum;
        this.humidityTestDate = humidityTestDate;
        this.humidityPassedDate = humidityPassedDate;
        this.humidityTestState = humidityTestState;
        this.humidityDeviceState = humidityDeviceState;
    }
    @Generated(hash = 786345970)
    public Sensor() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEvaporationRateType() {
        return this.evaporationRateType;
    }
    public void setEvaporationRateType(String evaporationRateType) {
        this.evaporationRateType = evaporationRateType;
    }
    public String getEvaporationRateNum() {
        return this.evaporationRateNum;
    }
    public void setEvaporationRateNum(String evaporationRateNum) {
        this.evaporationRateNum = evaporationRateNum;
    }
    public String getLaserType() {
        return this.laserType;
    }
    public void setLaserType(String laserType) {
        this.laserType = laserType;
    }
    public String getLaserNum() {
        return this.laserNum;
    }
    public void setLaserNum(String laserNum) {
        this.laserNum = laserNum;
    }
    public String getLaserTestDate() {
        return this.laserTestDate;
    }
    public void setLaserTestDate(String laserTestDate) {
        this.laserTestDate = laserTestDate;
    }
    public String getLaserPassedDate() {
        return this.laserPassedDate;
    }
    public void setLaserPassedDate(String laserPassedDate) {
        this.laserPassedDate = laserPassedDate;
    }
    public String getLaserTestState() {
        return this.laserTestState;
    }
    public void setLaserTestState(String laserTestState) {
        this.laserTestState = laserTestState;
    }
    public String getLaserDeviceState() {
        return this.laserDeviceState;
    }
    public void setLaserDeviceState(String laserDeviceState) {
        this.laserDeviceState = laserDeviceState;
    }
    public String getFlowmeterType() {
        return this.flowmeterType;
    }
    public void setFlowmeterType(String flowmeterType) {
        this.flowmeterType = flowmeterType;
    }
    public String getFlowmeterNum() {
        return this.flowmeterNum;
    }
    public void setFlowmeterNum(String flowmeterNum) {
        this.flowmeterNum = flowmeterNum;
    }
    public String getFlowmeterTestDate() {
        return this.flowmeterTestDate;
    }
    public void setFlowmeterTestDate(String flowmeterTestDate) {
        this.flowmeterTestDate = flowmeterTestDate;
    }
    public String getFlowmeterPassedDate() {
        return this.flowmeterPassedDate;
    }
    public void setFlowmeterPassedDate(String flowmeterPassedDate) {
        this.flowmeterPassedDate = flowmeterPassedDate;
    }
    public String getFlowmeterTestState() {
        return this.flowmeterTestState;
    }
    public void setFlowmeterTestState(String flowmeterTestState) {
        this.flowmeterTestState = flowmeterTestState;
    }
    public String getFlowmeterDeviceState() {
        return this.flowmeterDeviceState;
    }
    public void setFlowmeterDeviceState(String flowmeterDeviceState) {
        this.flowmeterDeviceState = flowmeterDeviceState;
    }
    public String getTemperatureType() {
        return this.temperatureType;
    }
    public void setTemperatureType(String temperatureType) {
        this.temperatureType = temperatureType;
    }
    public String getTemperatureNum() {
        return this.temperatureNum;
    }
    public void setTemperatureNum(String temperatureNum) {
        this.temperatureNum = temperatureNum;
    }
    public String getTemperatureTestDate() {
        return this.temperatureTestDate;
    }
    public void setTemperatureTestDate(String temperatureTestDate) {
        this.temperatureTestDate = temperatureTestDate;
    }
    public String getTemperaturePassedDate() {
        return this.temperaturePassedDate;
    }
    public void setTemperaturePassedDate(String temperaturePassedDate) {
        this.temperaturePassedDate = temperaturePassedDate;
    }
    public String getTemperatureTestState() {
        return this.temperatureTestState;
    }
    public void setTemperatureTestState(String temperatureTestState) {
        this.temperatureTestState = temperatureTestState;
    }
    public String getTemperatureDeviceState() {
        return this.temperatureDeviceState;
    }
    public void setTemperatureDeviceState(String temperatureDeviceState) {
        this.temperatureDeviceState = temperatureDeviceState;
    }
    public String getAirPressureType() {
        return this.airPressureType;
    }
    public void setAirPressureType(String airPressureType) {
        this.airPressureType = airPressureType;
    }
    public String getAirPressureNum() {
        return this.airPressureNum;
    }
    public void setAirPressureNum(String airPressureNum) {
        this.airPressureNum = airPressureNum;
    }
    public String getAirPressureTestDate() {
        return this.airPressureTestDate;
    }
    public void setAirPressureTestDate(String airPressureTestDate) {
        this.airPressureTestDate = airPressureTestDate;
    }
    public String getAirPressurePassedDate() {
        return this.airPressurePassedDate;
    }
    public void setAirPressurePassedDate(String airPressurePassedDate) {
        this.airPressurePassedDate = airPressurePassedDate;
    }
    public String getAirPressureTestState() {
        return this.airPressureTestState;
    }
    public void setAirPressureTestState(String airPressureTestState) {
        this.airPressureTestState = airPressureTestState;
    }
    public String getAirPressureDeviceState() {
        return this.airPressureDeviceState;
    }
    public void setAirPressureDeviceState(String airPressureDeviceState) {
        this.airPressureDeviceState = airPressureDeviceState;
    }
    public String getPressureType() {
        return this.pressureType;
    }
    public void setPressureType(String pressureType) {
        this.pressureType = pressureType;
    }
    public String getPressureNum() {
        return this.pressureNum;
    }
    public void setPressureNum(String pressureNum) {
        this.pressureNum = pressureNum;
    }
    public String getPressureTestDate() {
        return this.pressureTestDate;
    }
    public void setPressureTestDate(String pressureTestDate) {
        this.pressureTestDate = pressureTestDate;
    }
    public String getPressurePassedDate() {
        return this.pressurePassedDate;
    }
    public void setPressurePassedDate(String pressurePassedDate) {
        this.pressurePassedDate = pressurePassedDate;
    }
    public String getPressureTestState() {
        return this.pressureTestState;
    }
    public void setPressureTestState(String pressureTestState) {
        this.pressureTestState = pressureTestState;
    }
    public String getPressureDeviceState() {
        return this.pressureDeviceState;
    }
    public void setPressureDeviceState(String pressureDeviceState) {
        this.pressureDeviceState = pressureDeviceState;
    }
    public String getHumidityType() {
        return this.humidityType;
    }
    public void setHumidityType(String humidityType) {
        this.humidityType = humidityType;
    }
    public String getHumidityNum() {
        return this.humidityNum;
    }
    public void setHumidityNum(String humidityNum) {
        this.humidityNum = humidityNum;
    }
    public String getHumidityTestDate() {
        return this.humidityTestDate;
    }
    public void setHumidityTestDate(String humidityTestDate) {
        this.humidityTestDate = humidityTestDate;
    }
    public String getHumidityPassedDate() {
        return this.humidityPassedDate;
    }
    public void setHumidityPassedDate(String humidityPassedDate) {
        this.humidityPassedDate = humidityPassedDate;
    }
    public String getHumidityTestState() {
        return this.humidityTestState;
    }
    public void setHumidityTestState(String humidityTestState) {
        this.humidityTestState = humidityTestState;
    }
    public String getHumidityDeviceState() {
        return this.humidityDeviceState;
    }
    public void setHumidityDeviceState(String humidityDeviceState) {
        this.humidityDeviceState = humidityDeviceState;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
