package com.hooke.zdl.admin.module.system.role.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.hooke.zdl.admin.module.system.role.dao.RoleMenuDao;
import com.hooke.zdl.admin.module.system.role.domain.entity.RoleMenuEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色-菜单 manager
 */
@Service
public class RoleMenuManager extends ServiceImpl<RoleMenuDao, RoleMenuEntity> {

    @Resource
    private RoleMenuDao roleMenuDao;

    /**
     * 更新角色权限
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenu(Long roleId, List<RoleMenuEntity> roleMenuEntityList) {
        // 根据角色ID删除菜单权限
        roleMenuDao.deleteByRoleId(roleId);
        // 批量添加菜单权限
        saveBatch(roleMenuEntityList);
    }
}
