package com.yanghi.haimusic.utils;

/**
 * 枚举状态类
 */
public enum ResultCode {
    /**
     * 通用状态码
     */
    SUCCESS(1,"OK"),
    FAILED(-1,"FAIL"),
    /*
    参数错误状态码
     */
    PARAM_IS_INVAlID(101,"参数无效"),
    PARAM_IS_BLANK(101,"参数为空"),
    /* 用户错误  201 - 299  */
    USER_NOT_LOGIN(201,"未登录"),
    USER_NOT_EXIST(202,"用户不存在"),
    USER_LOGIN_ERROR(203,"登陆失败，账号或者密码有误"),
    NOT_PERMISSION(204,"无权限访问"),
    /* 业务错误 301 - 399*/
    DATA_NOT_FOUND(301,"没有数据");

    //返回状态码
    private Integer code;

    //返回消息
    private String msg;

    ResultCode() {
    }

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
