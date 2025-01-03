package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.PositionXyt;
import com.hooke.zdl.admin.module.business.service.PositionXytAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/position-xyt")
@Tag(name = "小盈通持仓信息", description = "小盈通持仓信息")
public class PositionXytAdminController {
    @Autowired
    private PositionXytAdminService positionXytAdminService;

    @Operation(summary = "小盈通持仓")
    @GetMapping("/page")
    public ResponseDTO<PageResult<PositionXyt>> pagePositionXyt(PositionXyt positionXyt, PageParam pageParam) {
        PageResult<PositionXyt> model = positionXytAdminService.pagePositionXyt(positionXyt, pageParam);
        return ResponseDTO.ok(model);
    }

}
