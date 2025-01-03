package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_user_user_user_task")
public class UserTask extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String content;

    /**
     * 任务类型（每日任务/每月任务）
     */
    private String type;

    /**
     * 目标分数
     */
    private Integer targetScope;

    /**
     * 任务状态
     */
    private String status;

}
