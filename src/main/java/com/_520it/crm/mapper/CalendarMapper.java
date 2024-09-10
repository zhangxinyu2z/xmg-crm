package com._520it.crm.mapper;

import com._520it.crm.domain.Calendar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CalendarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Calendar record);

    Calendar selectByPrimaryKey(Long id);

    List<Calendar> selectAll();

    int updateByPrimaryKey(Calendar record);

    int updateDayDelta(Long id,Calendar start, Calendar end);

    int updateDayDelta(@Param("id") Long id, @Param("startInt") int startInt,@Param("endInt") int endInt);
}