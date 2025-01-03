package com.hooke.zdl.admin.module.support.config;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.hooke.zdl.admin.module.support.config.domain.ConfigEntity;
import com.hooke.zdl.admin.module.support.config.domain.ConfigQueryForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数配置 zdl_admin_config Dao层
 */
@Component
@Mapper
public interface ConfigDao extends BaseMapper<ConfigEntity> {

    /**
     * 分页查询系统配置
     */
    List<ConfigEntity> queryByPage(Page page, @Param("query") ConfigQueryForm queryForm);

    /**
     * 根据key查询获取数据
     */
    ConfigEntity selectByKey(String key);
}
