package com.yanghi.haimusic.utils;

import java.io.Serializable;
import java.util.Objects;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;  //状态码
    private boolean success; //是否成功
    private String msg; //提示信息
    private T data; //数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    private static <T> Result<T> restResult(int code, String msg,T data) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setSuccess(code == 1);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


    public static <T> Result<T> ok() {
        return restResult( ResultCode.SUCCESS.getCode(),  ResultCode.SUCCESS.getMsg(),null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(),data);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(ResultCode.SUCCESS.getCode(), msg,data);
    }

    public static <T> Result<T> failed() {
        return restResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(),null);
    }
    public static <T> Result<T> failed(String msg) {
        return restResult(ResultCode.FAILED.getCode(), msg,null);
    }

    public static <T> Result<T> failed(ResultCode resultCode) {
        return restResult(resultCode.getCode(), resultCode.getMsg(),null);
    }
    public static <T> Result<T> failed(Integer code,String msg) {
        return restResult(code, msg,null);
    }

    public static <T> Result<T> failed(T data) {
        return restResult( ResultCode.FAILED.getCode(), ResultCode.SUCCESS.getMsg(),data);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return restResult(ResultCode.FAILED.getCode(), msg,data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return code == result.code && success == result.success && Objects.equals(msg, result.msg) && Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, success, msg, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}