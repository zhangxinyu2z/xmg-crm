package com._520it.crm.mapper;

import com._520it.crm.domain.Menu;
import java.util.List;

public interface MenuMapper {

	/**
	 * 获取所有的菜单
	 * @return
	 */
	List<Menu> queryForMenu();

}