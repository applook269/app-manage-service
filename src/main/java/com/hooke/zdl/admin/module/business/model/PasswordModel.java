package com.hooke.zdl.admin.module.business.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordModel {
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    private String newPassword;
    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;
}
