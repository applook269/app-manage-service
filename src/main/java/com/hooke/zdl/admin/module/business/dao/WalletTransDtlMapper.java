package com.hooke.zdl.admin.module.business.dao;


import com.mybatisflex.core.BaseMapper;
import com.hooke.zdl.admin.module.business.entity.WalletTransDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface WalletTransDtlMapper extends BaseMapper<WalletTransDtl> {
    WalletTransDtl getTotalRecharge(@Param("userId") Integer userId);
}
