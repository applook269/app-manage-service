package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_wallet_trans_dtl")
public class WalletTransDtl extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 交易流水号
     */
    private String transactionNo;

    /**
     * 交易名称
     */
    private String name;

    /**
     * 交易状态
     */
    private String status;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 交易类型（提现/充值/购买/赎回）
     */
    private String type;

    /**
     * 交易方向（收入/支出）
     */
    private String direction;

    /**
     * 商品说明
     */
    private String goodsDescribe;

    /**
     * 对方账户
     */
    private String oppositeAccount;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 证明材料
     */
    private String proof;

    /**
     * 交易渠道
     */
    private String chanel;

    /**
     * 产品 ID
     */
    private Integer productId;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

}
