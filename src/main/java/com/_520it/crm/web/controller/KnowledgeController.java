package com._520it.crm.web.controller;

import com._520it.crm.domain.KnowledgeMenu;
import com._520it.crm.service.IKnowledgeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 客户知识库
 *
 * @author
 */
@Controller
public class KnowledgeController {
    @Autowired
    private IKnowledgeMenuService knowledgeMenuService;

    @RequestMapping("/knowledge")
    public String index() {
        return "knowledge";
    }

    @RequestMapping("/knowledgeMenu")
    @ResponseBody
    public List<KnowledgeMenu> list() {
        List<KnowledgeMenu> result = null;
        result = knowledgeMenuService.queryKnowledgeMenu();
        return result;
    }

}
