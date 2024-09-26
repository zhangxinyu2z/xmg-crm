package com._520it.crm.mapper;

import com._520it.crm.domain.Customer;
import com._520it.crm.req.CustomerQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);

    /**
     * 查询当前用户负责的客户
     */
    List<Customer> queryForPage(CustomerQueryObject pageReq);

    /**
     * 查询当前用户负责的客户的数量
     */
    Long queryCount(CustomerQueryObject pageReq);

    /**
     * 修改当前客户状态
     *
     * @param status 1表示正式客户（sql需要录入时间），-1表示开发失败
     */
    void updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 修改客户的负责人
     *
     * @param ct
     */
    void updateInChargerUser(@Param("newSellerId")Long newSellerId, @Param("customerId") Long customerId);

    int updateByChargeId(@Param("id") Long id, @Param("inchargeId") Long inChargeId);

    // 吸纳资源池的用户
    int customerAdmit(@Param("inchargeId") Long inchargeId, @Param("id") Long id);

    // 移入资源池
    int moveToResourcePool(@Param("inchargeId") Long inchargeId, @Param("id") Long id);

    int lostCustomer(@Param("inchargeId") Long inChargeId, @Param("id") Long id);

    Long selectAllCount();

    int updateStatusFalseById(Long id);

    int updateStatusSuccessById(Long id);

    List<Customer> listAllFormalCustomer();

}