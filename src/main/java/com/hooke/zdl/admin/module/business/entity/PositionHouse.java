package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_position_house")
public class PositionHouse extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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

}
