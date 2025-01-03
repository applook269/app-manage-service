package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class LoginModel {
    private String username;
    private String password;
    private String verificationCode;
    private String loginType;
}
