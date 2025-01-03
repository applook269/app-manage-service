package com.hooke.zdl.admin.module.business.service;



import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.UserMapper;
import com.hooke.zdl.admin.module.business.dao.WalletMapper;
import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.entity.Wallet;
import com.hooke.zdl.admin.module.business.model.CustomerUserModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserAdminService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private WalletMapper walletMapper;

    public PageResult<CustomerUserModel> pageUser(User user, PageParam pageParam) {
        Page<User> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<User> userPage = page(page, QueryWrapper.create(user));
        PageResult<CustomerUserModel> result = SmartPageUtil.convert2PageResult(page, userPage.getRecords(), CustomerUserModel.class);
        List<CustomerUserModel> list = result.getList();
        if (!list.isEmpty()) {
            List<Integer> userIds = list.stream().map(CustomerUserModel::getId).toList();
            Map<Integer, BigDecimal> walletMap = QueryChain.of(walletMapper).in(Wallet::getUserId, userIds).list().stream().collect(Collectors.toMap(Wallet::getUserId, Wallet::getBalance));
            list.forEach(e -> e.setBalance(walletMap.get(e.getId())));
        }
        return result;
    }

    public void editUser(User user) {
        updateById(user);
    }

}
