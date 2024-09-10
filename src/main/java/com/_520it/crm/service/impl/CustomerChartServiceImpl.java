package com._520it.crm.service.impl;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerVO;
import com._520it.crm.mapper.CustomerChartMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.CustomerChartQueryObject;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.ICustomerChartService;
import com._520it.crm.util.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerChartServiceImpl implements ICustomerChartService {
    @Autowired
    CustomerChartMapper dao;


    @Override
    public List<String> selectTimeYear(QueryObject qo) {
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:all")) {
            CustomerChartQueryObject chartQo = (CustomerChartQueryObject) qo;
            chartQo.setUserId(null);
        }
        return dao.selectTimeYear(qo);
    }


    public List<Long> selectAmountYear(QueryObject qo) {
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:all")) {
            CustomerChartQueryObject chartQo = (CustomerChartQueryObject) qo;
            chartQo.setUserId(null);
        }
        return dao.selectAmountYear(qo);
    }

    @Override
    public List<CustomerVO> selectPie(QueryObject qo) {
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:all")) {
            CustomerChartQueryObject chartQo = (CustomerChartQueryObject) qo;
            chartQo.setUserId(null);
        }
        return dao.selectPie(qo);
    }


    @Override
    public PageResult selectByCondition(QueryObject qo) {
        if (PermissionUtils.checkPermission("com._520it.crm.web.controller.CustomerController:all")) {
            CustomerChartQueryObject chartQo = (CustomerChartQueryObject) qo;
            chartQo.setUserId(null);
        }
        Long count = dao.queryByConditionCount(qo);
        if (count > 0) {
            List<Customer> result = dao.queryByCondition(qo);
            return new PageResult(count, result);
        } else {
            return PageResult.EMPTY;
        }
    }


}
