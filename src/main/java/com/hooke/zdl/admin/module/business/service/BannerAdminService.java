package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.BannerMapper;
import com.hooke.zdl.admin.module.business.entity.Banner;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BannerAdminService extends ServiceImpl<BannerMapper, Banner> {
    public void addBanner(Banner banner) {
        save(banner);
    }

    public void editBanner(Banner banner) {
        updateById(banner);
    }

    public void removeBanner(Banner banner) {
        removeById(banner.getId());
    }

    public PageResult<Banner> pageBanner(Banner banner, PageParam pageParam) {
        Page<Banner> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Banner> walletPage = page(page, QueryWrapper.create(banner));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), Banner.class);
    }

}
