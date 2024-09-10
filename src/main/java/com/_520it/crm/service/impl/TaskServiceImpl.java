package com._520it.crm.service.impl;

import com._520it.crm.domain.Department;
import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Task;
import com._520it.crm.mapper.TaskMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.TaskQueryObject;
import com._520it.crm.service.IDepartmentService;
import com._520it.crm.service.ITaskService;
import com._520it.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {
	@Autowired
	private TaskMapper dao;
	
	@Autowired
	private IDepartmentService departmentService;

	@Override
	public int save(Task task) {
		return dao.insert(task);
	}

	@Override
	public int update(Task task) {
		return dao.updateByPrimaryKey(task);
	}

	@Override
	public int delete(Long id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public Task get(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<Task> selectAll() {
		return dao.selectAll();
	}
	
	@Override
	public PageResult selectByCondition(TaskQueryObject qo) {
	 	HttpServletRequest request = UserContext.get();
        Employee current = (Employee) request.getSession().getAttribute(UserContext.USER_IN_SESSION);
		if(current.getAdmin()){
			Long id = current.getId();
			List<Department> departments = departmentService.listAll();
		}else if (current!=null){
            Long id = current.getId();
            List<Department> departments = departmentService.getByMid(id);
            if (departments==null||departments.size()==0){
               qo.setHandleUserId(id);
            }
        }
        
		 Long total=dao.getCount(qo);
	        if (total > 0) {
	            List<Task> rows = dao.selectByCondition(qo);
	            PageResult pageResult = new PageResult(total, rows);
	            return pageResult;
	        }
	        return PageResult.EMPTY;
	}

	@Override
	public int change(Task todayTask) {
		Integer dayDelta = todayTask.getDayDelta();
		Date start = todayTask.getStart();
		Date end = todayTask.getEnd();
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.DAY_OF_MONTH,dayDelta);
		Date newStart = c.getTime();
		todayTask.setStart(newStart);
		c.setTime(end);
		c.add(Calendar.DAY_OF_MONTH,dayDelta);
		Date newEnd = c.getTime();
		todayTask.setEnd(newEnd);
		int i = dao.updateByPrimaryKey(todayTask);
		return i ;
	}
}
