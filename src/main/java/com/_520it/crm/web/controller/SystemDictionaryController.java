package com._520it.crm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com._520it.crm.domain.SystemDictionary;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.SystemDictionaryItemQueryObject;
import com._520it.crm.req.SystemDictionaryQueryObject;
import com._520it.crm.service.SystemDictionaryItemService;
import com._520it.crm.service.SystemDictionaryService;

/**
 * @author xinyu
 * @date 2021/06/26
 */
@Controller
public class SystemDictionaryController {
	@Autowired
	private SystemDictionaryService systemDictionaryService;
	@Autowired
	private SystemDictionaryItemService systemDictionaryItemService;
	
	@RequestMapping("/systemDictionary")
	public String index() {
		return "systemDictionary";
	}
	
	@RequestMapping("/systemDictionaryList")
	@ResponseBody
	public PageResult list(SystemDictionaryQueryObject qo) {
		PageResult result = systemDictionaryService.queryForPage(qo);
		return result;
	}
	
	@RequestMapping("/querySystemDictionaryItemById")
	@ResponseBody
	public PageResult itemList(SystemDictionaryItemQueryObject qo) {
		PageResult result = systemDictionaryItemService.queryItemById(qo);
		return result;
	}

	@RequestMapping("/sysDic_queryAllDic")
	@ResponseBody
	public List<SystemDictionary> queryAllDic() {
		return systemDictionaryService.selectAll();
	}
}
