package com._520it.crm.mapper;

import com._520it.crm.domain.GuaranteeItem;

import java.util.List;

public interface GuaranteeItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GuaranteeItem record);

    GuaranteeItem selectByPrimaryKey(Long id);

    List<GuaranteeItem> selectAll();

    int updateByPrimaryKey(GuaranteeItem record);
    ////根据保修单查询对应的明细
	List<GuaranteeItem> selectAllItem(Long id);
   ////根据保修单id查询对应的明细总数
	Long selectItems(Long id);
	//根据明细的状态查询出对应的明细集合
	List<GuaranteeItem> queryStatusByItem(int status);
}