package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PositionXytModel {
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

    /**
     * 姓名
     */
    private String name;

    /**
     * 启用状态
     */
    private Boolean enable;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 是否羊毛用户
     */
    private Boolean isWool;

    /**
     * 余额
     */
    private BigDecimal balance;
}
