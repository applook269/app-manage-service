package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_real_name_validate")
public class RealNameValidate extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    private Integer userId;

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

}
