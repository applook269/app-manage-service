package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.FeedbackMapper;
import com.hooke.zdl.admin.module.business.entity.Feedback;
import com.hooke.zdl.admin.module.business.model.FeedbackModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.FeedbackTableDef.FEEDBACK;
import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;

@Service
public class FeedbackAdminService extends ServiceImpl<FeedbackMapper, Feedback> {
    public PageResult<FeedbackModel> pageFeedback(FeedbackModel feedback, PageParam pageParam) {
        Page<FeedbackModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(FEEDBACK.ALL_COLUMNS, USER.NAME)
                .from(FEEDBACK)
                .leftJoin(USER).on(USER.ID.eq(FEEDBACK.USER_ID))
                .where(USER.NAME.like(feedback.getSearchText())
                        .or(FEEDBACK.PHONE_NO.like(feedback.getSearchText()))
                        .or(FEEDBACK.CONTENT.like(feedback.getSearchText())));
        Page<FeedbackModel> modelPage = pageAs(page, queryWrapper, FeedbackModel.class);
        return SmartPageUtil.convert2PageResult(page, modelPage.getRecords());
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
