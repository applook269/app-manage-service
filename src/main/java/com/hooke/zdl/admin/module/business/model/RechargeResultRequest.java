package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class RechargeResultRequest {
    /**
     * 支付中心生成的订单号
     */
    private String payOrderId;

    /**
     * 支付中心分配的商户号
     */
    private String mchId;

    /**
     * 商户应用ID
     */
    private String appId;

    /**
     * 支付产品ID
     */
    private String productId;

    /**
     * 商户生成的订单号
     */
    private String mchOrderNo;

    /**
     * 请求支付下单时金额,单位分
     */
    private int amount;

    /**
     * 用户实际付款的金额,单位分
     */
    private int income;

    /**
     * 支付状态,-2:订单已关闭,0-订单生成,1-支付中,2-支付成功,3-业务处理完成,4-已退款（2和3都表示支付成功,3表示支付平台回调商户且返回成功后的状态）
     */
    private int status;

    /**
     * 三方支付渠道订单号
     */
    private String channelOrderNo;

    /**
     * 支付中心回调时会原样返回
     */
    private String param2;

    /**
     * 精确到毫秒
     */
    private long paySuccTime;

    /**
     * 通知类型，1-前台通知，2-后台通知
     */
    private int backType;

    /**
     * 通知请求时间，yyyyMMddHHmmss格式
     */
    private String reqTime;

    /**
     * 签名值，详见签名算法
     */
    private String sign;
}
