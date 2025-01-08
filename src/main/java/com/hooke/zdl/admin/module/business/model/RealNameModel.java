package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RealNameModel {
    private Integer id;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 认证视频 ID
     */
    private String video;

    /**
     * 身份证正面 ID
     */
    private String idCardFront;

    /**
     * 身份证反面 ID
     */
    private String idCardBack;

    /**
     * 身份证过期日期
     */
    private String idCardExpiredDate;

    /**
     * 实名认证状态
     */
    private String status;

    private LocalDateTime insertTime;

}
