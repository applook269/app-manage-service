package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.ProductXyt;
import com.hooke.zdl.admin.module.business.entity.ProductXytOption;
import com.hooke.zdl.admin.module.business.service.XytAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/xyt")
@Tag(name = "小盈通", description = "小盈通")
public class XytAdminController {
    @Autowired
    private XytAdminService xytAdminService;

    @Operation(summary = "小盈通分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<ProductXyt>> pageXyt(ProductXyt model, PageParam pageParam) {
        PageResult<ProductXyt> models = xytAdminService.pageXyt(model, pageParam);
        return ResponseDTO.ok(models);
    }

    @Operation(summary = "新增小盈通")
    @PostMapping("/add")
    public ResponseDTO<Void> addXyt(@RequestBody ProductXyt model) {
        xytAdminService.addXyt(model);
        return ResponseDTO.ok();
    }

    @Operation(summary = "编辑小盈通")
    @PostMapping("/edit")
    public ResponseDTO<Void> editXyt(@RequestBody ProductXyt model) {
        xytAdminService.editXyt(model);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除小盈通")
    @PostMapping("/remove")
    public ResponseDTO<Void> removeXyt(@RequestBody ProductXyt model) {
        xytAdminService.removeXyt(model);
        return ResponseDTO.ok();
    }

    @Operation(summary = "查询小盈通选项")
    @GetMapping("/get-options")
    public ResponseDTO<List<ProductXytOption>> getOption(ProductXytOption option) {
        List<ProductXytOption> list = xytAdminService.getOption(option);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "设置小盈通选项")
    @PostMapping("/set-options")
    public ResponseDTO<Void> setOption(@RequestBody List<ProductXytOption> options) {
        xytAdminService.setOption(options);
        return ResponseDTO.ok();
    }
}
