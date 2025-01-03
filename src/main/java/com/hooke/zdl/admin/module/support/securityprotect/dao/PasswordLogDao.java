package com.hooke.zdl.admin.module.support.securityprotect.dao;

import com.mybatisflex.core.BaseMapper;
import com.hooke.zdl.admin.module.support.securityprotect.domain.PasswordLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PasswordLogDao extends BaseMapper<PasswordLogEntity> {

    /**
     * 查询最后一次修改密码记录
     */
    PasswordLogEntity selectLastByUserTypeAndUserId(@Param("userType") Integer userType, @Param("userId") Long userId);


    /**
     * 查询最近几次修改后的密码
     */
    List<String> selectOldPassword(@Param("userType") Integer userType, @Param("userId") Long userId, @Param("limit") int limit);

}
