package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.mapper.CustomerMapper;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.CustomerQueryObject;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerMapper customerDao;
	@Autowired
	private CustomerTransferMapper customerTransferDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return customerDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Customer record) {
		return customerDao.insert(record);
	}

	@Override
	public Customer selectByPrimaryKey(Long id) {
		return customerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Customer> selectAll() {
		return customerDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Customer record) {
		return customerDao.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryForPage(QueryObject qo) {
		Long count = customerDao.queryCount(qo);
		if (count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<Customer> customerList = customerDao.queryForPage(qo);
		return new PageResult(count.intValue(), customerList);
	}

	@Override
	public void updateStatusById(Long id, Integer status) {
		// 开发失败，修改客户状态
		customerDao.updateStatusById(id, status);
	}

	@Override
	public void transfer(CustomerTransfer ct) {
		// 1、修改客户的负责人
		customerDao.updateInChargerUser(ct);
		// 2、插入一条修改记录
		customerTransferDao.insert(ct);
	}

	@Override
	public PageResult formalList(CustomerQueryObject qo) {
		Long count = customerDao.queryFormalCustomerCount(qo);
		if (count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<Customer> customerList = customerDao.queryFormalCustomer(qo);
		return new PageResult(count.intValue(), customerList);
	}

}
