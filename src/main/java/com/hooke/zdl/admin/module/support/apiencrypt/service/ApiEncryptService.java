package com.hooke.zdl.admin.module.support.apiencrypt.service;

/**
 * 接口加密、解密 Service
 */

public interface ApiEncryptService {

    /**
     * 解密
     */
    String decrypt(String data);

    /**
     * 加密
     */
    String encrypt(String data);

}
