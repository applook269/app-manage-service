package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.User;
import com.hooke.zdl.admin.module.business.model.CustomerUserModel;
import com.hooke.zdl.admin.module.business.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/customer")
@Tag(name = "用户", description = "用户")
public class CustomerUserController {
    @Autowired
    private UserAdminService userAdminService;

    @Operation(summary = "用户分页")
    @GetMapping("/page")
    public ResponseDTO<PageResult<CustomerUserModel>> pageUser(CustomerUserModel user, PageParam pageParam) {
        PageResult<CustomerUserModel> model = userAdminService.pageUser(user, pageParam);
        return ResponseDTO.ok(model);
    }

    @Operation(summary = "用户修改")
    @PostMapping("/edit")
    public ResponseDTO<Void> editUser(@RequestBody User user) {
        userAdminService.editUser(user);
        return ResponseDTO.ok();
    }
}
