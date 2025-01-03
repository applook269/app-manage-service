package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_team")
public class Team extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 团队名称
     */
    private String name;

    /**
     * 团队队长
     */
    private Integer leaderId;

    /**
     * 团队等级
     */
    private Integer level;

    /**
     * 签到开始时间
     */
    private LocalDate signInStart;

    /**
     * 签到结束时间
     */
    private LocalDate signInEnd;

    /**
     * 团队升级日期
     */
    private LocalDate levelUpDate;

}
