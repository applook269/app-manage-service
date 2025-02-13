package com.hooke.zdl.admin.module.support.config;

import com.hooke.zdl.admin.common.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统配置常量类
 */
@Getter
@AllArgsConstructor
public enum ConfigKeyEnum implements BaseEnum {

    /**
     * 万能密码
     */
    SUPER_PASSWORD("super_password", "万能密码"),

    LEVEL3_PROTECT_CONFIG("level3_protect_config", "三级等保配置"),
    ;

    private final String value;

    private final String desc;
}
