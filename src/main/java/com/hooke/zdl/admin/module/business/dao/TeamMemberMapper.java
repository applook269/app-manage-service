package com.hooke.zdl.admin.module.business.dao;

import com.mybatisflex.core.BaseMapper;
import com.hooke.zdl.admin.module.business.entity.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TeamMemberMapper extends BaseMapper<TeamMember> {

}
