package com._520it.crm.web.controller;

import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.service.SystemDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
	public AjaxResult add(SystemDictionaryItem dicItem) {
		AjaxResult result = null;
		try {
			dicItem.setState(false);
			systemDictionaryItemService.insert(dicItem);
			result= new AjaxResult(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("添加失败，请联系管理员");
		}
		return result;
	}
	@RequestMapping("/sysDic_update")
	@ResponseBody
	public AjaxResult update(SystemDictionaryItem dicItem) {
		AjaxResult result = null;
		try {
			systemDictionaryItemService.updateByPrimaryKey(dicItem);
			result= new AjaxResult(true,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("添加失败，请联系管理员");
		}
		return result;
	}
	
	@RequestMapping("/sysDic_forbidden")
	@ResponseBody
	public AjaxResult forbidden(Long id) {
		AjaxResult result = null;
		try {
			systemDictionaryItemService.forbiddenDictionaryItem(id);
			result= new AjaxResult(true,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult("操作失败，请联系管理员");
		}
		return result;
	}
	
	@RequestMapping("/customer_queryDicItem")
	@ResponseBody
	public List<SystemDictionaryItem> queryDicItem(Integer id) {
		return systemDictionaryItemService.queryDicItem(id);
	}

	/**
	 * 查询字典编号对应的子元素数据
	 */
	@RequestMapping("/sysDic_queryBySn")
	@ResponseBody
	public List<SystemDictionaryItem> queryBySn(String sn) {
		return systemDictionaryItemService.queryBySn(sn);
	}

}
