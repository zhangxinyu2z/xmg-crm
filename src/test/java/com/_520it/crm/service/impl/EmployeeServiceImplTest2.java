package com._520it.crm.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com._520it.crm.domain.Employee;
import com._520it.crm.service.EmployeeService;

/*
 * spring整合junit测试，引入配置文件，测试运行于spring环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public class EmployeeServiceImplTest2 {
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testDeleteByPrimaryKey() {
			
	}

	@Test
	public void testInsert() {
		Employee employee = new Employee();
		employeeService.insert(employee);
	}

	@Test
	public void testSelectByPrimaryKey() {

	}

	@Test
	public void testSelectAll() {

	}

	@Test
	public void testUpdateByPrimaryKey() {

	}

}
