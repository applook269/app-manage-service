package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartBeanUtil;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.PositionXytMapper;
import com.hooke.zdl.admin.module.business.entity.PositionXyt;
import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.model.PositionXytModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.hooke.zdl.admin.module.business.entity.table.PositionXytTableDef.POSITION_XYT;
import static com.hooke.zdl.admin.module.business.entity.table.ProductXytTableDef.PRODUCT_XYT;
import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;
import static com.hooke.zdl.admin.module.business.entity.table.WalletTableDef.WALLET;

@Service
public class PositionXytAdminService extends ServiceImpl<PositionXytMapper, PositionXyt> {
    public PageResult<PositionXytModel> pagePositionXyt(PositionXytModel model, PageParam pageParam) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(POSITION_XYT.ALL_COLUMNS, USER.ALL_COLUMNS, WALLET.BALANCE, PRODUCT_XYT.NAME.as("productName"))
                .from(POSITION_XYT)
                .leftJoin(USER).on(USER.ID.eq(POSITION_XYT.USER_ID))
                .leftJoin(WALLET).on(USER.ID.eq(WALLET.USER_ID))
                .leftJoin(PRODUCT_XYT).on(PRODUCT_XYT.ID.eq(POSITION_XYT.PRODUCT_ID))
                .where(USER.NAME.like(model.getSearchText()).or(USER.PHONE_NO.like(model.getSearchText())));
        Page<PositionXytModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<PositionXytModel> walletPage = pageAs(page, queryWrapper, PositionXytModel.class);
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords());
    }
}
