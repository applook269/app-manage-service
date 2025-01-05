package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Notice;
import com.hooke.zdl.admin.module.business.service.NoticeAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/notice")
@Tag(name = "通知配置", description = "通知配置")
public class NoticeAdminController {
    @Autowired
    private NoticeAdminService noticeAdminService;

    @Operation(summary = "通知配置分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Notice>> pageNotice(Notice notice, PageParam pageParam) {
        PageResult<Notice> noticePageResult = noticeAdminService.pageNotice(notice, pageParam);
        return ResponseDTO.ok(noticePageResult);
    }

    @Operation(summary = "新增通知配置")
    @PostMapping("/add")
    public ResponseDTO<Void> addNotice(@RequestBody Notice notice) {
        noticeAdminService.addNotice(notice);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新通知配置")
    @PostMapping("/edit")
    public ResponseDTO<Void> editNotice(@RequestBody Notice notice) {
        noticeAdminService.editNotice(notice);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除通知配置")
    @PostMapping("/remove")
    public ResponseDTO<Void> removeNotice(@RequestBody Notice notice) {
        noticeAdminService.removeNotice(notice);
        return ResponseDTO.ok();
    }

}
