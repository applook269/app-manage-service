package com.hooke.zdl.admin.module.system.role.domain.entity;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色 菜单
 */
@Data
@Table("zdl_admin_role_menu")
public class RoleMenuEntity {

    /**
     * 主键id
     */
    @Id(keyType = KeyType.Auto)
    private Long roleMenuId;

    /**
     * 角色 id
     */
    private Long roleId;

    /**
     * 菜单 id
     */
    private Long menuId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
