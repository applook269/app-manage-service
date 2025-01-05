package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_feedback")
public class Feedback extends BaseEntity{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 状态 0 未处理 1 已处理
     */
    private String status;
}
