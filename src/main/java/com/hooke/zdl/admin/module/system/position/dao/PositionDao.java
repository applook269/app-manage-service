package com.hooke.zdl.admin.module.system.position.dao;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.hooke.zdl.admin.module.system.position.domain.entity.PositionEntity;
import com.hooke.zdl.admin.module.system.position.domain.form.PositionQueryForm;
import com.hooke.zdl.admin.module.system.position.domain.vo.PositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 职务表 Dao
 */

@Mapper
@Component
public interface PositionDao extends BaseMapper<PositionEntity> {

    /**
     * 分页 查询
     */
    List<PositionVO> queryPage(Page page, @Param("queryForm") PositionQueryForm queryForm);


    /**
     * 查询
     */
    List<PositionVO> queryList(@Param("deletedFlag") Boolean deletedFlag);
}
