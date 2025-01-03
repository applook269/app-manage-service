package com.hooke.zdl.admin.module.system.login.domain;

import com.hooke.zdl.admin.common.swagger.SchemaEnum;
import com.hooke.zdl.admin.common.validator.enumeration.CheckEnum;
import com.hooke.zdl.admin.constant.LoginDeviceEnum;
import com.hooke.zdl.admin.module.support.captcha.domain.CaptchaForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 员工登录
 */
@Data
public class LoginForm extends CaptchaForm {

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    @Length(max = 30, message = "登录账号最多30字符")
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @SchemaEnum(desc = "登录终端", value = LoginDeviceEnum.class)
    @CheckEnum(value = LoginDeviceEnum.class, required = true, message = "此终端不允许登录")
    private Integer loginDevice;

    @Schema(description = "谷歌验证码")
    private String totpCode;
}
