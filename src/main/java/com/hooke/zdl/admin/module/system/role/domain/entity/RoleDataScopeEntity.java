package com.hooke.zdl.admin.module.system.role.domain.entity;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.hooke.zdl.admin.module.system.datascope.constant.DataScopeTypeEnum;
import com.hooke.zdl.admin.module.system.datascope.constant.DataScopeViewTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据范围与角色关系
 */
@Data
@Table("zdl_admin_role_data_scope")
public class RoleDataScopeEntity {
    /**
     * 主键id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;
    /**
     * 数据范围id
     * {@link DataScopeTypeEnum}
     */
    private Integer dataScopeType;
    /**
     * 数据范围类型
     * {@link DataScopeViewTypeEnum}
     */
    private Integer viewType;
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
