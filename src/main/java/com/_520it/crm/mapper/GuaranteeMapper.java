package com._520it.crm.mapper;

import com._520it.crm.domain.Guarantee;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface GuaranteeMapper {
	//删除
    int deleteByPrimaryKey(Long id);
    //保存
    int insert(Guarantee record);
    //查询单条
    Guarantee selectByPrimaryKey(Long id);
    //查询全部
    List<Guarantee> selectAll();
    //修改
    int updateByPrimaryKey(Guarantee record);
    //总条数
    Long selectByConditionCount(PageReq qo);
    //结果集
    List<Guarantee> selectByCondition(PageReq qo);
    //删除明细
    int  deleteByItems(Long id);
    //修改延保时间
	int updateTime(Long id);
 
}