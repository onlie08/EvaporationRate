package com.ch.service.serialport;

import com.ch.service.bean.BeanRTData;

import top.keepempty.sph.library.DataConversion;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

public class SerialPortIO {

    public static final String Default_SerialPort_Name = "dev/ttyS2";
    public static final int Deafulat_SerialPort_PortalRate = 9600;

    public  SerialPortHelper serialPortHelper = null;

    /**
     * 初始化
     */
    public int initPort()
    {
        if(serialPortHelper!=null)
        {
            serialPortHelper = null;
        }

        serialPortHelper = new SerialPortHelper(100,true);
        boolean isOpen = serialPortHelper.openDevice(Default_SerialPort_Name,Deafulat_SerialPort_PortalRate);

        if(!isOpen)
            return 0;

        serialPortHelper.setSphResultCallback(mSphHander);
        return 1;
    }

    public interface OnSPDataCB{

        public void revData(String data);
    }
    protected OnSPDataCB mCbHandler = null;
    public void setOnSPDataCB(OnSPDataCB handle)
    {
        mCbHandler = handle;
    }

    public int sendCmdToSerialPort()
    {

        return 1;
    }

    protected SphResultCallback mSphHander = new SphResultCallback(){

        @Override
        public void onSendData(SphCmdEntity sendCom) {

            int a=0;
            a=10;
        }

        @Override
        public void onReceiveData(SphCmdEntity data) {
            String revStrData = data.commandsHex;
            byte[] revBytData = data.commands;

            mCbHandler.revData(revStrData);
        }

        @Override
        public void onComplete() {

        }
    };
    public int getSerialPortData()
    {

        byte[] sendCMD = new byte[]{0x01};
        SphCmdEntity comEntry = new SphCmdEntity();
        comEntry.commands = sendCMD; // 发送命令字节数组
        comEntry.flag = 0;         // 备用标识
        comEntry.commandsHex = DataConversion.encodeHexString(sendCMD);  // 发送十六进制字符串
        comEntry.timeOut = 500;       // 超时时间 ms
        comEntry.reWriteCom = false;  // 超时是否重发 默认false
        comEntry.reWriteTimes = 1;    // 重发次数
        comEntry.receiveCount = 1;    // 接收数据条数，默认为1
        serialPortHelper.addCommands(comEntry);
        return 1;
    }

    //发送命令到设备 02-设置介质为lng 03-设置介质为n2 04-加热打开 05 -加热关闭
    public int sendCMDToDevice(byte cmdtype)
    {
        byte[] sendCMD = new byte[]{cmdtype};
        SphCmdEntity comEntry = new SphCmdEntity();
        comEntry.commands = sendCMD; // 发送命令字节数组
        comEntry.flag = 0;         // 备用标识
        comEntry.commandsHex = DataConversion.encodeHexString(sendCMD);  // 发送十六进制字符串
        comEntry.timeOut = 500;       // 超时时间 ms
        comEntry.reWriteCom = false;  // 超时是否重发 默认false
        comEntry.reWriteTimes = 1;    // 重发次数
        comEntry.receiveCount = 1;    // 接收数据条数，默认为1
        serialPortHelper.addCommands(comEntry);

        return 1;
    }



}
