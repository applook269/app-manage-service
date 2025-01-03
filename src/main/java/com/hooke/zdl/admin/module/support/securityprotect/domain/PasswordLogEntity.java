package com.hooke.zdl.admin.module.support.securityprotect.domain;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 */
@Data
@Table("zdl_admin_password_log")
public class PasswordLogEntity {

    /**
     * 主键id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    private Integer userType;

    private Long userId;

    private String oldPassword;

    private String newPassword;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}