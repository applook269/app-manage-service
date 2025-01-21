package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerUserModel {
    private Integer id;

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
     * 出生日期
     */
    private String brithDate;

    /**
     * 性别
     */
    private String gender;

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
     * 登录密码
     */
    private String password;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 推荐人 ID
     */
    private Integer referenceUserId;

    /**
     * 推荐人手机号
     */
    private String referencePhoneNo;

    /**
     * 推荐人 ID
     */
    private String referenceUserInviteCode;

    /**
     * 头像ID
     */
    private String photo;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 团队等级
     */
    private Integer teamLevel;

    /**
     * 是否实名
     */
    private Boolean isRealName;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 充值金额
     */
    private BigDecimal totalRecharge;

    /**
     * 提现金额
     */
    private BigDecimal totalWithdrawal;

    /**
     * 购买金额
     */
    private BigDecimal totalPurchase;

    /**
     * 搜索条件
     */
    private String searchText;
}
