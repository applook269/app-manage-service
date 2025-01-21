package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.PositionHouseMapper;
import com.hooke.zdl.admin.module.business.entity.PositionHouse;
import com.hooke.zdl.admin.module.business.model.PositionHouseModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.PositionHouseTableDef.POSITION_HOUSE;
import static com.hooke.zdl.admin.module.business.entity.table.ProductHouseTableDef.PRODUCT_HOUSE;
import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;

@Service
public class PositionHouseAdminService extends ServiceImpl<PositionHouseMapper, PositionHouse> {
    public PageResult<PositionHouseModel> pagePositionHouse(PositionHouseModel positionHouse, PageParam pageParam) {
        Page<PositionHouseModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = new QueryWrapper()
                .select(POSITION_HOUSE.ALL_COLUMNS, PRODUCT_HOUSE.NAME.as("houseName"), USER.NAME.as("userName"), USER.PHONE_NO.as("phoneNo"))
                .from(POSITION_HOUSE)
                .leftJoin(PRODUCT_HOUSE).on(PRODUCT_HOUSE.ID.eq(POSITION_HOUSE.HOUSE_ID))
                .leftJoin(USER).on(USER.ID.eq(POSITION_HOUSE.USER_ID))
                .where(USER.NAME.like(positionHouse.getSearchText())
                        .or(USER.PHONE_NO.like(positionHouse.getSearchText()))
                        .or(PRODUCT_HOUSE.NAME.like(positionHouse.getSearchText())));
        Page<PositionHouseModel> walletPage = pageAs(page, queryWrapper, PositionHouseModel.class);
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords());
    }
}
