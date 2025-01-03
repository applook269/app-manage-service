package com.hooke.zdl.admin.module.support.operatelog;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.hooke.zdl.admin.module.support.operatelog.domain.OperateLogEntity;
import com.hooke.zdl.admin.module.support.operatelog.domain.OperateLogQueryForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 操作日志
 */
@Mapper
@Component
public interface OperateLogDao extends BaseMapper<OperateLogEntity> {

    /**
     * 分页查询
     */
    List<OperateLogEntity> queryByPage(Page page, @Param("query") OperateLogQueryForm queryForm);


    /**
     * 批量删除
     */
    void deleteByIds(@Param("idList") List<Long> idList);
}
