package com._520it.crm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.page.PageResult;
import com._520it.crm.query.CustomerTransferQueryObject;
import com._520it.crm.service.CustomerTransferService;

@Controller
public class CustomerTransferController {
	@Autowired
	private CustomerTransferService customerTransferService;
	
	@RequestMapping("/customerTransfer")
	public String index() {
		return "customerTransfer";
	}
	
	@RequestMapping("/customerTransfer_list")
	@ResponseBody
	public PageResult list(CustomerTransferQueryObject qo) {
		return customerTransferService.list(qo);
	}
}
