package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_product_house")
public class ProductHouse extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 封面图片 ID
     */
    private String photo;

    /**
     * 推荐类型（精选资产、限时特选）
     */
    private String recommendType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 合同模版 ID
     */
    private String contractTemplate;

    /**
     * 标签
     */
    private String tags;

    /**
     * 项目总额
     */
    private BigDecimal projectPrice;

    /**
     * 项目进度
     */
    private String projectProgress;

    /**
     * 起投金额
     */
    private BigDecimal minPrice;

    /**
     * 上限金额
     */
    private BigDecimal maxPrice;

    /**
     * 当前利率
     */
    private BigDecimal yieldRate;

    /**
     * 项目周期
     */
    private Integer projectPeriod;

    /**
     * 限购VIP等级
     */
    private String vipLimit;

    /**
     * 返款方式
     */
    private String repaymentWay;

    /**
     * 能否复投
     */
    private String allowReinvest;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 担保公司
     */
    private String guaranteeCompany;

    /**
     * 担保公司描述
     */
    private String guaranteeCompanyDesc;

    /**
     * 流转方式
     */
    private String circulationWay;

    /**
     * 物业类型
     */
    private String propertyType;

    /**
     * 资产用途
     */
    private String assetsUsage;

    /**
     * 建筑面积
     */
    private String buildingArea;

    /**
     * 建筑地址
     */
    private String address;

    /**
     * 报名人数
     */
    private String registerNumber;

    /**
     * 投资次数
     */
    private String investNumber;

    /**
     * 围观次数
     */
    private String considerCompany;

    /**
     * 分享奖励
     */
    private String shareReward;

    /**
     * 分享速度
     */
    private String shareSpeedUp;

    /**
     * 删除标记
     */
    private String deleteFlag;

}
