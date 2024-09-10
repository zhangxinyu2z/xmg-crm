package com._520it.crm.service;

import com._520it.crm.domain.Task;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.TaskQueryObject;

import java.util.List;


public interface ITaskService {
	int save(Task task);
	int update(Task task);
	int delete(Long id);
	Task get(Long id);
	List<Task> selectAll();
	PageResult selectByCondition(TaskQueryObject qo);

    int change(Task todayTask);
}
