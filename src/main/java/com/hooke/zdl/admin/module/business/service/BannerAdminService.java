package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.BannerMapper;
import com.hooke.zdl.admin.module.business.entity.Banner;
import com.hooke.zdl.admin.module.business.model.BannerModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.BannerTableDef.BANNER;

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

    public PageResult<BannerModel> pageBanner(BannerModel banner, PageParam pageParam) {
        Page<BannerModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<BannerModel> bannerModelPage = QueryChain.of(Banner.class)
                .where(BANNER.TITLE.like(banner.getSearchText())
                        .or(BANNER.LINK.like(banner.getSearchText())))
                .pageAs(page, BannerModel.class);
        return SmartPageUtil.convert2PageResult(page, bannerModelPage.getRecords(), BannerModel.class);
    }

}
