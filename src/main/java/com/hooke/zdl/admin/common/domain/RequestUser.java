package com.hooke.zdl.admin.common.domain;

import com.hooke.zdl.admin.common.enumeration.UserTypeEnum;

/**
 * 请求用户
 */
public interface RequestUser {

    /**
     * 请求用户id
     */
    Long getUserId();

    /**
     * 请求用户名称
     */
    String getUserName();

    /**
     * 获取用户类型
     */
    UserTypeEnum getUserType();

    /**
     * 获取请求的IP
     */
    String getIp();

    /**
     * 获取请求 user-agent
     */
    String getUserAgent();

}
