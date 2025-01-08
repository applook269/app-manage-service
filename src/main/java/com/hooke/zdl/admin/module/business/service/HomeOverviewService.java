package com.hooke.zdl.admin.module.business.service;

import com.hooke.zdl.admin.module.business.dao.UserMapper;
import com.hooke.zdl.admin.module.business.dao.WalletTransDtlMapper;
import com.hooke.zdl.admin.module.business.entity.WalletTransDtl;
import com.hooke.zdl.admin.module.business.model.HomeOverviewModel;
import com.mybatisflex.core.query.QueryChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;
import static com.hooke.zdl.admin.module.business.entity.table.WalletTransDtlTableDef.WALLET_TRANS_DTL;
import static com.mybatisflex.core.query.QueryMethods.dateFormat;
import static com.mybatisflex.core.query.QueryMethods.sum;

@Service
public class HomeOverviewService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WalletTransDtlMapper walletTransDtlMapper;

    public static final String DB_DATE_FORMAT = "%Y-%m-%d";

    public HomeOverviewModel getHomeOverview() {
        HomeOverviewModel model = new HomeOverviewModel();
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    // 获取用户总数
                    long count = QueryChain.of(userMapper).count();
                    model.setTotalUser(new BigDecimal(count));
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取充值总额
                    WalletTransDtl recharge = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.eq("RECHARGE")).one();
                    model.setTotalRecharge(recharge == null ? BigDecimal.ZERO :recharge.getAmount());
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取提现总额
                    WalletTransDtl withdrawal = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.eq("WITHDRAWAL")).one();
                    model.setTotalWithdrawal(withdrawal == null ? BigDecimal.ZERO : withdrawal.getAmount());
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日充值总额
                    WalletTransDtl todayRecharge = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.eq("RECHARGE"))
                            .and(dateFormat(WALLET_TRANS_DTL.APPLY_TIME, DB_DATE_FORMAT).eq(LocalDate.now())).one();
                    model.setTodayRecharge(todayRecharge == null ? BigDecimal.ZERO : todayRecharge.getAmount());
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日提现总额
                    WalletTransDtl todayWithdraw = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.eq("WITHDRAWAL"))
                            .and(dateFormat(WALLET_TRANS_DTL.APPLY_TIME, DB_DATE_FORMAT).eq(LocalDate.now())).one();
                    model.setTodayWithdraw(todayWithdraw == null ? BigDecimal.ZERO : todayWithdraw.getAmount());
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日新增用户数
                    long todayNewUser = QueryChain.of(userMapper).where(dateFormat(USER.INSERT_TIME, DB_DATE_FORMAT).eq(LocalDate.now())).count();
                    model.setTodayNewUser(new BigDecimal(todayNewUser));
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日投资用户数
                    long count = QueryChain.of(walletTransDtlMapper).select(WALLET_TRANS_DTL.USER_ID)
                            .where(WALLET_TRANS_DTL.TYPE.in("PURCHASE_XYT", "PURCHASE_HOUSE"))
                            .and(dateFormat(WALLET_TRANS_DTL.APPLY_TIME, DB_DATE_FORMAT).eq(LocalDate.now()))
                            .groupBy(WALLET_TRANS_DTL.USER_ID).count();
                    model.setTodayInvestUser(new BigDecimal(count));
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日投资总额
                    WalletTransDtl todayInvestAmount = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.in("PURCHASE_XYT", "PURCHASE_HOUSE"))
                            .and(dateFormat(WALLET_TRANS_DTL.APPLY_TIME, DB_DATE_FORMAT).eq(LocalDate.now())).one();
                    model.setTodayInvestAmount(todayInvestAmount == null ? BigDecimal.ZERO : todayInvestAmount.getAmount());
                }),
                CompletableFuture.runAsync(() -> {
                    // 获取今日利息总额
                    WalletTransDtl todayInterest = QueryChain.of(walletTransDtlMapper).select(sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                            .where(WALLET_TRANS_DTL.TYPE.in("COUPON_YIELD", "YIELD_XYT", "YIELD_HOUSE"))
                            .and(dateFormat(WALLET_TRANS_DTL.APPLY_TIME, DB_DATE_FORMAT).eq(LocalDate.now())).one();
                    model.setTodayInterest(todayInterest == null ? BigDecimal.ZERO : todayInterest.getAmount());
                })
        ).join();
        return model;
    }

}
