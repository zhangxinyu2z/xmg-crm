package com._520it.crm.web.controller;

import com._520it.crm.domain.Menu;
import com._520it.crm.utils.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/queryForMenu")
    @ResponseBody
    public List<Menu> queryForMenu() {
        // 从session中获取当前用户拥有的权限菜单
        return (List<Menu>)UserContext.get().getSession().getAttribute(UserContext.MENU_IN_SESSION);
        // return menuService.queryForMenu();
    }
}
