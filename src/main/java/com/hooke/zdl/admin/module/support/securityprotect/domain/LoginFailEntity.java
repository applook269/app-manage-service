package com.hooke.zdl.admin.module.support.securityprotect.domain;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录失败记录
 */

@Data
@Builder
@Table("zdl_admin_login_fail")
public class LoginFailEntity {


    @Id(keyType = KeyType.Auto)
    private Long loginFailId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 锁定状态
     */
    private Boolean lockFlag;

    /**
     * 登录失败次数
     */
    private Integer loginFailCount;

    /**
     * 连续登录失败锁定开始时间
     */
    private LocalDateTime loginLockBeginTime;


    private LocalDateTime updateTime;

    private LocalDateTime createTime;


}
