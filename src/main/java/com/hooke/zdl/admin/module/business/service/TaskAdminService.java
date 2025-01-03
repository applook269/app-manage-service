package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.TaskMapper;
import com.hooke.zdl.admin.module.business.entity.Task;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        Page<Task> walletPage = page(page, QueryWrapper.create(task));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), Task.class);
    }
}
