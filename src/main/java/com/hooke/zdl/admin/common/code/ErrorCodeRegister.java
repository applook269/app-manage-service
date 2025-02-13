package com.hooke.zdl.admin.common.code;

import static com.hooke.zdl.admin.common.code.ErrorCodeRangeContainer.register;

/**
 * 注册code状态码 <br>
 * ps：为什么要在此处不那么优雅的手动注册？
 * 主要是为了能统一、清晰、浏览当前定义的所有状态码
 * 方便后续维护
 */
public class ErrorCodeRegister {

    static {

        // 系统 错误码
        register(SystemErrorCode.class, 10001, 20000);

        // 意外 错误码
        register(UnexpectedErrorCode.class, 20001, 30000);

        // 用户 通用错误码
        register(UserErrorCode.class, 30001, 40000);

    }


    public static int initialize() {
        return ErrorCodeRangeContainer.initialize();
    }

    public static void main(String[] args) {
        ErrorCodeRegister.initialize();
    }

}
