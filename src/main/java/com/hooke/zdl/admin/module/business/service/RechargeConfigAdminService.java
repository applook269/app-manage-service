package com.hooke.zdl.admin.module.business.service;

import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.RechargeConfigMapper;
import com.hooke.zdl.admin.module.business.entity.RechargeConfig;
import com.hooke.zdl.admin.module.business.entity.RechargeConfig;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RechargeConfigAdminService extends ServiceImpl<RechargeConfigMapper, RechargeConfig> {
    public void addRechargeConfig(RechargeConfig rechargeConfig) {
        save(rechargeConfig);
    }

    public void editRechargeConfig(RechargeConfig rechargeConfig) {
        updateById(rechargeConfig);
    }

    public void removeRechargeConfig(RechargeConfig rechargeConfig) {
        removeById(rechargeConfig.getId());
    }

    public PageResult<RechargeConfig> pageRechargeConfig(RechargeConfig rechargeConfig, PageParam pageParam) {
        Page<RechargeConfig> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<RechargeConfig> walletPage = page(page, QueryWrapper.create(rechargeConfig));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), RechargeConfig.class);
    }
}
