package com.xo.common.utils.api;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    REQUEST_SUCCESS(1, "请求成功"),
    REQUEST_ERROR(0, "请求错误"),
    SERVER_ERROR(-1, "服务器出现异常"),
    UNAUTHENTICATED(-2, "需要重新登录"),
    UNAUTHORIZED(-3, "没有权限"),
    BIND_PHONE(-4, "需要绑定手机号"),
    BIND_WX(-5, "需要绑定微信号");
    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
