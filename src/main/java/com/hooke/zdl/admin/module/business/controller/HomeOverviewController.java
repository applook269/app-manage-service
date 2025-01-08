package com.hooke.zdl.admin.module.business.controller;

import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.model.HomeOverviewModel;
import com.hooke.zdl.admin.module.business.service.HomeOverviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/home-overview")
@Tag(name = "首页总览", description = "首页总览")
public class HomeOverviewController {
    @Autowired
    private HomeOverviewService homeOverviewService;

    @Operation(summary = "获取总览")
    @GetMapping("/get")
    public ResponseDTO<HomeOverviewModel> getHomeOverview() {
        HomeOverviewModel homeOverview = homeOverviewService.getHomeOverview();
        return ResponseDTO.ok(homeOverview);
    }
}
