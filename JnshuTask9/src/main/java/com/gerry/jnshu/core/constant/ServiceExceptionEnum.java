package com.gerry.jnshu.core.constant;

public enum ServiceExceptionEnum {


    // ========== 系统级别 ==========
    SUCCESS(0, "成功"),
    SYS_ERROR(500, "服务端异常"),
    MISSING_REQUEST_PARAM_ERROR(400, "参数异常"),

    // ========== 用户模块 ==========
    USER_NOT_FOUND(-1, "用户不存在"),
    USER_NOT_MATCH(-1, "账号密码不匹配"),
    USER_ACCOUNT_ERROR(-1, "账号异常"),
    USER_EXIST(-1, "手机号已注册"),
    AUTHORIZED_ERROR(403,"用户验证失败"),
    AUTHENTICATION_ERROR(401,"用户失败");

    // ========== 订单模块 ==========

    // ========== 商品模块 ==========
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误提示
     */
    private final String message;

    ServiceExceptionEnum(int code, String message) {
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
