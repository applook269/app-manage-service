package com.hooke.zdl.admin.module.support.config;

import com.hooke.zdl.admin.common.controller.SupportBaseController;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.constant.SwaggerTagConst;
import com.hooke.zdl.admin.module.support.config.domain.ConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置
 */
@Tag(name = SwaggerTagConst.Support.CONFIG)
@RestController
public class ConfigController extends SupportBaseController {

    @Resource
    private ConfigService configService;

    @Operation(summary = "查询配置详情")
    @GetMapping("/config/queryByKey")
    public ResponseDTO<ConfigVO> queryByKey(@RequestParam String configKey) {
        return ResponseDTO.ok(configService.getConfig(configKey));
    }

}
