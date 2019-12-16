package com.ch.service.bean;

public class BeanOperaParam {

    public Float gasDensity;    //气体密度 g/L
    public Float liquidDensity; //液态密度 kg/m3
    public Float conK;          //热力学常数字
    public Float correcFactor;  //修正系数

    public BeanOperaParam(Float gasDensity,Float liquidDensity,Float conK,Float correcFactor)
    {
        this.gasDensity = gasDensity;
        this.liquidDensity = liquidDensity;
        this.conK = conK;
        this.correcFactor = correcFactor;
    }

    public Float getGasDensity() {
        return gasDensity;
    }

    public void setGasDensity(Float gasDensity) {
        this.gasDensity = gasDensity;
    }

    public Float getLiquidDensity() {
        return liquidDensity;
    }

    public void setLiquidDensity(Float liquidDensity) {
        this.liquidDensity = liquidDensity;
    }

    public Float getConK() {
        return conK;
    }

    public void setConK(Float conK) {
        this.conK = conK;
    }

    public Float getCorrecFactor() {
        return correcFactor;
    }

    public void setCorrecFactor(Float correcFactor) {
        this.correcFactor = correcFactor;
    }
}
