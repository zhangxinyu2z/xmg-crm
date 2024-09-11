package com._520it.crm.web.controller;

import com._520it.crm.domain.*;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.CustomerQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.service.CustomerService;
import com._520it.crm.utils.PermissionUtils;
import com._520it.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author xinyu
 * @date 2021/06/22
 */
@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/customer")
	public String index() {
		return "customer";
	}

	/**
	 * 潜在客户查询
	 * 
	 * @param qo
	 * @return
	 */
	@RequestMapping("/customer_list")
	@ResponseBody
	public PageResult list(CustomerQueryObject qo) {
		/*
		 * 用户的角色不同，能查询的信息也有所不同
		 * 市场专员只能查看自己负责的客户
		 * 市场经理可以查询所有的客户
		 * 因此要对当前用户的角色进行判断
		 */
		/*
		 * 角色信息由于管理员可以删除，直接通过角色表中判断不严谨，角色一定要分配权限
		 * 而主管和专员的权限有不同就是主管有移交权限，而市场专员没有
		 */
		boolean result = PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:transfer");
		if (result) {
			// 拥有该权限，查所有，不通过id

		} else {
			// 没有该权限，说明是市场专员,根据id查询
			Employee currentUser = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
			qo.setUserid(currentUser.getId());
		}

		return customerService.queryForPage(qo);
	}

	/**
	 * 正式客户查询，只有市场经理有权限
	 * 
	 * @param qo
	 * @return
	 */
	@RequestMapping("/formalcustomer_list")
	@ResponseBody
	public PageResult formalList(CustomerQueryObject qo) {
		return customerService.formalList(qo);
	}

	@RequestMapping("/customer_save")
	@ResponseBody
	public AjaxResult save(Customer customer) {
		AjaxResult result = null;
		try {
			Employee currentUser = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);

			customer.setInputuser(currentUser);
			customer.setInchargeuser(currentUser);
			customer.setInputtime(new Date());
			// 新增用户状态为潜在客户 sql插入为0
			customerService.insert(customer);
			result = new AjaxResult(true, "录入成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("警告，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/customer_update")
	@ResponseBody
	public AjaxResult update(Customer customer) {
		AjaxResult result = null;
		try {
			// 修改sql,只修改操作的信息，其它的status、user之类的不要修改
			customerService.updateByPrimaryKey(customer);
			result = new AjaxResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("警告，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/customer_fail")
	@ResponseBody
	public AjaxResult fail(Long id) {
		AjaxResult result = null;
		try {
			customerService.updateStatusById(id, -1);
			result = new AjaxResult(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("警告，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/customer_become")
	@ResponseBody
	public AjaxResult become(Long id) {
		AjaxResult result = null;
		try {
			customerService.updateStatusById(id, 1);
			result = new AjaxResult(true, "转正成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("警告，请联系管理员");
		}
		return result;
	}

	@RequestMapping("/customer_share")
	@ResponseBody
	public AjaxResult transfer(CustomerTransfer ct) {
		AjaxResult result = null;
		Employee user = (Employee)UserContext.get().getSession().getAttribute(UserContext.USER_IN_SESSION);
		if (ct.getOldseller().getId() == ct.getNewseller().getId()) {
			result = new AjaxResult("您不能移交给原负责人啊");
			return result;
		}
		try {
			// 移交的操作员
			ct.setTransuser(user);
			customerService.transfer(ct);
			result = new AjaxResult(true, "移交成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("请联系管理员");
		}
		return result;
	}
}
