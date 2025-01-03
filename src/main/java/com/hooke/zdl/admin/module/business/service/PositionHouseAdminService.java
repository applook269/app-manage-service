package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.PositionHouseMapper;
import com.hooke.zdl.admin.module.business.entity.PositionHouse;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PositionHouseAdminService extends ServiceImpl<PositionHouseMapper, PositionHouse> {
    public PageResult<PositionHouse> pagePositionHouse(PositionHouse positionHouse, PageParam pageParam) {
        Page<PositionHouse> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<PositionHouse> walletPage = page(page, QueryWrapper.create(positionHouse));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), PositionHouse.class);
    }
}
