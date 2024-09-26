package com._520it.crm.service.impl;

import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.req.CustomerTransferQueryObject;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTransferServiceImpl implements CustomerTransferService {
    @Autowired
    private CustomerTransferMapper customerTransferDao;

    @Override
    public PageResult queryPage(CustomerTransferQueryObject qo) {
        Long count = customerTransferDao.queryCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<CustomerTransfer> transferList = customerTransferDao.queryTransferList(qo);

        return new PageResult(count, transferList);
    }

    @Override
    public int save(CustomerTransfer transfer) {
        return customerTransferDao.insert(transfer);
    }

    @Override
    public int delete(Long id) {
        return customerTransferDao.deleteByPrimaryKey(id);
    }

    @Override
    public CustomerTransfer get(Long id) {
        return customerTransferDao.selectByPrimaryKey(id);
    }

    @Override
    public int update(CustomerTransfer transfer) {
        return customerTransferDao.updateByPrimaryKey(transfer);
    }

}
