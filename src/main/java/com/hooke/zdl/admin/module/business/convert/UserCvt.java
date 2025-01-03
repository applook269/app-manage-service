package com.hooke.zdl.admin.module.business.convert;


import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.model.CustomerUserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserCvt {
    UserCvt INSTANCE = Mappers.getMapper(UserCvt.class);

    CustomerUserModel toModel(User user);

    List<CustomerUserModel> toModel(List<User> user);

    User toEntity(CustomerUserModel userModel);

    List<User> toEntity(List<CustomerUserModel> userModel);
}
