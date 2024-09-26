package com._520it.crm.mapper;

import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface CustomerTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerTransfer record);

    CustomerTransfer selectByPrimaryKey(Long id);

    List<CustomerTransfer> selectAll();

    int updateByPrimaryKey(CustomerTransfer record);

	/**
	 * @param qo
	 * @return
	 */
	Long queryCount(PageReq qo);

	/**
	 * @param qo
	 * @return
	 */
	List<CustomerTransfer> queryTransferList(PageReq qo);
}