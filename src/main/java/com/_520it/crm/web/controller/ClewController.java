package com._520it.crm.web.controller;

import com._520it.crm.page.AjaxResult;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.ClewQueryObject;
import com._520it.crm.service.IClewService;
import com._520it.crm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 爬取客户数据
 *
 * @author
 */
@Controller
public class ClewController extends BaseController {

    @Autowired
    private IClewService clewService;

    @RequestMapping("/clew")
    public String index() {
        return "clew";
    }


    @RequestMapping("/clew_list")
    @ResponseBody
    public PageResult list(ClewQueryObject qo) {
        PageResult result = null;
        try {
            if (StringUtils.hasLength(qo.getKeyword())) {
                result = clewService.querybyLuceneCondition(qo);
            } else {
                result = clewService.querybyLuceneAll("*:*");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/lucene_update")
    @ResponseBody
    public void updateLucenePool() {
        //更新数据库到lucene库中
        clewService.luceneWriteIndex();

    }

    @RequestMapping("/clew_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {

            int enffectcount = clewService.deleteLuceneById(id);
            if (enffectcount > 0) {
                result = new AjaxResult(true, "标记成功");
            } else {
                result = new AjaxResult(false, "标记失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "标记异常");
        }
        return result;
    }

    @RequestMapping("/clew_loadFromDataSorue")
    @ResponseBody
    public AjaxResult reload() {
        AjaxResult result = null;

        int enffectcount = clewService.reload();
        if (enffectcount > 0) {
            result = new AjaxResult(true, "加载成功");
        } else {
            result = new AjaxResult(false, "加载失败");
        }

        return result;
    }

    @RequestMapping("/clew_getContentById")
    @ResponseBody
    public Map<String, Object> getContentById(Long id, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String content = clewService.getContentById(id);
        System.out.println("content = " + content);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        return map;
    }
}
