package com.hooke.zdl.admin.module.system.menu.dao;

import com.mybatisflex.core.BaseMapper;
import com.hooke.zdl.admin.module.system.menu.domain.entity.MenuEntity;
import com.hooke.zdl.admin.module.system.menu.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单 dao
 */
@Mapper
@Component
public interface MenuDao extends BaseMapper<MenuEntity> {

    /**
     * 根据名称查询同一级下的菜单
     */
    MenuEntity getByMenuName(@Param("menuName") String menuName, @Param("parentId") Long parentId, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 根据前端权限字符串查询菜单
     */
    MenuEntity getByWebPerms(@Param("webPerms") String webPerms, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 根据菜单ID删除菜单（逻辑删除）
     */
    void deleteByMenuIdList(@Param("menuIdList") List<Long> menuIdList, @Param("updateUserId") Long updateUserId, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 查询菜单列表
     */
    List<MenuVO> queryMenuList(@Param("deletedFlag") Boolean deletedFlag, @Param("disabledFlag") Boolean disabledFlag, @Param("menuTypeList") List<Integer> menuTypeList);


    /**
     * 根据菜单ID 查询功能点列表
     */
    List<MenuEntity> getPointListByMenuId(@Param("menuId") Long menuId, @Param("menuType") Integer menuType, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 根据员工ID查询菜单列表
     */
    List<MenuVO> queryMenuByEmployeeId(@Param("deletedFlag") Boolean deletedFlag,
                                       @Param("disabledFlag") Boolean disabledFlag,
                                       @Param("employeeId") Long employeeId);

    /**
     * 根据菜单类型查询
     */
    List<MenuEntity> queryMenuByType(@Param("menuType") Integer menuType,
                                     @Param("deletedFlag") Boolean deletedFlag,
                                     @Param("disabledFlag") Boolean disabledFlag);

    /**
     * 查询孩子id
     */
    List<Long> selectMenuIdByParentIdList(@Param("menuIdList") List<Long> menuIdList);
}
