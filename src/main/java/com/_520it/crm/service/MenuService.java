package com._520it.crm.service;

import java.util.List;

import com._520it.crm.domain.Menu;

/**
 * @author xinyu
 * @date 2021/06/21
 */
public interface MenuService {

	/**
	 * 获取所有的菜单节点
	 */
	List<Menu> queryForMenu();
	
}
