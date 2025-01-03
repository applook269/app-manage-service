package com.hooke.zdl.admin.module.support.loginlog;

import com.hooke.zdl.admin.common.enumeration.BaseEnum;

/**
 * 登录类型
 */
public enum LoginLogResultEnum implements BaseEnum {

    LOGIN_SUCCESS(0, "登录成功"),
    LOGIN_FAIL(1, "登录失败"),
    LOGIN_OUT(2, "退出登录");

    private Integer type;
    private String desc;

    LoginLogResultEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return type;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
