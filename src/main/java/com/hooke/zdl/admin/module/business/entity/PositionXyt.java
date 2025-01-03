package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_position_xyt")
public class PositionXyt extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 产品 ID
     */
    private Integer productId;

    /**
     * 持仓金额
     */
    private BigDecimal amount;

    /**
     * 本金
     */
    private BigDecimal principal;

    /**
     * 待确认金额
     */
    private BigDecimal unconfirmedAmount;

    /**
     * 收益率
     */
    private BigDecimal yieldRate;

    /**
     * 收益周期
     */
    private Integer yieldPeriod;

    /**
     * 购买日期
     */
    private LocalDateTime purchaseDate;

    /**
     * 持仓状态
     */
    private String status;

}
