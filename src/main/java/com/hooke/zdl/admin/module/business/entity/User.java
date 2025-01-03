package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    private String name;

    /**
     * 启用状态
     */
    private Boolean enable;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 出生日期
     */
    private String brithDate;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 登录密码
     */
    private String password;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 推荐人 ID
     */
    private Integer referenceUserId;

    /**
     * 头像ID
     */
    private String photo;

}
