package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.PositionXytMapper;
import com.hooke.zdl.admin.module.business.entity.PositionXyt;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PositionXytAdminService extends ServiceImpl<PositionXytMapper, PositionXyt> {
    public PageResult<PositionXyt> pagePositionXyt(PositionXyt positionXyt, PageParam pageParam) {
        Page<PositionXyt> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<PositionXyt> walletPage = page(page, QueryWrapper.create(positionXyt));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), PositionXyt.class);
    }
}
