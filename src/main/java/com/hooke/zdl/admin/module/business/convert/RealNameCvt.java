package com.hooke.zdl.admin.module.business.convert;


import com.hooke.zdl.admin.module.business.entity.RealNameValidate;
import com.hooke.zdl.admin.module.business.model.RealNameModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RealNameCvt {
    RealNameCvt INSTANCE = Mappers.getMapper(RealNameCvt.class);

    RealNameModel toModel(RealNameValidate user);

    List<RealNameModel> toModel(List<RealNameValidate> user);

    RealNameValidate toEntity(RealNameModel userModel);

    List<RealNameValidate> toEntity(List<RealNameModel> userModel);
}
