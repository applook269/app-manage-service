package com.hooke.zdl.admin.module.support.captcha;


import com.hooke.zdl.admin.common.controller.SupportBaseController;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.constant.SwaggerTagConst;
import com.hooke.zdl.admin.module.support.captcha.domain.CaptchaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图形验证码业务
 */
@Tag(name = SwaggerTagConst.Support.CAPTCHA)
@RestController
public class CaptchaController extends SupportBaseController {

    @Resource
    private CaptchaService captchaService;

    @Operation(summary = "获取图形验证码")
    @GetMapping("/captcha")
    public ResponseDTO<CaptchaVO> generateCaptcha() {
        return ResponseDTO.ok(captchaService.generateCaptcha());
    }

}
