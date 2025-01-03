package com.hooke.zdl.admin.module.system.position.domain.entity;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 职务表 实体类
 */

@Data
@Table("zdl_admin_position")
public class PositionEntity {

    /**
     * 职务ID
     */
    @Id(keyType = KeyType.Auto)
    private Long positionId;

    /**
     * 职务名称
     */
    private String positionName;

    /**
     * 职级
     */
    private String level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    private Boolean deletedFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}