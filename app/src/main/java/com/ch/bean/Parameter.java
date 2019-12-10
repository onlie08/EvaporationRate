package com.ch.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Parameter {
    @Id(autoincrement = true)
    private Long id;

    private String checkoutCompany;

    private String useDeviceCompany;

    private String testAddress;

    private String deviceMadeinCompany;

    @Unique
    private String deviceId;

    private String mediumType;

    private String deviceName;

    private String deviceType;

    private String fullnessRate;

    private String measurementVolume;

    private String effectiveVolume;

    private String licenseNo;

    private String testDate;

    private String testEndDate;

    private String qualificationRate;

    private String madeinDate;

    private String liquidFillingEndDate;

    private String designStandard;

    @Generated(hash = 2080903578)
    public Parameter(Long id, String checkoutCompany, String useDeviceCompany,
            String testAddress, String deviceMadeinCompany, String deviceId,
            String mediumType, String deviceName, String deviceType,
            String fullnessRate, String measurementVolume, String effectiveVolume,
            String licenseNo, String testDate, String testEndDate,
            String qualificationRate, String madeinDate,
            String liquidFillingEndDate, String designStandard) {
        this.id = id;
        this.checkoutCompany = checkoutCompany;
        this.useDeviceCompany = useDeviceCompany;
        this.testAddress = testAddress;
        this.deviceMadeinCompany = deviceMadeinCompany;
        this.deviceId = deviceId;
        this.mediumType = mediumType;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.fullnessRate = fullnessRate;
        this.measurementVolume = measurementVolume;
        this.effectiveVolume = effectiveVolume;
        this.licenseNo = licenseNo;
        this.testDate = testDate;
        this.testEndDate = testEndDate;
        this.qualificationRate = qualificationRate;
        this.madeinDate = madeinDate;
        this.liquidFillingEndDate = liquidFillingEndDate;
        this.designStandard = designStandard;
    }

    @Generated(hash = 1889793194)
    public Parameter() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckoutCompany() {
        return this.checkoutCompany;
    }

    public void setCheckoutCompany(String checkoutCompany) {
        this.checkoutCompany = checkoutCompany;
    }

    public String getUseDeviceCompany() {
        return this.useDeviceCompany;
    }

    public void setUseDeviceCompany(String useDeviceCompany) {
        this.useDeviceCompany = useDeviceCompany;
    }

    public String getTestAddress() {
        return this.testAddress;
    }

    public void setTestAddress(String testAddress) {
        this.testAddress = testAddress;
    }

    public String getDeviceMadeinCompany() {
        return this.deviceMadeinCompany;
    }

    public void setDeviceMadeinCompany(String deviceMadeinCompany) {
        this.deviceMadeinCompany = deviceMadeinCompany;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMediumType() {
        return this.mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFullnessRate() {
        return this.fullnessRate;
    }

    public void setFullnessRate(String fullnessRate) {
        this.fullnessRate = fullnessRate;
    }

    public String getMeasurementVolume() {
        return this.measurementVolume;
    }

    public void setMeasurementVolume(String measurementVolume) {
        this.measurementVolume = measurementVolume;
    }

    public String getEffectiveVolume() {
        return this.effectiveVolume;
    }

    public void setEffectiveVolume(String effectiveVolume) {
        this.effectiveVolume = effectiveVolume;
    }

    public String getLicenseNo() {
        return this.licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getTestDate() {
        return this.testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestEndDate() {
        return this.testEndDate;
    }

    public void setTestEndDate(String testEndDate) {
        this.testEndDate = testEndDate;
    }

    public String getQualificationRate() {
        return this.qualificationRate;
    }

    public void setQualificationRate(String qualificationRate) {
        this.qualificationRate = qualificationRate;
    }

    public String getMadeinDate() {
        return this.madeinDate;
    }

    public void setMadeinDate(String madeinDate) {
        this.madeinDate = madeinDate;
    }

    public String getLiquidFillingEndDate() {
        return this.liquidFillingEndDate;
    }

    public void setLiquidFillingEndDate(String liquidFillingEndDate) {
        this.liquidFillingEndDate = liquidFillingEndDate;
    }

    public String getDesignStandard() {
        return this.designStandard;
    }

    public void setDesignStandard(String designStandard) {
        this.designStandard = designStandard;
    }


}
