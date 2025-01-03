package com.hooke.zdl.admin.module.system.login.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // 生成一个新的密钥
    public String generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    // 验证用户输入的 TOTP 代码
    public boolean verifyTotp(String secretKey, int totpCode) {
        return gAuth.authorize(secretKey, totpCode);
    }

    // 生成二维码链接
    public String getQRBarcodeUrl(String appName, String user, String secretKey) {
        // 构建 URI
        String label = appName + ":" + user;
        String encodedLabel = java.net.URLEncoder.encode(label, java.nio.charset.StandardCharsets.UTF_8);
        return "otpauth://totp/" + encodedLabel + "?secret=" + secretKey + "&issuer=" + appName;
    }
}