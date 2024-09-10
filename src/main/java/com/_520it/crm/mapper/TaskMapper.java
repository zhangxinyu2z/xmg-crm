package com._520it.crm.mapper;


import com._520it.crm.domain.Task;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    Task selectByPrimaryKey(Long id);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);
    Long getCount(QueryObject qo);
	List<Task> selectByCondition(QueryObject qo);

	void deleteALL(Task task);
}