package com.hooke.zdl.admin.module.business.service;

import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.ProductXytMapper;
import com.hooke.zdl.admin.module.business.dao.ProductXytOptionMapper;
import com.hooke.zdl.admin.module.business.entity.ProductXyt;
import com.hooke.zdl.admin.module.business.entity.ProductXytOption;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hooke.zdl.admin.module.business.entity.table.ProductXytTableDef.PRODUCT_XYT;

@Service
public class XytAdminService extends ServiceImpl<ProductXytMapper, ProductXyt> {
    @Autowired
    private ProductXytOptionMapper productXytOptionMapper;

    public PageResult<ProductXyt> pageXyt(ProductXyt productXyt, PageParam pageParam) {
        Page<ProductXyt> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<ProductXyt> xytPage = QueryChain.of(ProductXyt.class)
                .where(PRODUCT_XYT.NAME.like(productXyt.getName()))
                .page(page);
        return SmartPageUtil.convert2PageResult(page, xytPage.getRecords(), ProductXyt.class);
    }

    public void addXyt(ProductXyt productXyt) {
        save(productXyt);
    }

    public void editXyt(ProductXyt productXyt) {
        updateById(productXyt);
    }

    public void removeXyt(ProductXyt productXyt) {
        removeById(productXyt.getId());
    }

    public List<ProductXytOption> getOption(ProductXytOption option) {
        return productXytOptionMapper.selectListByQuery(QueryWrapper.create(option));
    }

    public void setOption(List<ProductXytOption> options) {
        if (!options.isEmpty()) {
            QueryWrapper wrapper = QueryWrapper.create(ProductXytOption.class)
                    .eq(ProductXytOption::getProductId, options.get(0).getProductId());
            productXytOptionMapper.deleteByQuery(wrapper);
            productXytOptionMapper.insertBatch(options);
        }
    }
}
