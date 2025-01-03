package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_article")
public class Article extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面
     */
    private String cover;

    /**
     * 类型
     */
    private String type;

    /**
     * 小类
     */
    private String subType;


    /**
     * 标签
     */
    private String tags;


    /**
     * 已读人数
     */
    private String readCount;


    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 首页热榜展示
     */
    private Boolean isHot;

    /**
     * 首页推荐展示
     */
    private Boolean isRecommend;

    /**
     * 是否必读
     */
    private Boolean isRequired;

}
