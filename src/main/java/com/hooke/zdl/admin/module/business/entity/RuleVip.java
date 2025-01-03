package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_rule_vip")
public class RuleVip extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * vip 等级
     */
    private Integer vipLevel;

    /**
     * 资产配额
     */
    private BigDecimal assetsAmount;

    /**
     * 资产返佣
     */
    private BigDecimal assetsCommission;

    /**
     * 好友等级
     */
    private Integer friendLevel;

    /**
     * 佣金返利
     */
    private String friendRebate;

}
