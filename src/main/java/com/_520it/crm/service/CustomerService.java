package com._520it.crm.service;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.req.*;
import com._520it.crm.resp.PageResult;

import java.util.List;

/**
 * @author xinyu
 * @date 2021/06/28
 */
public interface CustomerService {
	int deleteByPrimaryKey(Long id);

	int insert(Customer record);

	Customer selectByPrimaryKey(Long id);

	List<Customer> selectAll();

	/**
	 * 修改客户信息
	 */
	int updateByPrimaryKey(Customer record);

	/**
	 * 查询潜在客户和开发失败用户
	 * <p>
	 * 市场专员只能查询自己负责的
	 */
	PageResult queryForPage(PageReq pageReq);

	/**
	 * 修改潜在客户状态为-1；开发失败 1;正式客户
	 */
	void updateStatusById(Long id, Integer status);

	/**
	 * 转移潜在客户
	 */
	void transfer(CustomerTransfer ct);

	/**
	 * 查询所有的正式客户
	 */
	PageResult formalList(CustomerQueryObject qo);

	int updateByChargeId(Long id, Long inchargeuserId);

	int customerAdmit(Long id, Long id1);

	PageResult queryResourcePoolByCondition(CustomerResourcePoolQueryObject qo);

	boolean save(Customer c);

	int updateById(Customer c);

	int updateStatusFalseById(Long id);

	int updateStatusSuccessById(Long id);

	PageResult queryByCondition(PotentialCustomerQueryObject qo);
}
