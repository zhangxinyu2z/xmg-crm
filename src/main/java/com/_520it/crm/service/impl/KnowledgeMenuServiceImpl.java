package com._520it.crm.service.impl;

import com._520it.crm.domain.KnowledgeMenu;
import com._520it.crm.mapper.KnowledgeMenuMapper;
import com._520it.crm.service.IKnowledgeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KnowledgeMenuServiceImpl implements IKnowledgeMenuService{
     @Autowired
	 private KnowledgeMenuMapper dao;

	@Override
	public List<KnowledgeMenu> queryKnowledgeMenu() {
		return dao.selectRoot();
	}

}
