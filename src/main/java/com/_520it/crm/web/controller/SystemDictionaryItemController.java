package com._520it.crm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.service.SystemDictionaryItemService;
import com._520it.crm.resp.AJAXResult;

/**
 * @author xinyu
 * @date 2021/06/26
 */
@Controller
public class SystemDictionaryItemController {
	@Autowired
	private SystemDictionaryItemService  systemDictionaryItemService;
	
	@RequestMapping("/sysDic_add")
	@ResponseBody
	public AJAXResult add(SystemDictionaryItem dicItem) {
		AJAXResult result = null;
		try {
			dicItem.setState(false);
			systemDictionaryItemService.insert(dicItem);
			result= new AJAXResult(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("添加失败，请联系管理员");
		}
		return result;
	}
	@RequestMapping("/sysDic_update")
	@ResponseBody
	public AJAXResult update(SystemDictionaryItem dicItem) {
		AJAXResult result = null;
		try {
			systemDictionaryItemService.updateByPrimaryKey(dicItem);
			result= new AJAXResult(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("添加失败，请联系管理员");
		}
		return result;
	}
	
	@RequestMapping("/sysDic_forbidden")
	@ResponseBody
	public AJAXResult forbidden(Long id) {
		AJAXResult result = null;
		try {
			systemDictionaryItemService.forbiddenDictionaryItem(id);
			result= new AJAXResult(true,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AJAXResult("操作失败，请联系管理员");
		}
		return result;
	}
	
	@RequestMapping("/customer_queryDicItem")
	@ResponseBody
	public List<SystemDictionaryItem> queryDicItem(Integer id) {
		return systemDictionaryItemService.queryDicItem(id);
	}
	
	
}
