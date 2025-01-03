package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.ProductHouseMapper;
import com.hooke.zdl.admin.module.business.entity.ProductHouse;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HouseAdminService extends ServiceImpl<ProductHouseMapper, ProductHouse> {
    public PageResult<ProductHouse> pageHouse(ProductHouse productHouse, PageParam pageParam) {
        Page<ProductHouse> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<ProductHouse> walletPage = page(page, QueryWrapper.create(productHouse));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), ProductHouse.class);
    }

    public void addHouse(ProductHouse productHouse) {
        save(productHouse);
    }

    public void editHouse(ProductHouse productHouse) {
        updateById(productHouse);
    }

    public void removeHouse(ProductHouse productHouse) {
        removeById(productHouse.getId());
    }
}
