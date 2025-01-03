package com.hooke.zdl.admin.module.support.loginlog.domain;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志
 */
@Table("zdl_admin_login_log")
@Data
@Builder
public class LoginLogEntity {

    @Id(keyType = KeyType.Auto)
    private Long loginLogId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录ip地区
     */
    private String loginIpRegion;

    /**
     * user-agent
     */
    private String userAgent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录类型
     */
    private Integer loginResult;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

}
