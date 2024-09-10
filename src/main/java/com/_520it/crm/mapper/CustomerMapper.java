package com._520it.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.query.CustomerQueryObject;
import com._520it.crm.query.QueryObject;

public interface CustomerMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Customer record);

	Customer selectByPrimaryKey(Long id);

	List<Customer> selectAll();

	int updateByPrimaryKey(Customer record);

	/**
	 * 查询当前用户负责的客户
	 */
	List<Customer> queryForPage(QueryObject queryObject);

	/**
	 * 查询当前用户负责的客户的数量
	 */
	Long queryCount(QueryObject queryObject);

	/**
	 * 修改当前客户状态
	 * 
	 * @param status
	 *            1表示正式客户（sql需要录入时间），-1表示开发失败
	 */
	void updateStatusById(@Param("id") Long id, @Param("status") Integer status);

	/**
	 * 修改客户的负责人
	 * 
	 * @param ct
	 */
	void updateInChargerUser(CustomerTransfer ct);

	/**
	 * 查询满足条件的所有正式客户的统计数量
	 */
	Long queryFormalCustomerCount(CustomerQueryObject qo);

	/**
	 * 查询满足条件的所有正式客户
	 * @param qo
	 * @return
	 */
	List<Customer> queryFormalCustomer(CustomerQueryObject qo);
}