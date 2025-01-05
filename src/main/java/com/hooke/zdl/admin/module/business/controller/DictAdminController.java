package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Dict;
import com.hooke.zdl.admin.module.business.service.DictAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dict")
@Tag(name = "字典配置", description = "字典配置")
public class DictAdminController {
    @Autowired
    private DictAdminService dictAdminService;

    @Operation(summary = "字典配置分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Dict>> pageDict(Dict dict, PageParam pageParam) {
        PageResult<Dict> dictPageResult = dictAdminService.pageDict(dict, pageParam);
        return ResponseDTO.ok(dictPageResult);
    }

    @Operation(summary = "新增字典配置")
    @PostMapping("/add")
    public ResponseDTO<Void> addDict(@RequestBody Dict dict) {
        dictAdminService.addDict(dict);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新字典配置")
    @PostMapping("/edit")
    public ResponseDTO<Void> editDict(@RequestBody Dict dict) {
        dictAdminService.editDict(dict);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除字典配置")
    @PostMapping("/remove")
    public ResponseDTO<Void> removeDict(@RequestBody Dict dict) {
        dictAdminService.removeDict(dict);
        return ResponseDTO.ok();
    }

}
