package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class FeedbackModel {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String name;

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

    private String searchText;
}
