package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HomeOverviewModel {
    private BigDecimal totalUser;
    private BigDecimal totalRecharge;
    private BigDecimal totalWithdrawal;
    private BigDecimal todayRecharge;
    private BigDecimal todayWithdraw;
    private BigDecimal todayNewUser;
    private BigDecimal todayInvestUser;
    private BigDecimal todayInvestAmount;
    private BigDecimal todayInterest;
}
