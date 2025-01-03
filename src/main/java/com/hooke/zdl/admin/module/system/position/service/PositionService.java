package com.hooke.zdl.admin.module.system.position.service;

import com.mybatisflex.core.paginate.Page;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.common.util.SmartBeanUtil;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.system.position.dao.PositionDao;
import com.hooke.zdl.admin.module.system.position.domain.entity.PositionEntity;
import com.hooke.zdl.admin.module.system.position.domain.form.PositionAddForm;
import com.hooke.zdl.admin.module.system.position.domain.form.PositionQueryForm;
import com.hooke.zdl.admin.module.system.position.domain.form.PositionUpdateForm;
import com.hooke.zdl.admin.module.system.position.domain.vo.PositionVO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职务表 Service
 */

@Service
public class PositionService {

    @Resource
    private PositionDao positionDao;

    /**
     * 分页查询
     */
    public PageResult<PositionVO> queryPage(PositionQueryForm queryForm) {
        queryForm.setDeletedFlag(Boolean.FALSE);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PositionVO> list = positionDao.queryPage(page, queryForm);
        PageResult<PositionVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PositionAddForm addForm) {
        PositionEntity positionEntity = SmartBeanUtil.copy(addForm, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    public ResponseDTO<String> update(PositionUpdateForm updateForm) {
        PositionEntity positionEntity = SmartBeanUtil.copy(updateForm, PositionEntity.class);
        positionDao.update(positionEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }

        positionDao.deleteBatchByIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long positionId) {
        if (null == positionId) {
            return ResponseDTO.ok();
        }

        positionDao.deleteById(positionId);
        return ResponseDTO.ok();
    }

    /**
     * 分页查询
     */
    public List<PositionVO> queryList() {
        List<PositionVO> list = positionDao.queryList(Boolean.FALSE);
        return list;
    }
}
