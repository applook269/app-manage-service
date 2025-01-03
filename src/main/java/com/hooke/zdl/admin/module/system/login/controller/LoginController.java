package com.hooke.zdl.admin.module.system.login.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.hooke.zdl.admin.common.annoation.NoNeedLogin;
import com.hooke.zdl.admin.common.constant.RequestHeaderConst;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.common.util.SmartRequestUtil;
import com.hooke.zdl.admin.constant.AdminSwaggerTagConst;
import com.hooke.zdl.admin.module.support.captcha.domain.CaptchaVO;
import com.hooke.zdl.admin.module.support.securityprotect.service.Level3ProtectConfigService;
import com.hooke.zdl.admin.module.system.login.domain.GoogleAuthVO;
import com.hooke.zdl.admin.module.system.login.domain.LoginForm;
import com.hooke.zdl.admin.module.system.login.domain.LoginResultVO;
import com.hooke.zdl.admin.module.system.login.domain.RequestEmployee;
import com.hooke.zdl.admin.module.system.login.service.LoginService;
import com.hooke.zdl.admin.module.system.login.service.TotpService;
import com.hooke.zdl.admin.util.AdminRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工登录
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_LOGIN)
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private TotpService totpService;

    @Resource
    private Level3ProtectConfigService level3ProtectConfigService;

    @NoNeedLogin
    @PostMapping("/login")
    @Operation(summary = "登录")
    public ResponseDTO<LoginResultVO> login(@Valid @RequestBody LoginForm loginForm, HttpServletRequest request) {
        String ip = JakartaServletUtil.getClientIP(request);
        String userAgent = JakartaServletUtil.getHeaderIgnoreCase(request, RequestHeaderConst.USER_AGENT);
        return loginService.login(loginForm, ip, userAgent);
    }

    @GetMapping("/login/getLoginInfo")
    @Operation(summary = "获取登录结果信息 ")
    public ResponseDTO<LoginResultVO> getLoginInfo() {
        String tokenValue = StpUtil.getTokenValue();
        LoginResultVO loginResult = loginService.getLoginResult(AdminRequestUtil.getRequestUser(), tokenValue);
        loginResult.setToken(tokenValue);
        return ResponseDTO.ok(loginResult);
    }

    @Operation(summary = "退出登陆 ")
    @GetMapping("/login/logout")
    public ResponseDTO<String> logout(@RequestHeader(value = RequestHeaderConst.TOKEN, required = false) String token) {
        return loginService.logout(token, SmartRequestUtil.getRequestUser());
    }

    @Operation(summary = "获取验证码 ")
    @GetMapping("/login/getCaptcha")
    @NoNeedLogin
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return loginService.getCaptcha();
    }

    @Operation(summary = "获取谷歌验证")
    @GetMapping("/login/getGoogleAuth")
    public ResponseDTO<GoogleAuthVO> getGoogleAuth() {
        GoogleAuthVO googleAuthVO = loginService.getGoogleAuth();
        return ResponseDTO.ok(googleAuthVO);
    }

    @Operation(summary = "设置谷歌验证")
    @PostMapping("/login/setGoogleAuth")
    public ResponseDTO<?> setGoogleAuth(@RequestBody GoogleAuthVO googleAuthVO) {
        return loginService.setGoogleAuth(googleAuthVO);
    }

}
