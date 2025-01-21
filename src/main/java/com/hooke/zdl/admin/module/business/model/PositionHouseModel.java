package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PositionHouseModel {
    /**
     * 姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 房产名称
     */
    private String houseName;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 房子 ID
     */
    private Integer houseId;

    /**
     * 加息券 ID
     */
    private Integer couponId;

    /**
     * 项目周期
     */
    private Integer projectPeriod;

    /**
     * 收益率
     */
    private BigDecimal yieldRate;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易份数
     */
    private Integer copiesNum;

    /**
     * 合同 ID
     */
    private String contractId;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 结算日期
     */
    private LocalDate settlementDate;

    /**
     * 是否已结算
     */
    private Boolean settlementFlag;

    /**
     * 分享码
     */
    private String shareCode;

    /**
     * 总收益
     */
    private BigDecimal totalYield;

    /**
     * 搜索条件
     */
    private String searchText;
}
