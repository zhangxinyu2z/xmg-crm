package com._520it.crm.service.impl;

import com._520it.crm.domain.Contract;
import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.mapper.ContractChartMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IContractChartService;
import com._520it.crm.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractChartServiceImpl implements IContractChartService {

    @Autowired
    private ContractChartMapper dao;


    @Override
    public PageResult selectByCondition(PageReq qo) {
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:all")) {

        }
        Long count = dao.selectByConditionCount(qo);
        if (count > 0) {
            List<Contract> result = dao.selectByCondition(qo);
            return new PageResult(count, result);
        } else {
            return PageResult.EMPTY;
        }
    }

    @Override
    public List<String> selectTimeYear(PageReq qo) {
        return dao.selectTimeYear(qo);
    }


    @Override
    public List<Long> selectAmountYear(PageReq qo) {
        return dao.selectAmountYear(qo);
    }

    @Override
    public List<ContractChartVO> selectPie(PageReq qo) {
        return dao.selectPie(qo);
    }
}
