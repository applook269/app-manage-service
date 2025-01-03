package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class RegisterModel {
    private String username;
    private String password;
    private String confirmPassword;
}
