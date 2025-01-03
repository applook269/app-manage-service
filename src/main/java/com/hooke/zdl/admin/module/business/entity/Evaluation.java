package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_evaluation")
public class Evaluation extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 团队长
     */
    private Integer leaderId;

    /**
     * 评价人
     */
    private Integer evaluatorId;

    /**
     * 评价人VIP等级
     */
    private Integer evaluatorVipLevel;

    /**
     * 总体评分
     */
    private Integer overallScore;

    /**
     * 领导能力评分
     */
    private Integer leadershipScore;

    /**
     * 工作效率评分
     */
    private Integer efficiencyScore;

    /**
     * 团队气氛评分
     */
    private Integer teamAtmosphereScore;

    /**
     * 沟通能力评分
     */
    private Integer communicationScore;

    /**
     * 支持指导评分
     */
    private Integer supportScore;

    /**
     * 评价时间
     */
    private LocalDateTime evaluatorTime;

    /**
     * 邀请时间
     */
    private LocalDateTime inviteTime;

    /**
     * 邀请次数
     */
    private Integer inviteNum;

    /**
     * 评价状态
     */
    private String status;

    /**
     * 是否通过
     */
    private Boolean passFlag;

}
