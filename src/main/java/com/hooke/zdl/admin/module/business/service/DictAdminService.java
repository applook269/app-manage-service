package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.DictMapper;
import com.hooke.zdl.admin.module.business.entity.Banner;
import com.hooke.zdl.admin.module.business.entity.Dict;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DictAdminService extends ServiceImpl<DictMapper, Dict> {
    public PageResult<Dict> pageDict(Dict dict, PageParam pageParam) {
        Page<Dict> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = QueryWrapper.create(dict);
        Page<Dict> paged = page(page, queryWrapper);
        return SmartPageUtil.convert2PageResult(page, paged.getRecords(), Dict.class);
    }

    public void addDict(Dict dict) {
        save(dict);
    }

    public void editDict(Dict dict) {
        updateById(dict);
    }

    public void removeDict(Dict dict) {
        removeById(dict.getId());
    }
}
