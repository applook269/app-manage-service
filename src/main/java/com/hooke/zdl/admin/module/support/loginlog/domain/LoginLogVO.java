package com.hooke.zdl.admin.module.support.loginlog.domain;

import com.hooke.zdl.admin.common.enumeration.UserTypeEnum;
import com.hooke.zdl.admin.common.swagger.SchemaEnum;
import com.hooke.zdl.admin.module.support.loginlog.LoginLogResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志
 */
@Data
public class LoginLogVO {

    private Long loginLogId;

    @Schema(description = "用户id")
    private Long userId;

    @SchemaEnum(value = UserTypeEnum.class, desc = "用户类型")
    private Integer userType;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "登录ip")
    private String loginIp;

    @Schema(description = "登录ip地区")
    private String loginIpRegion;

    @Schema(description = "user-agent")
    private String userAgent;

    @Schema(description = "remark")
    private String remark;

    @SchemaEnum(LoginLogResultEnum.class)
    private Integer loginResult;

    private LocalDateTime createTime;

}
