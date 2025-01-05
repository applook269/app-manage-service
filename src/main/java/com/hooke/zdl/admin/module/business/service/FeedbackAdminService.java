package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.FeedbackMapper;
import com.hooke.zdl.admin.module.business.entity.Feedback;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FeedbackAdminService extends ServiceImpl<FeedbackMapper, Feedback> {
    public PageResult<Feedback> pageFeedback(Feedback feedback, PageParam pageParam) {
        Page<Feedback> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = QueryWrapper.create(feedback);
        Page<Feedback> paged = page(page, queryWrapper);
        return SmartPageUtil.convert2PageResult(page, paged.getRecords(), Feedback.class);
    }

    public void addFeedback(Feedback feedback) {
        save(feedback);
    }

    public void editFeedback(Feedback feedback) {
        updateById(feedback);
    }

    public void removeFeedback(Feedback feedback) {
        removeById(feedback.getId());
    }
}
