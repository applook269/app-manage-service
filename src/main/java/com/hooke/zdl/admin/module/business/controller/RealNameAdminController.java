package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.RealNameValidate;
import com.hooke.zdl.admin.module.business.model.RealNameModel;
import com.hooke.zdl.admin.module.business.service.RealNameAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/real-name-validate")
@Tag(name = "实名认证", description = "实名认证")
public class RealNameAdminController {
    @Autowired
    private RealNameAdminService realNameAdminService;

    @Operation(summary = "查询实名认证列表")
    @GetMapping("/page")
    public ResponseDTO<PageResult<RealNameModel>> page(RealNameValidate model, PageParam pageParam) {
        PageResult<RealNameModel> models = realNameAdminService.pageRealName(model, pageParam);
        return ResponseDTO.ok(models);
    }

    @Operation(summary = "审核实名认证")
    @PostMapping("/review")
    public ResponseDTO<String> reviewRealName(@RequestBody RealNameValidate model) {
        realNameAdminService.reviewRealName(model);
        return ResponseDTO.ok("审核成功");
    }
}
