package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.TaskMapper;
import com.hooke.zdl.admin.module.business.entity.Task;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.hooke.zdl.admin.module.business.entity.table.TaskTableDef.TASK;

@Service
public class TaskAdminService extends ServiceImpl<TaskMapper, Task> {
    public void addTask(Task task) {
        save(task);
    }

    public void editTask(Task task) {
        updateById(task);
    }

    public void removeTask(Task task) {
        removeById(task);
    }

    public PageResult<Task> pageTask(Task task, PageParam pageParam) {
        Page<Task> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Task> taskPage = QueryChain.of(Task.class)
                .where(TASK.NAME.like(task.getName()))
                .page(page);
        return SmartPageUtil.convert2PageResult(page, taskPage.getRecords(), Task.class);
    }
}
