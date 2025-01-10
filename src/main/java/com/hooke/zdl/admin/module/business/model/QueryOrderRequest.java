package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class QueryOrderRequest {
    // 支付中心分配的商户号
    private String mchId;
    // 支付中心生成的订单号，与mchOrderNo二者传一即可
    private String payOrderId;
    // 商户生成的订单号，与payOrderId二者传一即可
    private String mchOrderNo;
    // 是否执行回调，如果为true，则支付中心会再次向商户发起一次回调，如果为false则不发起
    private Boolean executeNotify;
    // 请求接口时间， yyyyMMddHHmmss格式
    private String reqTime;
    // 接口版本号，固定：1.0
    private String version;
    // 签名值，详见签名算法
    private String sign;
}
