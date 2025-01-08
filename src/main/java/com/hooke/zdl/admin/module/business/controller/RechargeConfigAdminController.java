package com.hooke.zdl.admin.module.business.controller;

import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.RechargeConfig;
import com.hooke.zdl.admin.module.business.service.RechargeConfigAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/recharge-config")
@Tag(name = "充值方式", description = "充值方式")
public class RechargeConfigAdminController {
    @Autowired
    private RechargeConfigAdminService rechargeConfigAdminService;
    
    @Operation(summary = "新增充值方式")
    @PostMapping("/add")
    public ResponseDTO<String> addRechargeConfig(@RequestBody RechargeConfig rechargeConfig) {
        rechargeConfigAdminService.addRechargeConfig(rechargeConfig);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "修改充值方式")
    @PostMapping("/edit")
    public ResponseDTO<String> editRechargeConfig(@RequestBody RechargeConfig rechargeConfig) {
        rechargeConfigAdminService.editRechargeConfig(rechargeConfig);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "删除充值方式")
    @PostMapping("/remove")
    public ResponseDTO<String> removeRechargeConfig(@RequestBody RechargeConfig rechargeConfig) {
        rechargeConfigAdminService.removeRechargeConfig(rechargeConfig);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "分页充值方式")
    @GetMapping("/page")
    public ResponseDTO<PageResult<RechargeConfig>> pageRechargeConfig(RechargeConfig rechargeConfig, PageParam pageParam) {
        PageResult<RechargeConfig> page = rechargeConfigAdminService.pageRechargeConfig(rechargeConfig, pageParam);
        return ResponseDTO.ok(page);
    }
}
