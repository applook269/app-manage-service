package com.hooke.zdl.admin.interceptor;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import com.hooke.zdl.admin.common.annoation.NoNeedLogin;
import com.hooke.zdl.admin.common.code.SystemErrorCode;
import com.hooke.zdl.admin.common.code.UserErrorCode;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.common.util.SmartRequestUtil;
import com.hooke.zdl.admin.common.util.SmartResponseUtil;
import com.hooke.zdl.admin.module.system.login.domain.RequestEmployee;
import com.hooke.zdl.admin.module.system.login.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * admin 拦截器
 */

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Resource
    private LoginService loginService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // OPTIONS请求直接return
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        boolean isHandler = handler instanceof HandlerMethod;
        if (!isHandler) {
            return true;
        }

        try {
            // --------------- 第一步： 根据token 获取用户 ---------------

            String tokenValue = StpUtil.getTokenValue();

            String loginId = (String) StpUtil.getLoginIdByToken(tokenValue);

            RequestEmployee requestEmployee = loginService.getLoginEmployee(loginId, request);

            // --------------- 第二步： 校验 登录 ---------------

            Method method = ((HandlerMethod) handler).getMethod();
            NoNeedLogin noNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoNeedLogin.class);
            if (noNeedLogin != null) {
                checkActiveTimeout(requestEmployee);
                return true;
            }

            if (requestEmployee == null) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_STATE_INVALID));
                return false;
            }

            // 检测token 活跃频率
            checkActiveTimeout(requestEmployee);


            // --------------- 第三步： 校验 权限 ---------------

            SmartRequestUtil.setRequestUser(requestEmployee);
            if (SaStrategy.instance.isAnnotationPresent.apply(method, SaIgnore.class)) {
                return true;
            }

            // 如果是超级管理员的话，不需要校验权限
            if (requestEmployee.getAdministratorFlag()) {
                return true;
            }

            SaStrategy.instance.checkMethodAnnotation.accept(method);

        } catch (SaTokenException e) {
            /*
             * sa-token 异常状态码
             * 具体请看： https://sa-token.cc/doc.html#/fun/exception-code
             */
            int code = e.getCode();
            if (code == 11041 || code == 11051) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.NO_PERMISSION));
            } else if (code == 11016) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_ACTIVE_TIMEOUT));
            } else if (code >= 11011 && code <= 11015) {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.LOGIN_STATE_INVALID));
            } else {
                SmartResponseUtil.write(response, ResponseDTO.error(UserErrorCode.PARAM_ERROR));
            }
            return false;
        } catch (Throwable e) {
            SmartResponseUtil.write(response, ResponseDTO.error(SystemErrorCode.SYSTEM_ERROR));
            log.error(e.getMessage(), e);
            return false;
        }

        // 通过验证
        return true;
    }


    /**
     * 检测：token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结
     */
    private void checkActiveTimeout(RequestEmployee requestEmployee) {

        // 用户不在线，也不用检测
        if (requestEmployee == null) {
            return;
        }

        StpUtil.checkActiveTimeout();
        StpUtil.updateLastActiveToNow();
    }


}