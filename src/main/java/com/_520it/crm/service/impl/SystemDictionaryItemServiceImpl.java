package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.SystemDictionaryItem;
import com._520it.crm.mapper.SystemDictionaryItemMapper;
import com._520it.crm.resp.PageResult;
import com._520it.crm.req.PageReq;
import com._520it.crm.service.SystemDictionaryItemService;

@Service
public class SystemDictionaryItemServiceImpl implements SystemDictionaryItemService {
	@Autowired
	private SystemDictionaryItemMapper systemDictionaryItemDao;

	@Override
	public PageResult queryItemById(PageReq qo) {
		Long count = systemDictionaryItemDao.queryCount(qo);
		if (count == 0) {
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<SystemDictionaryItem> itemList = systemDictionaryItemDao.queryForPageById(qo);
		return new PageResult(count.intValue(), itemList);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return systemDictionaryItemDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SystemDictionaryItem record) {
		// TODO 不应该添加重复的数据，没有校验
		return systemDictionaryItemDao.insert(record);
	}

	@Override
	public SystemDictionaryItem selectByPrimaryKey(Long id) {
		return systemDictionaryItemDao.selectByPrimaryKey(id);
	}

	@Override
	public List<SystemDictionaryItem> selectAll() {
		return systemDictionaryItemDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(SystemDictionaryItem record) {
		return systemDictionaryItemDao.updateByPrimaryKey(record);
	}

	/**
	 * 禁用字典明细
	 */
	@Override
	public void forbiddenDictionaryItem(Long id) {
		SystemDictionaryItem item = systemDictionaryItemDao.selectByPrimaryKey(id);
		item.setState(true);
		systemDictionaryItemDao.updateByPrimaryKey(item);
	}

	@Override
	public List<SystemDictionaryItem> queryDicItem(Integer id) {
		return systemDictionaryItemDao.queryDicItem(id);
	}

}
