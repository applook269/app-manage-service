package com.hooke.zdl.admin.module.support.datatracer.domain.entity;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.hooke.zdl.admin.module.support.datatracer.constant.DataTracerTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据记录 实体
 */
@Data
@Table("zdl_admin_data_tracer")
public class DataTracerEntity {

    @Id(keyType = KeyType.Auto)
    private Long dataTracerId;
    /**
     * 数据id
     */
    private Long dataId;
    /**
     * 业务类型
     * {@link DataTracerTypeEnum}
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * diff 差异：旧的数据
     */
    private String diffOld;

    /**
     * 差异：新的数据
     */
    private String diffNew;

    /**
     * 扩展字段
     */
    private String extraData;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求ip地区
     */
    private String ipRegion;

    /**
     * 请求头
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
