package com.ch.db;

import com.ch.base.base.BaseApplication;
import com.ch.bean.Parameter;
import com.ch.bean.ParameterDao;
import com.ch.bean.Sensor;
import com.ch.bean.SensorDao;
import com.ch.bean.TestProcess;
import com.ch.bean.TestProcessDao;
import com.ch.bean.User;
import com.ch.bean.UserDao;
import com.ch.service.bean.BeanRTData;
import com.ch.service.bean.BeanRTDataDao;

import java.util.List;

public class DbManage {

    ParameterDao parameterDao;
    UserDao userDao;
    SensorDao sensorDao;
    BeanRTDataDao beanRTDataDao;
    TestProcessDao testProcessDao;

    private static DbManage singleton = null;

    private DbManage() {
        userDao = BaseApplication.getDaoInstant().getUserDao();
        parameterDao = BaseApplication.getDaoInstant().getParameterDao();
        sensorDao = BaseApplication.getDaoInstant().getSensorDao();
        beanRTDataDao = BaseApplication.getDaoInstant().getBeanRTDataDao();
        testProcessDao = BaseApplication.getDaoInstant().getTestProcessDao();
    }

    public static DbManage getInstance() {
        if (singleton == null) {
            singleton = new DbManage();
        }
        return singleton;
    }

    public void saveUser(User user){
        userDao.insertOrReplace(user);
    }

    public User queryUser(String userName){
        return userDao.queryBuilder().where(UserDao.Properties.Name.eq(userName)).unique();
    }

    public void saveParamter(Parameter parameter){
        parameterDao.insertOrReplace(parameter);
    }

    public void updateParamter(Parameter parameter){
        parameterDao.update(parameter);
    }

    public Parameter getParamter(){
        List<Parameter> parameters = parameterDao.loadAll();
        if(parameters.isEmpty()){
            return null;
        }
        return parameters.get(parameters.size()-1);
    }

    public void saveSensor(Sensor sensor){
        sensorDao.insertOrReplace(sensor);
    }

    public Sensor getSensor(){
        List<Sensor> sensors = sensorDao.loadAll();
        if(sensors.isEmpty()){
            return null;
        }
        return sensors.get(sensors.size()-1);
    }

    public void saveBeanRTData(BeanRTData beanRTData){
        beanRTDataDao.insert(beanRTData);
    }

    public void saveTestProcess(TestProcess testProcess){
        testProcessDao.insertOrReplace(testProcess);
    }

    public TestProcess getTestProcess(){
        List<TestProcess> testProcesses = testProcessDao.loadAll();
        if(testProcesses.isEmpty()){
            return null;
        }
        return testProcesses.get(0);
    }

    public Parameter queryParamter(String s) {
        return parameterDao.queryBuilder().where(ParameterDao.Properties.DeviceId.eq(s)).unique();
    }

    public Sensor querySensor(String s) {
        return sensorDao.queryBuilder().where(SensorDao.Properties.DeviceId.eq(s)).unique();
    }

    public TestProcess queryTestProcess(String s) {
        return testProcessDao.queryBuilder().where(TestProcessDao.Properties.DeviceId.eq(s)).unique();
    }

    public List<TestProcess> queryLikeTestProcess(String s) {
        return testProcessDao.queryBuilder().where(TestProcessDao.Properties.DeviceId.eq(s)).list();
    }

    public List<TestProcess> queryAllTestProcess(int offset) {
//        return testProcessDao.queryBuilder().list();
        return testProcessDao.queryBuilder().offset(offset * 10).limit(10).list();
    }

    public List<BeanRTData> queryBeanRTData(String deviceId) {
        return beanRTDataDao.queryBuilder().where(BeanRTDataDao.Properties.DeviceId.eq(deviceId)).list();
    }

    public void delectTestProcess(Long key) {
        testProcessDao.deleteByKey(key);
    }

    public int queryTestProcessNum() {
        return testProcessDao.queryBuilder().list().size();
    }
}
