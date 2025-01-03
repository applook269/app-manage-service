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
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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

    public PageResult<WalletTransDtl> pageWalletTrans(WalletTransDtl walletTransDtl, PageParam pageParam) {
        Page<WalletTransDtl> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<WalletTransDtl> walletPage = walletTransDtlMapper.paginate(page, QueryWrapper.create(walletTransDtl));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), WalletTransDtl.class);
    }

    @Transactional
    public void rechargeConfirm(WalletTransDtl model) {
        WalletTransDtl detail = QueryChain.of(walletTransDtlMapper).eq(WalletTransDtl::getId, model.getId()).one();
        Wallet wallet = QueryChain.of(walletMapper).eq(Wallet::getUserId, detail.getUserId()).one();
        detail.setStatus(model.getStatus());
        detail.setCompleteTime(LocalDateTime.now());
        if ("SUCCESS".equals(model.getStatus())) {
            // 交易余额：充值时余额+交易金额
            detail.setBalance(detail.getBalance().add(detail.getAmount()));
            // 钱包余额：当前余额+交易金额
            wallet.setBalance(wallet.getBalance().add(detail.getAmount()));
            walletMapper.update(wallet);
            // 升级VIP
            vipLevelUp(detail.getUserId());
        }
        walletTransDtlMapper.update(detail);
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
        WalletTransDtl dtl = walletTransDtlMapper.getTotalRecharge(user.getId());
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
