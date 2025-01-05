package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.NoticeMapper;
import com.hooke.zdl.admin.module.business.entity.Notice;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NoticeAdminService extends ServiceImpl<NoticeMapper, Notice> {
    public PageResult<Notice> pageNotice(Notice notice, PageParam pageParam) {
        Page<Notice> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = QueryWrapper.create(notice);
        Page<Notice> paged = page(page, queryWrapper);
        return SmartPageUtil.convert2PageResult(page, paged.getRecords(), Notice.class);
    }

    public void addNotice(Notice notice) {
        save(notice);
    }

    public void editNotice(Notice notice) {
        updateById(notice);
    }

    public void removeNotice(Notice notice) {
        removeById(notice.getId());
    }
}
