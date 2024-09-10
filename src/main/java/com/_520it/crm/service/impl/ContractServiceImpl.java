package com._520it.crm.service.impl;

import com._520it.crm.domain.Contract;
import com._520it.crm.mapper.ContractMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class ContractServiceImpl implements IContractService {

    @Autowired
    private ContractMapper dao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Contract record) {
        return dao.insert(record);
    }

    @Override
    public Contract selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<Contract> selectAll() {
        return dao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Contract record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryByCondition(QueryObject qo) {
        Long count = dao.queryByConditionCount(qo);
        if(count > 0){
            List<Contract> rows = dao.queryByCondition(qo);
            return new PageResult(count,rows);
        }else{
            return PageResult.EMPTY;
        }
    }

    @Override
    public int checked(Long id) {
        return dao.checked(id);
    }

    @Override
    public int noChecked(Long id) {
        return dao.noChecked(id);
    }
}
