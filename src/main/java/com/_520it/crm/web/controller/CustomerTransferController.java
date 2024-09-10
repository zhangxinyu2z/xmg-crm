package com._520it.crm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.resp.PageResult;
import com._520it.crm.req.CustomerTransferPageReq;
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
	public PageResult list(CustomerTransferPageReq qo) {
		return customerTransferService.list(qo);
	}
}
