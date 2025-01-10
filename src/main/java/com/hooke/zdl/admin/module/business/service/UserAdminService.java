package com.hooke.zdl.admin.module.business.service;



import com.alibaba.fastjson2.JSONObject;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.*;
import com.hooke.zdl.admin.module.business.entity.*;
import com.hooke.zdl.admin.module.business.entity.table.UserTableDef;
import com.hooke.zdl.admin.module.business.model.CustomerUserModel;
import com.mybatisflex.core.activerecord.query.WhereBuilder;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.SqlOperators;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hooke.zdl.admin.module.business.entity.table.RealNameValidateTableDef.REAL_NAME_VALIDATE;
import static com.hooke.zdl.admin.module.business.entity.table.TeamMemberTableDef.TEAM_MEMBER;
import static com.hooke.zdl.admin.module.business.entity.table.TeamTableDef.TEAM;
import static com.hooke.zdl.admin.module.business.entity.table.UserTableDef.USER;
import static com.hooke.zdl.admin.module.business.entity.table.WalletTableDef.WALLET;
import static com.hooke.zdl.admin.module.business.entity.table.WalletTransDtlTableDef.WALLET_TRANS_DTL;
import static com.mybatisflex.core.query.QueryMethods.case_;
import static com.mybatisflex.core.query.QueryMethods.sum;


@Service
public class UserAdminService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private WalletTransDtlMapper walletTransDtlMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private RealNameValidateMapper realNameValidateMapper;
    @Autowired
    private TeamMemberMapper teamMemberMapper;

    public PageResult<CustomerUserModel> pageUser(CustomerUserModel user, PageParam pageParam) {
        Page<CustomerUserModel> page = SmartPageUtil.convert2PageQuery(pageParam);
        UserTableDef refUser = USER.as("ref_user");
        QueryWrapper queryWrapper = new QueryWrapper()
                .select(USER.ALL_COLUMNS,
                        WALLET.BALANCE.as("balance"),
                        TEAM.LEVEL.as("teamLevel"),
                        case_(REAL_NAME_VALIDATE.STATUS).when("SUCCESS").then(1).else_(0).end().as("isRealName"),
                        refUser.INVITATION_CODE.as("referenceUserInviteCode"),
                        refUser.PHONE_NO.as("referencePhoneNo"))
                .from(USER)
                .leftJoin(WALLET).on(WALLET.USER_ID.eq(USER.ID))
                .leftJoin(TEAM).on(TEAM.LEADER_ID.eq(USER.ID))
                .leftJoin(REAL_NAME_VALIDATE).on(REAL_NAME_VALIDATE.USER_ID.eq(USER.ID))
                .leftJoin(refUser).on(refUser.ID.eq(USER.REFERENCE_USER_ID));

        Page<CustomerUserModel> userPage = pageAs(page, queryWrapper, CustomerUserModel.class);
        PageResult<CustomerUserModel> result = SmartPageUtil.convert2PageResult(page, userPage.getRecords());
        List<CustomerUserModel> list = result.getList();
        if (!list.isEmpty()) {
            List<Integer> userIds = list.stream().map(CustomerUserModel::getId).toList();
            Map<Integer, Map<String, BigDecimal>> amountTypeMap = QueryChain.of(walletTransDtlMapper)
                    .select(WALLET_TRANS_DTL.USER_ID, WALLET_TRANS_DTL.TYPE, sum(WALLET_TRANS_DTL.AMOUNT).as("AMOUNT"))
                    .where(WALLET_TRANS_DTL.USER_ID.in(userIds))
                    .and(WALLET_TRANS_DTL.TYPE.in("RECHARGE", "WITHDRAWAL", "PURCHASE_XYT", "PURCHASE_HOUSE"))
                    .groupBy(WALLET_TRANS_DTL.USER_ID, WALLET_TRANS_DTL.TYPE)
                    .list().stream().collect(Collectors.groupingBy(WalletTransDtl::getUserId, Collectors.toMap(WalletTransDtl::getType, WalletTransDtl::getAmount)));
            list.forEach(e -> {
                Map<String, BigDecimal> amountMap = amountTypeMap.getOrDefault(e.getId(), Collections.emptyMap());
                e.setTotalRecharge(amountMap.getOrDefault("RECHARGE", BigDecimal.ZERO));
                e.setTotalWithdrawal(amountMap.getOrDefault("WITHDRAWAL", BigDecimal.ZERO));
                e.setTotalPurchase(amountMap.getOrDefault("PURCHASE_XYT", BigDecimal.ZERO).add(amountMap.getOrDefault("PURCHASE_HOUSE", BigDecimal.ZERO)));
            });
        }
        return result;
    }

    public void editUser(User user) {
        updateById(user);
    }

    public void batchBan(User user) {
        batchUpdateEnable(user, false);
    }

    public void batchUnBan(User user) {
        batchUpdateEnable(user, true);
    }

    private void batchUpdateEnable(User user, boolean enable) {
        List<Integer> subUserIds = QueryChain.of(teamMemberMapper)
                .leftJoin(TEAM).on(TEAM.ID.eq(TEAM_MEMBER.TEAM_ID))
                .where(TEAM.LEADER_ID.eq(user.getId()))
                .list().stream().map(TeamMember::getMemberUserId).toList();

        List<Integer> userIds = new ArrayList<>(subUserIds);
        userIds.add(user.getId());

        UpdateChain.of(userMapper)
                .set(USER.ENABLE, enable)
                .where(USER.ID.in(userIds))
                .update();
    }


}
