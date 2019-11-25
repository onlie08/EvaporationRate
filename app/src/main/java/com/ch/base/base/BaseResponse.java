package com.ch.base.base;

import android.content.Context;

import java.util.List;

public class BaseResponse<T> {

    /**
     * 返回状态码
     */
    private int code;

    private String msg;

    /**
     * 返回数据result对象
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk(Context context) {
        if (code == 0) {
            return true;
        } else {
            //待补充异常统一处理
            return false;
        }
    }

}