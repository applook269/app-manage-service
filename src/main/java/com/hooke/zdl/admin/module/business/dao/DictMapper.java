package com.hooke.zdl.admin.module.business.dao;

import com.hooke.zdl.admin.module.business.entity.Dict;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DictMapper extends BaseMapper<Dict> {

}
