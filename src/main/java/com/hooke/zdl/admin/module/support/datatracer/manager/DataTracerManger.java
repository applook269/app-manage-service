package com.hooke.zdl.admin.module.support.datatracer.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.hooke.zdl.admin.module.support.datatracer.dao.DataTracerDao;
import com.hooke.zdl.admin.module.support.datatracer.domain.entity.DataTracerEntity;
import org.springframework.stereotype.Service;

/**
 * manager层
 */
@Service
public class DataTracerManger extends ServiceImpl<DataTracerDao, DataTracerEntity> {
}
