package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.annoation.NoNeedLogin;
import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Wallet;
import com.hooke.zdl.admin.module.business.entity.WalletTransDtl;
import com.hooke.zdl.admin.module.business.model.RechargeResultRequest;
import com.hooke.zdl.admin.module.business.service.WalletAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/wallet")
@Tag(name = "钱包", description = "钱包")
public class WalletAdminController {
    @Autowired
    private WalletAdminService walletAdminService;

    @Operation(summary = "钱包分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Wallet>> pageWallet(Wallet wallet, PageParam pageParam) {
        PageResult<Wallet> walletPage = walletAdminService.pageWallet(wallet, pageParam);
        return ResponseDTO.ok(walletPage);
    }

    @Operation(summary = "交易明细分页")
    @GetMapping("/page-trans")
    public ResponseDTO<PageResult<WalletTransDtl>> pageWalletTrans(WalletTransDtl walletTransDtl, PageParam pageParam) {
        PageResult<WalletTransDtl> walletTransDtlPage = walletAdminService.pageWalletTrans(walletTransDtl, pageParam);
        return ResponseDTO.ok(walletTransDtlPage);
    }

    @Operation(summary = "充值确认")
    @PostMapping("/recharge-confirm")
    public ResponseDTO<String> rechargeConfirm(@RequestBody WalletTransDtl walletTransDtl) {
        walletAdminService.rechargeConfirm(walletTransDtl);
        return ResponseDTO.ok("充值确认成功");
    }

    @NoNeedLogin
    @Operation(summary = "充值确认（第三方回调）")
    @PostMapping("/other-recharge-confirm")
    public String otherRechargeConfirm(@ModelAttribute RechargeResultRequest rechargeResultRequest) {
        // 这里使用form表单的形式接收参数
        walletAdminService.otherRechargeConfirm(rechargeResultRequest);
        return "success";
    }

    @Operation(summary = "提现确认")
    @PostMapping("/withdrawal-confirm")
    public ResponseDTO<String> withdrawalConfirm(@RequestBody WalletTransDtl walletTransDtl) {
        walletAdminService.withdrawalConfirm(walletTransDtl);
        return ResponseDTO.ok("提现确认成功");
    }
}
