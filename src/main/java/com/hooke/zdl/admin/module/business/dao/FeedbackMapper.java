package com.hooke.zdl.admin.module.business.dao;

import com.hooke.zdl.admin.module.business.entity.Feedback;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
