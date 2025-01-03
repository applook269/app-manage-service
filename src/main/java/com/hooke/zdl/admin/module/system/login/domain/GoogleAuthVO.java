package com.hooke.zdl.admin.module.system.login.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthVO {
    private Boolean isOpen;
    private String userSecretKey;
    private String verifyCode;
    private String qrCodeUrl;
}
