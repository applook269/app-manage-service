package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Feedback;
import com.hooke.zdl.admin.module.business.model.FeedbackModel;
import com.hooke.zdl.admin.module.business.service.FeedbackAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/feedback")
@Tag(name = "字典配置", description = "字典配置")
public class FeedbackAdminController {
    @Autowired
    private FeedbackAdminService feedbackAdminService;

    @Operation(summary = "字典配置分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<FeedbackModel>> pageFeedback(FeedbackModel feedback, PageParam pageParam) {
        PageResult<FeedbackModel> feedbackPageResult = feedbackAdminService.pageFeedback(feedback, pageParam);
        return ResponseDTO.ok(feedbackPageResult);
    }

    @Operation(summary = "新增字典配置")
    @PostMapping("/add")
    public ResponseDTO<Void> addFeedback(@RequestBody Feedback feedback) {
        feedbackAdminService.addFeedback(feedback);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新字典配置")
    @PostMapping("/edit")
    public ResponseDTO<Void> editFeedback(@RequestBody Feedback feedback) {
        feedbackAdminService.editFeedback(feedback);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除字典配置")
    @PostMapping("/remove")
    public ResponseDTO<Void> removeFeedback(@RequestBody Feedback feedback) {
        feedbackAdminService.removeFeedback(feedback);
        return ResponseDTO.ok();
    }

}
