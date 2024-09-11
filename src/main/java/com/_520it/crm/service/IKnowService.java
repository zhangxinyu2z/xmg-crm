package com._520it.crm.service;

import com._520it.crm.domain.Know;
import com._520it.crm.req.KnowQueryObject;
import com._520it.crm.resp.PageResult;

import java.util.List;

public interface IKnowService {
	 int delete(Long id);
	    int save(Know record);
	    Know selectByPrimaryKey(Long id);
	    List<Know> selectAll();
	    int update(Know record);
	    void indexWriterReload();
	    PageResult query(KnowQueryObject qo);
	    PageResult querySearch(KnowQueryObject qo);
		Know queryContextById(Long id);
}
