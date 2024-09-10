package com._520it.crm.service;

import com._520it.crm.resp.PageResult;
import com._520it.crm.req.PageReq;

public interface CustomerTransferService {
	PageResult list(PageReq qo);
}
