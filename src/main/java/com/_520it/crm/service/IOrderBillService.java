package com._520it.crm.service;

import com._520it.crm.domain.OrderBill;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import java.util.List;

/**
 * @author
 */
public interface IOrderBillService {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill record);

    /**
     * 动态查询+分页
     * 查询所有的订单
     *
     * @param qo
     * @return
     */
    PageResult queryByCondition(PageReq qo);

    /**
     * 审核订单
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

    void updateStatus(Long id);
}
