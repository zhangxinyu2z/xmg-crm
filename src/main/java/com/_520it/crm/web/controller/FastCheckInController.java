package com._520it.crm.web.controller;

import com._520it.crm.service.ICheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FastCheckInController extends BaseController {

	@Autowired
	private ICheckInService checkInService;

	@RequestMapping("/fastCheckIn")
	public String index() {
		return "fastCheckIn";
	}

}
