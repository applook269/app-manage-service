package com.hooke.zdl.admin.module.system.department.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.constant.AdminSwaggerTagConst;
import com.hooke.zdl.admin.module.system.department.domain.form.DepartmentAddForm;
import com.hooke.zdl.admin.module.system.department.domain.form.DepartmentUpdateForm;
import com.hooke.zdl.admin.module.system.department.domain.vo.DepartmentTreeVO;
import com.hooke.zdl.admin.module.system.department.domain.vo.DepartmentVO;
import com.hooke.zdl.admin.module.system.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_DEPARTMENT)
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @Operation(summary = "查询部门树形列表")
    @GetMapping("/department/treeList")
    public ResponseDTO<List<DepartmentTreeVO>> departmentTree() {
        return departmentService.departmentTree();
    }

    @Operation(summary = "添加部门")
    @PostMapping("/department/add")
    @SaCheckPermission("system:department:add")
    public ResponseDTO<String> addDepartment(@Valid @RequestBody DepartmentAddForm createDTO) {
        return departmentService.addDepartment(createDTO);
    }

    @Operation(summary = "更新部门")
    @PostMapping("/department/update")
    @SaCheckPermission("system:department:update")
    public ResponseDTO<String> updateDepartment(@Valid @RequestBody DepartmentUpdateForm updateDTO) {
        return departmentService.updateDepartment(updateDTO);
    }

    @Operation(summary = "删除部门")
    @GetMapping("/department/delete/{departmentId}")
    @SaCheckPermission("system:department:delete")
    public ResponseDTO<String> deleteDepartment(@PathVariable Long departmentId) {
        return departmentService.deleteDepartment(departmentId);
    }

    @Operation(summary = "查询部门列表")
    @GetMapping("/department/listAll")
    public ResponseDTO<List<DepartmentVO>> listAll() {
        return ResponseDTO.ok(departmentService.listAll());
    }

}
