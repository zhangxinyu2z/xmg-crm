package com._520it.crm.mapper;

import com._520it.crm.domain.CheckIn;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface CheckInMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CheckIn record);

    CheckIn selectByPrimaryKey(Long id);

    List<CheckIn> selectAll();

    int updateByPrimaryKey(CheckIn record);

    Long queryByConditionCount(PageReq qo);

    List<CheckIn> queryByCondition(PageReq qo);


    Long queryCheckInByEidCount(Long id);

    /**
     * 查询当前用户的考勤记录
     * @param id
     * @return
     */
    List<CheckIn> queryCheckInByEid(Long id);
}