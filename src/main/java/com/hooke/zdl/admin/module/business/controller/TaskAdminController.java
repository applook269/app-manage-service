package com.hooke.zdl.admin.module.business.controller;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.domain.ResponseDTO;
import com.hooke.zdl.admin.module.business.entity.Task;
import com.hooke.zdl.admin.module.business.service.TaskAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/task")
@Tag(name = "任务", description = "任务")
public class TaskAdminController {
    @Autowired
    private TaskAdminService taskAdminService;

    @Operation(summary = "新增任务")
    @PostMapping("/add")
    public ResponseDTO<String> addTask(@RequestBody Task Task) {
        taskAdminService.addTask(Task);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "修改任务")
    @PostMapping("/edit")
    public ResponseDTO<String> editTask(@RequestBody Task Task) {
        taskAdminService.editTask(Task);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "删除任务")
    @PostMapping("/remove")
    public ResponseDTO<String> removeTask(@RequestBody Task Task) {
        taskAdminService.removeTask(Task);
        return ResponseDTO.ok("成功");
    }

    @Operation(summary = "分页任务")
    @GetMapping("/page")
    public ResponseDTO<PageResult<Task>> pageTask(Task Task, PageParam pageParam) {
        PageResult<Task> page = taskAdminService.pageTask(Task, pageParam);
        return ResponseDTO.ok(page);
    }
}
