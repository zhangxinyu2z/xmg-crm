package com._520it.crm.service.impl;

import com._520it.crm.domain.OrderBill;
import com._520it.crm.mapper.OrderBillMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper dao;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderBill record) {
        return dao.insert(record);
    }

    @Override
    public OrderBill selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderBill> selectAll() {
        return dao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(OrderBill record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryByCondition(PageReq qo) {
        Long count = dao.queryByConditionCount(qo);
        if(count > 0){
            List<OrderBill> rows = dao.queryByCondition(qo);
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

    @Override
    public void updateStatus(Long id) {
        dao.updateStatus(id);
    }
}
