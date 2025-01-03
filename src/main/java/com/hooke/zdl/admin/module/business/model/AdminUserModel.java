package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.util.List;

@Data
public class AdminUserModel {
    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像ID
     */
    private String photo;

    private List<String> roleCodes;
    private List<String> authCodes;
}
