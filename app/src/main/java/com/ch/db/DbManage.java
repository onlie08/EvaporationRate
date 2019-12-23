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
        return parameters.get(0);
    }

    public void saveSensor(Sensor sensor){
        sensorDao.insertOrReplace(sensor);
    }

    public Sensor getSensor(){
        List<Sensor> sensors = sensorDao.loadAll();
        if(sensors.isEmpty()){
            return null;
        }
        return sensors.get(0);
    }

    public void saveBeanRTData(BeanRTData beanRTData){
        beanRTDataDao.insert(beanRTData);
    }

    public void saveTestProcess(TestProcess testProcess){
        testProcessDao.insert(testProcess);
    }

    public TestProcess getTestProcess(){
        List<TestProcess> testProcesses = testProcessDao.loadAll();
        if(testProcesses.isEmpty()){
            return null;
        }
        return testProcesses.get(0);
    }
}
