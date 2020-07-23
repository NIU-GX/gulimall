package com.atguigu.common.exception;

/**
 * @author NGX
 * @Date 2020/7/16 22:56
 */


public enum BizCodeEnum {
    //
    UNKONW_EXCEPTION(10000,"系统未知异常"),
    //
    VALID_EXCEPTION(10001,"参数格式检验失败");



    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
