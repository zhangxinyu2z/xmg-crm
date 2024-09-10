package com._520it.crm.mapper;

import com._520it.crm.domain.OrderBill;
import com._520it.crm.query.QueryObject;

import java.util.List;

public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill record);

    List<OrderBill> queryByCondition(QueryObject qo);

    Long queryByConditionCount(QueryObject qo);

    int checked(Long id);

    int noChecked(Long id);

    void updateStatus(Long id);
}