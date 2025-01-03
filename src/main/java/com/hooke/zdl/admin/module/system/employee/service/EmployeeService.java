package com.hooke.zdl.admin.module.system.employee.service;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.google.common.collect.Lists;
import com.hooke.zdl.admin.common.code.UserErrorCode;
import com.hooke.zdl.admin.common.constant.StringConst;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.RequestUser;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.common.enumeration.UserTypeEnum;
import com.hooke.zdl.admin.common.util.SmartBeanUtil;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.support.securityprotect.service.SecurityPasswordService;
import com.hooke.zdl.admin.module.system.department.dao.DepartmentDao;
import com.hooke.zdl.admin.module.system.department.domain.entity.DepartmentEntity;
import com.hooke.zdl.admin.module.system.department.domain.vo.DepartmentVO;
import com.hooke.zdl.admin.module.system.department.service.DepartmentService;
import com.hooke.zdl.admin.module.system.employee.dao.EmployeeDao;
import com.hooke.zdl.admin.module.system.employee.domain.entity.EmployeeEntity;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeAddForm;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeBatchUpdateDepartmentForm;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeQueryForm;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeUpdateAvatarForm;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeUpdateForm;
import com.hooke.zdl.admin.module.system.employee.domain.form.EmployeeUpdatePasswordForm;
import com.hooke.zdl.admin.module.system.employee.domain.vo.EmployeeVO;
import com.hooke.zdl.admin.module.system.employee.manager.EmployeeManager;
import com.hooke.zdl.admin.module.system.login.service.LoginService;
import com.hooke.zdl.admin.module.system.position.dao.PositionDao;
import com.hooke.zdl.admin.module.system.position.domain.entity.PositionEntity;
import com.hooke.zdl.admin.module.system.role.dao.RoleEmployeeDao;
import com.hooke.zdl.admin.module.system.role.domain.vo.RoleEmployeeVO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 员工 service
 */
