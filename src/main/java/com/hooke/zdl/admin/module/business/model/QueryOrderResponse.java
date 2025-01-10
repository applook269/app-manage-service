package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class QueryOrderResponse {
    private String retCode;
    private String retMsg;

    // 支付中心分配的商户号
    private String mchId;
    // 商户应用ID
    private String appId;
    // 支付产品ID
    private String productId;
    // 返回支付系统订单号
    private String payOrderId;
    // 商户生成的订单号
    private String mchOrderNo;
    // 下单时传的支付金额,单位分
    private int amount;
    // 三位货币代码,人民币:cny
    private String currency;
    // 当前订单状态: -2:订单已关闭,0-订单生成,1-支付中,2-支付成功,3-业务处理完成,4-已退款（2和3都表示支付成功,3表示支付平台回调商户且返回成功后的状态）
    private String status;
    // 渠道用户
    private String channelUser;
    // 对应的第三方支付订单号
    private String channelOrderNo;
    // 渠道数据包
    private String channelAttach;
    // 支付成功时间，精确到毫秒
    private Long paySuccTime;
    // 签名值，详见签名算法
    private String sign;
}
