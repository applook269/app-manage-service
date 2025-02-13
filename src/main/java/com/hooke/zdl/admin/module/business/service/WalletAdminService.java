package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.EvaluationMapper;
import com.hooke.zdl.admin.module.business.dao.RuleVipMapper;
import com.hooke.zdl.admin.module.business.dao.UserMapper;
import com.hooke.zdl.admin.module.business.dao.WalletMapper;
import com.hooke.zdl.admin.module.business.dao.WalletTransDtlMapper;
import com.hooke.zdl.admin.module.business.entity.Evaluation;
import com.hooke.zdl.admin.module.business.entity.RuleVip;
import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.entity.Wallet;
import com.hooke.zdl.admin.module.business.entity.WalletTransDtl;
import com.hooke.zdl.admin.module.business.model.RechargeResultRequest;
import com.hooke.zdl.admin.module.business.model.WalletTransDtlModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;
import static com.hooke.zdl.admin.module.business.entity.table.WalletTransDtlTableDef.WALLET_TRANS_DTL;
import static com.mybatisflex.core.query.QueryMethods.sum;


@Service
public class WalletAdminService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RuleVipMapper ruleVipMapper;
    @Autowired
    private EvaluationMapper evaluationMapper;
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private WalletTransDtlMapper walletTransDtlMapper;

    public PageResult<Wallet> pageWallet(Wallet wallet, PageParam pageParam) {
        Page<Wallet> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Wallet> walletPage = walletMapper.paginate(page, QueryWrapper.create(wallet));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), Wallet.class);
    }

    public PageResult<WalletTransDtlModel> pageWalletTrans(WalletTransDtlModel walletTransDtl, PageParam pageParam) {
        Page<WalletTransDtlModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        QueryWrapper queryWrapper = new QueryWrapper()
                .select(WALLET_TRANS_DTL.DEFAULT_COLUMNS)
                .select(USER.NAME.as("userName"), USER.PHONE_NO.as("phoneNo"), USER.TYPE.as("userType"), USER.IS_WOOL.as("isWool"))
                .from(WALLET_TRANS_DTL)
                .leftJoin(USER).on(WALLET_TRANS_DTL.USER_ID.eq(USER.ID))
                .where(WALLET_TRANS_DTL.STATUS.eq(walletTransDtl.getStatus()))
                .and(WALLET_TRANS_DTL.TYPE.eq(walletTransDtl.getType()))
                .orderBy(WALLET_TRANS_DTL.ID, false);
        Page<WalletTransDtlModel> walletPage = walletTransDtlMapper.paginateAs(page, queryWrapper, WalletTransDtlModel.class);
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords());
    }

    public void save(WalletTransDtl model) {
        WalletTransDtl detail = QueryChain.of(walletTransDtlMapper).eq(WalletTransDtl::getId, model.getId()).one();
        detail.setProof(model.getProof());
        walletTransDtlMapper.update(detail);
    }

    @Transactional
    public void rechargeConfirm(WalletTransDtl model) {
        WalletTransDtl detail = QueryChain.of(walletTransDtlMapper).eq(WalletTransDtl::getId, model.getId()).one();
        Wallet wallet = QueryChain.of(walletMapper).eq(Wallet::getUserId, detail.getUserId()).one();
        detail.setStatus(model.getStatus());
        detail.setProof(model.getProof());
        detail.setCompleteTime(LocalDateTime.now());
        if ("SUCCESS".equals(model.getStatus())) {
            // 交易余额：充值时余额+交易金额
            detail.setBalance(detail.getBalance().add(detail.getAmount()));
            // 钱包余额：当前余额+交易金额
            wallet.setBalance(wallet.getBalance().add(detail.getAmount()));
            walletMapper.update(wallet);
        }
        walletTransDtlMapper.update(detail);
        // 升级VIP
        vipLevelUp(detail.getUserId());
    }

    @Transactional
    public void otherRechargeConfirm(RechargeResultRequest rechargeResultRequest) {
        WalletTransDtl detail = QueryChain.of(walletTransDtlMapper).eq(WalletTransDtl::getTransactionNo, rechargeResultRequest.getMchOrderNo()).one();
        int status = rechargeResultRequest.getStatus();
        if (status == 2 || status == 3) {
            // 支付成功
            Wallet wallet = QueryChain.of(walletMapper).eq(Wallet::getUserId, detail.getUserId()).one();
            detail.setStatus("SUCCESS");
            detail.setCompleteTime(LocalDateTime.now());
            // 交易余额：充值时余额+交易金额
            detail.setBalance(detail.getBalance().add(detail.getAmount()));
            // 钱包余额：当前余额+交易金额
            wallet.setBalance(wallet.getBalance().add(detail.getAmount()));
            walletMapper.update(wallet);
            walletTransDtlMapper.update(detail);
            // 升级VIP
            vipLevelUp(detail.getUserId());
        } else if (status == -2) {
            // 支付失败
            detail.setStatus("FAILURE");
            detail.setCompleteTime(LocalDateTime.now());
            walletTransDtlMapper.update(detail);
        }
        // 其他情况不处理
    }

    @Transactional
    public void withdrawalConfirm(WalletTransDtl model) {
        WalletTransDtl detail = QueryChain.of(walletTransDtlMapper).eq(WalletTransDtl::getId, model.getId()).one();
        Wallet wallet = QueryChain.of(walletMapper).eq(Wallet::getUserId, detail.getUserId()).one();

        detail.setStatus(model.getStatus());
        detail.setCompleteTime(LocalDateTime.now());
        if ("FAILURE".equals(model.getStatus())) {
            // 提现失败，将提现金额返还到钱包余额
            wallet.setBalance(wallet.getBalance().add(detail.getAmount()));
            walletMapper.update(wallet);
            // 交易余额：提现时余额+交易金额
            detail.setBalance(detail.getBalance().add(detail.getAmount()));
        }
        walletTransDtlMapper.update(detail);
    }

    /**
     * VIP等级升级方法
     * 检查用户累计充值金额是否满足更高级别的VIP条件，如果满足，则升级用户VIP等级
     */
    public void vipLevelUp(Integer userId) {
        User user = userMapper.selectOneById(userId);
        Integer vipLevel = user.getVipLevel();
        // 判断充值金额总数是否达到 VIP升级要求
        List<RuleVip> rules = ruleVipMapper.selectAll();
        WalletTransDtl dtl = QueryChain.of(walletTransDtlMapper)
                .select(sum(WalletTransDtl::getAmount).as("AMOUNT"))
                .eq(WalletTransDtl::getUserId, userId)
                .eq(WalletTransDtl::getType, "RECHARGE")
                .eq(WalletTransDtl::getStatus, "SUCCESS")
                .one();
        BigDecimal amount = dtl == null ? BigDecimal.ZERO : dtl.getAmount();
        // 计算可升级的最大VIP等级
        Integer newVipLevel = rules.stream()
                .filter(e -> amount.compareTo(e.getAssetsAmount()) >= 0)
                .map(RuleVip::getVipLevel)
                .max(Integer::compareTo).orElse(0);
        // 保存用户信息
        if (newVipLevel > vipLevel) {
            user.setVipLevel(newVipLevel);
            userMapper.update(user);
            if (user.getReferenceUserId() != null) {
                // 如果推荐人不为空 每升一级生成一条评价数据，状态为待邀请
                while (vipLevel < newVipLevel) {
                    Evaluation evaluation = new Evaluation();
                    evaluation.setLeaderId(user.getReferenceUserId());
                    evaluation.setEvaluatorId(user.getId());
                    evaluation.setEvaluatorVipLevel(++vipLevel);
                    evaluation.setStatus("PENDING_INVITE");
                    evaluation.setInviteTime(LocalDateTime.now());
                    evaluation.setInviteNum(0);
                    evaluation.setPassFlag(false);
                    evaluationMapper.insert(evaluation);
                }
            }
        }
    }
}
