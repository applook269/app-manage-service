package com.hooke.zdl.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hooke.zdl.admin.common.controller.SupportBaseController;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.RequestUser;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.common.util.SmartRequestUtil;
import com.hooke.zdl.admin.constant.SwaggerTagConst;
import com.hooke.zdl.admin.module.support.operatelog.OperateLogService;
import com.hooke.zdl.admin.module.support.operatelog.domain.OperateLogQueryForm;
import com.hooke.zdl.admin.module.support.operatelog.domain.OperateLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志
 */
@RestController
@Tag(name = SwaggerTagConst.Support.OPERATE_LOG)
public class AdminOperateLogController extends SupportBaseController {

    @Resource
    private OperateLogService operateLogService;

    @Operation(summary = "分页查询")
    @PostMapping("/operateLog/page/query")
    @SaCheckPermission("support:operateLog:query")
    public ResponseDTO<PageResult<OperateLogVO>> queryByPage(@RequestBody OperateLogQueryForm queryForm) {
        return operateLogService.queryByPage(queryForm);
    }

    @Operation(summary = "详情")
    @GetMapping("/operateLog/detail/{operateLogId}")
    @SaCheckPermission("support:operateLog:detail")
    public ResponseDTO<OperateLogVO> detail(@PathVariable Long operateLogId) {
        return operateLogService.detail(operateLogId);
    }

    @Operation(summary = "分页查询当前登录人信息")
    @PostMapping("/operateLog/page/query/login")
    public ResponseDTO<PageResult<OperateLogVO>> queryByPageLogin(@RequestBody OperateLogQueryForm queryForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        queryForm.setOperateUserId(requestUser.getUserId());
        queryForm.setOperateUserType(requestUser.getUserType().getValue());
        return operateLogService.queryByPage(queryForm);
    }

}
