package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.ProductHouse;
import com.hooke.zdl.admin.module.business.service.HouseAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/house")
@Tag(name = "房产", description = "房产")
public class HouseAdminController {
    @Autowired
    private HouseAdminService houseAdminService;

    @Operation(summary = "房产信息分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<ProductHouse>> pageHouse(ProductHouse model, PageParam pageParam) {
        PageResult<ProductHouse> ProductHousePage = houseAdminService.pageHouse(model, pageParam);
        return ResponseDTO.ok(ProductHousePage);
    }

    @Operation(summary = "新增小盈通")
    @PostMapping("/add")
    public ResponseDTO<Void> addHouse(@RequestBody ProductHouse model) {
        houseAdminService.addHouse(model);
        return ResponseDTO.ok();
    }

    @Operation(summary = "编辑小盈通")
    @PostMapping("/edit")
    public ResponseDTO<Void> editHouse(@RequestBody ProductHouse model) {
        houseAdminService.editHouse(model);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除小盈通")
    @PostMapping("/remove")
    public ResponseDTO<Void> removeHouse(@RequestBody ProductHouse model) {
        houseAdminService.removeHouse(model);
        return ResponseDTO.ok();
    }
}
