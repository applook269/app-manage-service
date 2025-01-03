package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.PositionHouse;
import com.hooke.zdl.admin.module.business.service.PositionHouseAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/position-house")
@Tag(name = "房产持仓信息", description = "房产持仓信息")
public class PositionHouseAdminController {

    @Autowired
    private PositionHouseAdminService positionHouseAdminService;

    @Operation(summary = "房产持仓")
    @GetMapping("/page")
    public ResponseDTO<PageResult<PositionHouse>> pagePositionHouse(PositionHouse positionHouse, PageParam pageParam) {
        PageResult<PositionHouse> model = positionHouseAdminService.pagePositionHouse(positionHouse, pageParam);
        return ResponseDTO.ok(model);
    }

}
