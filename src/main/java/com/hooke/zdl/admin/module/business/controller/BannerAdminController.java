package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Banner;
import com.hooke.zdl.admin.module.business.service.BannerAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/banner")
@Tag(name = "Banner", description = "Banner")
public class BannerAdminController {
    @Autowired
    private BannerAdminService bannerAdminService;

    @Operation(summary = "新增Banner")
    @PostMapping("/add")
    public ResponseDTO<String> addBanner(@RequestBody Banner banner) {
        bannerAdminService.addBanner(banner);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "修改Banner")
    @PostMapping("/edit")
    public ResponseDTO<String> editBanner(@RequestBody Banner banner) {
        bannerAdminService.editBanner(banner);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "删除Banner")
    @PostMapping("/remove")
    public ResponseDTO<String> removeBanner(@RequestBody Banner banner) {
        bannerAdminService.removeBanner(banner);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "分页Banner")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Banner>> pageBanner(Banner Banner, PageParam pageParam) {
        PageResult<Banner> page = bannerAdminService.pageBanner(Banner, pageParam);
        return ResponseDTO.ok(page);
    }

}
