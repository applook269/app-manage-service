package com.hooke.zdl.admin.module.support.loginlog;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.hooke.zdl.admin.module.support.loginlog.domain.LoginLogEntity;
import com.hooke.zdl.admin.module.support.loginlog.domain.LoginLogQueryForm;
import com.hooke.zdl.admin.module.support.loginlog.domain.LoginLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 登录日志
 */
@Mapper
@Component
public interface LoginLogDao extends BaseMapper<LoginLogEntity> {

    /**
     * 分页查询
     */
    List<LoginLogVO> queryByPage(Page page, @Param("query") LoginLogQueryForm queryForm);

    /**
     * 查询上一个登录记录
     */
    LoginLogVO queryLastByUserId(@Param("userId") Long userId, @Param("userType") Integer userType, @Param("loginLogResult") Integer loginLogResult);


}
