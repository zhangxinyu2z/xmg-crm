package com._520it.crm.service;

import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.req.CustomerTransferQueryObject;
import com._520it.crm.resp.PageResult;

public interface CustomerTransferService {
	PageResult queryPage(CustomerTransferQueryObject qo);

	int save(CustomerTransfer e);

	int delete(Long id);

	CustomerTransfer get(Long id);

	int update(CustomerTransfer e);

}
