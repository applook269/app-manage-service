package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.NoticeMapper;
import com.hooke.zdl.admin.module.business.entity.Notice;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.NoticeTableDef.NOTICE;

@Service
public class NoticeAdminService extends ServiceImpl<NoticeMapper, Notice> {
    public PageResult<Notice> pageNotice(Notice notice, PageParam pageParam) {
        Page<Notice> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Notice> noticePage = QueryChain.of(Notice.class)
                .where(NOTICE.TITLE.like(notice.getTitle()))
                .page(page);
        return SmartPageUtil.convert2PageResult(page, noticePage.getRecords(), Notice.class);
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