@Service
public class EmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private EmployeeManager employeeManager;

    @Resource
    private RoleEmployeeDao roleEmployeeDao;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private SecurityPasswordService securityPasswordService;

    @Resource
    @Lazy
    private LoginService loginService;

    @Resource
    private PositionDao positionDao;

    public EmployeeEntity getById(Long employeeId) {
        return employeeDao.selectOneById(employeeId);
    }


    /**
     * 查询员工列表
     */
    public ResponseDTO<PageResult<EmployeeVO>> queryEmployee(EmployeeQueryForm employeeQueryForm) {
        employeeQueryForm.setDeletedFlag(false);
        Page pageParam = SmartPageUtil.convert2PageQuery(employeeQueryForm);

        List<Long> departmentIdList = new ArrayList<>();
        if (employeeQueryForm.getDepartmentId() != null) {
            departmentIdList.addAll(departmentService.selfAndChildrenIdList(employeeQueryForm.getDepartmentId()));
        }

        List<EmployeeVO> employeeList = employeeDao.queryEmployee(pageParam, employeeQueryForm, departmentIdList);
        if (CollectionUtils.isEmpty(employeeList)) {
            PageResult<EmployeeVO> pageResult = SmartPageUtil.convert2PageResult(pageParam, employeeList);
            return ResponseDTO.ok(pageResult);
        }

        // 查询员工角色
        List<Long> employeeIdList = employeeList.stream().map(EmployeeVO::getEmployeeId).collect(Collectors.toList());
        List<RoleEmployeeVO> roleEmployeeEntityList = employeeIdList.isEmpty() ? Collections.emptyList() : roleEmployeeDao.selectRoleByEmployeeIdList(employeeIdList);
        Map<Long, List<Long>> employeeRoleIdListMap = roleEmployeeEntityList.stream().collect(Collectors.groupingBy(RoleEmployeeVO::getEmployeeId, Collectors.mapping(RoleEmployeeVO::getRoleId, Collectors.toList())));
        Map<Long, List<String>> employeeRoleNameListMap = roleEmployeeEntityList.stream().collect(Collectors.groupingBy(RoleEmployeeVO::getEmployeeId, Collectors.mapping(RoleEmployeeVO::getRoleName, Collectors.toList())));

        // 查询员工职位
        List<Long> positionIdList = employeeList.stream().map(EmployeeVO::getPositionId).filter(Objects::nonNull).collect(Collectors.toList());
        List<PositionEntity> positionEntityList = positionIdList.isEmpty() ? Collections.emptyList() : positionDao.selectListByIds(positionIdList);
        Map<Long, String> positionNameMap = positionEntityList.stream().collect(Collectors.toMap(PositionEntity::getPositionId, PositionEntity::getPositionName));

        employeeList.forEach(e -> {
            e.setRoleIdList(employeeRoleIdListMap.getOrDefault(e.getEmployeeId(), Lists.newArrayList()));
            e.setRoleNameList(employeeRoleNameListMap.getOrDefault(e.getEmployeeId(), Lists.newArrayList()));
            e.setDepartmentName(departmentService.getDepartmentPath(e.getDepartmentId()));
            e.setPositionName(positionNameMap.get(e.getPositionId()));
        });
        PageResult<EmployeeVO> pageResult = SmartPageUtil.convert2PageResult(pageParam, employeeList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 新增员工
     */
    public synchronized ResponseDTO<String> addEmployee(EmployeeAddForm employeeAddForm) {
        // 校验登录名是否重复
        EmployeeEntity employeeEntity = employeeDao.getByLoginName(employeeAddForm.getLoginName(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("登录名重复");
        }
        // 校验电话是否存在
        employeeEntity = employeeDao.getByPhone(employeeAddForm.getPhone(), null);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("手机号已存在");
        }
        // 部门是否存在
        Long departmentId = employeeAddForm.getDepartmentId();
        DepartmentEntity department = departmentDao.selectOneById(departmentId);
        if (department == null) {
            return ResponseDTO.userErrorParam("部门不存在");
        }

        EmployeeEntity entity = SmartBeanUtil.copy(employeeAddForm, EmployeeEntity.class);

        // 设置密码 默认密码
        String password = securityPasswordService.randomPassword();
        entity.setLoginPwd(SecurityPasswordService.getEncryptPwd(password));

        // 保存数据
        entity.setDeletedFlag(Boolean.FALSE);
        employeeManager.saveEmployee(entity, employeeAddForm.getRoleIdList());

        return ResponseDTO.ok(password);
    }

    /**
     * 更新员工
     */
    public synchronized ResponseDTO<String> updateEmployee(EmployeeUpdateForm employeeUpdateForm) {

        Long employeeId = employeeUpdateForm.getEmployeeId();
        EmployeeEntity employeeEntity = employeeDao.selectOneById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 部门是否存在
        Long departmentId = employeeUpdateForm.getDepartmentId();
        DepartmentEntity departmentEntity = departmentDao.selectOneById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.userErrorParam("部门不存在");
        }


        EmployeeEntity existEntity = employeeDao.getByLoginName(employeeUpdateForm.getLoginName(), null);
        if (null != existEntity && !Objects.equals(existEntity.getEmployeeId(), employeeId)) {
            return ResponseDTO.userErrorParam("登录名重复");
        }

        existEntity = employeeDao.getByPhone(employeeUpdateForm.getPhone(), null);
        if (null != existEntity && !Objects.equals(existEntity.getEmployeeId(), employeeId)) {
            return ResponseDTO.userErrorParam("手机号已存在");
        }

        // 不更新密码
        EmployeeEntity entity = SmartBeanUtil.copy(employeeUpdateForm, EmployeeEntity.class);
        entity.setLoginPwd(null);

        // 更新数据
        employeeManager.updateEmployee(entity, employeeUpdateForm.getRoleIdList());

        // 清除员工缓存
        loginService.clearLoginEmployeeCache(employeeId);

        return ResponseDTO.ok();
    }


    /**
     * 更新登录人头像
     */
    public ResponseDTO<String> updateAvatar(EmployeeUpdateAvatarForm employeeUpdateAvatarForm) {
        Long employeeId = employeeUpdateAvatarForm.getEmployeeId();
        EmployeeEntity employeeEntity = employeeDao.selectOneById(employeeId);
        if (employeeEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        // 更新头像
        EmployeeEntity updateEntity = new EmployeeEntity();
        updateEntity.setEmployeeId(employeeId);
        updateEntity.setAvatar(employeeUpdateAvatarForm.getAvatar());
        employeeDao.update(updateEntity);

        // 清除员工缓存
        loginService.clearLoginEmployeeCache(employeeId);
        return ResponseDTO.ok();
    }

    /**
     * 更新禁用/启用状态
     */
    public ResponseDTO<String> updateDisableFlag(Long employeeId) {
        if (null == employeeId) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        EmployeeEntity employeeEntity = employeeDao.selectOneById(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        employeeDao.updateDisableFlag(employeeId, !employeeEntity.getDisabledFlag());

        if (employeeEntity.getDisabledFlag()) {
            // 强制退出登录
            StpUtil.logout(UserTypeEnum.ADMIN_EMPLOYEE.getValue() + StringConst.COLON + employeeId);
        }

        return ResponseDTO.ok();
    }

    /**
     * 批量删除员工
     */
    public ResponseDTO<String> batchUpdateDeleteFlag(List<Long> employeeIdList) {
        if (CollectionUtils.isEmpty(employeeIdList)) {
            return ResponseDTO.ok();
        }
        List<EmployeeEntity> employeeEntityList = employeeManager.listByIds(employeeIdList);
        if (CollectionUtils.isEmpty(employeeEntityList)) {
            return ResponseDTO.ok();
        }
        // 更新删除
        List<EmployeeEntity> deleteList = employeeIdList.stream().map(e -> {
            EmployeeEntity updateEmployee = new EmployeeEntity();
            updateEmployee.setEmployeeId(e);
            updateEmployee.setDeletedFlag(true);
            return updateEmployee;
        }).collect(Collectors.toList());
        employeeManager.updateBatch(deleteList);

        for (Long employeeId : employeeIdList) {
            // 强制退出登录
            StpUtil.logout(UserTypeEnum.ADMIN_EMPLOYEE.getValue() + StringConst.COLON + employeeId);
        }
        return ResponseDTO.ok();
    }


    /**
     * 批量更新部门
     */
    public ResponseDTO<String> batchUpdateDepartment(EmployeeBatchUpdateDepartmentForm batchUpdateDepartmentForm) {
        List<Long> employeeIdList = batchUpdateDepartmentForm.getEmployeeIdList();
        List<EmployeeEntity> employeeEntityList = employeeDao.selectListByIds(employeeIdList);
        if (employeeIdList.size() != employeeEntityList.size()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        // 更新
        List<EmployeeEntity> updateList = employeeIdList.stream().map(e -> {
            EmployeeEntity updateEmployee = new EmployeeEntity();
            updateEmployee.setEmployeeId(e);
            updateEmployee.setDepartmentId(batchUpdateDepartmentForm.getDepartmentId());
            return updateEmployee;
        }).collect(Collectors.toList());
        employeeManager.updateBatch(updateList);

        return ResponseDTO.ok();
    }


    /**
     * 更新密码
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> updatePassword(RequestUser requestUser, EmployeeUpdatePasswordForm updatePasswordForm) {
        Long employeeId = updatePasswordForm.getEmployeeId();
        EmployeeEntity employeeEntity = employeeDao.selectOneById(employeeId);
        if (employeeEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        // 校验原始密码
        String oldPassword = SecurityPasswordService.getEncryptPwd(updatePasswordForm.getOldPassword());
        if (!Objects.equals(oldPassword, employeeEntity.getLoginPwd())) {
            return ResponseDTO.userErrorParam("原密码有误，请重新输入");
        }

        // 校验密码复杂度
        ResponseDTO<String> validatePassComplexity = securityPasswordService.validatePasswordComplexity(updatePasswordForm.getNewPassword());
        if (!validatePassComplexity.getOk()) {
            return validatePassComplexity;
        }

        // 新旧密码相同
        String newPassword = SecurityPasswordService.getEncryptPwd(updatePasswordForm.getNewPassword());
        if (Objects.equals(oldPassword, newPassword)) {
            return ResponseDTO.userErrorParam("新密码与原始密码相同，请重新输入");
        }

        // 根据三级等保规则，校验密码是否重复
        ResponseDTO<String> passwordRepeatTimes = securityPasswordService.validatePasswordRepeatTimes(requestUser, updatePasswordForm.getNewPassword());
        if (!passwordRepeatTimes.getOk()) {
            return ResponseDTO.error(passwordRepeatTimes);
        }

        // 更新密码
        EmployeeEntity updateEntity = new EmployeeEntity();
        updateEntity.setEmployeeId(employeeId);
        updateEntity.setLoginPwd(newPassword);
        employeeDao.update(updateEntity);

        // 保存修改密码密码记录
        securityPasswordService.saveUserChangePasswordLog(requestUser, newPassword, oldPassword);

        return ResponseDTO.ok();
    }

    /**
     * 获取某个部门的员工信息
     */
    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByDepartmentId(Long departmentId, Boolean disabledFlag) {
        List<EmployeeEntity> employeeEntityList = employeeDao.selectByDepartmentId(departmentId, disabledFlag);
        if (disabledFlag != null) {
            employeeEntityList = employeeEntityList.stream().filter(e -> e.getDisabledFlag().equals(disabledFlag)).collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(employeeEntityList)) {
            return ResponseDTO.ok(Collections.emptyList());
        }

        DepartmentVO department = departmentService.getDepartmentById(departmentId);

        List<EmployeeVO> voList = employeeEntityList.stream().map(e -> {
            EmployeeVO employeeVO = SmartBeanUtil.copy(e, EmployeeVO.class);
            if (department != null) {
                employeeVO.setDepartmentName(department.getName());
            }
            return employeeVO;
        }).collect(Collectors.toList());
        return ResponseDTO.ok(voList);
    }


    /**
     * 重置密码
     */
    public ResponseDTO<String> resetPassword(Long employeeId) {
        String password = securityPasswordService.randomPassword();
        employeeDao.updatePassword(employeeId, SecurityPasswordService.getEncryptPwd(password));
        return ResponseDTO.ok(password);
    }


    /**
     * 查询全部员工
     */
    public ResponseDTO<List<EmployeeVO>> queryAllEmployee(Boolean disabledFlag) {
        List<EmployeeVO> employeeList = employeeDao.selectEmployeeByDisabledAndDeleted(disabledFlag, Boolean.FALSE);
        return ResponseDTO.ok(employeeList);
    }

    /**
     * 根据登录名获取员工
     */
    public EmployeeEntity getByLoginName(String loginName) {
        return employeeDao.getByLoginName(loginName, null);
    }

    public void update(EmployeeEntity employeeEntity) {
        employeeDao.update(employeeEntity);
    }

}
