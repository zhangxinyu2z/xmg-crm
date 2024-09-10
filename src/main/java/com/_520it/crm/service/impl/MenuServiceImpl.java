package com._520it.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.Menu;
import com._520it.crm.mapper.MenuMapper;
import com._520it.crm.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> queryForMenu() {
		List<Menu> menus = null;
		menus = menuMapper.queryForMenu();
		return menus;
	}

}
