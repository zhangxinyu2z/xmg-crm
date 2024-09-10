package com._520it.crm.service;

import com._520it.crm.domain.Contract;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;

import java.util.List;

/**
 * @author
 */
public interface IContractService {
    int deleteByPrimaryKey(Long id);

    int insert(Contract record);

    Contract selectByPrimaryKey(Long id);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    /**
     * 所有的合同
     * 动态查询+分页
     *
     * @param qo
     * @return
     */
    PageResult queryByCondition(QueryObject qo);

    /**
     * 审核合同
     *
     * @param id
     * @return
     */
    int checked(Long id);

    /**
     * 拒绝审核
     *
     * @param id
     * @return
     */
    int noChecked(Long id);
}
