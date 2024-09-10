package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.CustomerTransferService;

@Service
public class CustomerTransferServiceImpl implements CustomerTransferService {
	@Autowired
	private CustomerTransferMapper customerTransferDao;
	
	@Override
	public PageResult list(QueryObject qo) {
		Long count = customerTransferDao.queryCount(qo);
		if(count ==0) {
		return new PageResult(0,Collections.EMPTY_LIST);
		}
		List<CustomerTransfer> transferList = customerTransferDao.queryTransferList(qo);
		
		return new PageResult(count.intValue(), transferList);
	}

}
