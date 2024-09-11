package com._520it.crm.service;

import com._520it.crm.domain.Clew;
import com._520it.crm.req.ClewQueryObject;
import com._520it.crm.resp.PageResult;

/**
 * @author zhangxinyu
 */
public interface IClewService {
    int save(Clew c);

    int delete(Long id);

    Clew get(Long id);

    int update(Clew c);

    PageResult listAll();


    void luceneWriteIndex();

    PageResult querybyLuceneAll(String keyword);

    public PageResult querybyLuceneCondition(ClewQueryObject qo);

    int deleteLuceneById(Long id);

    int reload();

    String getContentById(Long id);
}
