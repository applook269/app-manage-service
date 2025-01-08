package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_recharge_config")
public class RechargeConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 充值类型
     */
    private String type;

    /**
     * 充值名称
     */
    private String name;

    /**
     * 充值图标
     */
    private String icon;

    /**
     * 充值详情
     */
    private String detail;

}
