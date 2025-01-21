package com.hooke.zdl.admin.module.business.service;



import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.RealNameValidateMapper;
import com.hooke.zdl.admin.module.business.dao.UserMapper;
import com.hooke.zdl.admin.module.business.entity.BaseEntity;
import com.hooke.zdl.admin.module.business.entity.RealNameValidate;
import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.model.RealNameModel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RealNameAdminService extends ServiceImpl<RealNameValidateMapper, RealNameValidate> {
    @Autowired
    private UserMapper userMapper;

    public PageResult<RealNameModel> pageRealName(RealNameValidate model, PageParam pageParam) {
        Page<RealNameValidate> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<RealNameValidate> walletPage = page(page, QueryWrapper.create(model).orderBy(RealNameValidate::getInsertTime).desc());
        PageResult<RealNameModel> result = SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), RealNameModel.class);
        List<RealNameModel> list = result.getList();
        if (!list.isEmpty()) {
            List<Integer> userIds = list.stream().map(RealNameModel::getUserId).toList();
            Map<Integer, User> userMap = userMapper.selectListByIds(userIds).stream().collect(Collectors.toMap(BaseEntity::getId, e -> e));
            list.forEach(e -> {
                User user = userMap.get(e.getUserId());
                e.setName(user.getName());
                e.setIdNo(user.getIdNo());
            });
        }
        return result;


    }

    public void reviewRealName(RealNameValidate model) {
        RealNameValidate realNameValidate = queryChain().eq(RealNameValidate::getId, model.getId()).one();
        realNameValidate.setStatus(model.getStatus());
        updateById(realNameValidate);
    }
}
