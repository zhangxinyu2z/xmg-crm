package com._520it.crm.web.controller;

import com._520it.crm.domain.Know;
import com._520it.crm.req.KnowQueryObject;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IKnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class KnowController {
	@Autowired
    private IKnowService KnowService;
/*	@RequestMapping("/know_list")
	@ResponseBody
	public List<Know> list(){
		List<Know> result = null;
		result = KnowService.selectAll();
		return result;
	}*/
	
	@RequestMapping("/know_list")
	@ResponseBody
	public PageResult list(KnowQueryObject qo){
		PageResult result = null;
		if(qo.getKeyword() != null&& !"".equals(qo.getKeyword())){
			result = KnowService.querySearch(qo);
		}else{
			result = KnowService.query(qo);
		}
		return result;
	}
	
	
	
	@RequestMapping("/konw_listAll")
	@ResponseBody
	public Know listAll(Long id){
		Know result  = null;
		result = KnowService.queryContextById(id);
		return result;
	}
	


}
