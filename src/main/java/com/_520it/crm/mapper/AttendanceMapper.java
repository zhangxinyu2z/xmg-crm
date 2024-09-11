package com._520it.crm.mapper;

import com._520it.crm.domain.Attendance;
import com._520it.crm.domain.CheckIn;
import com._520it.crm.req.PageReq;

import java.util.List;

public interface AttendanceMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Attendance record);

	Attendance selectByPrimaryKey(Long id);

	List<Attendance> selectAll();

	int updateByPrimaryKey(Attendance record);

	// 获取迟到天数
	Long getLateCount(Long eid);

	// 获取早退天数
	Long getEarlyCount(Long eid);

	// 获取出勤天数
	Long getSigninCount(Long eid);
	
	
	// 获取请假天数
	Long getVacateCount(Long eid);
	
	
	

	//判断数据库中是否已经存在
	Attendance selectByEid(Long eid);

	
	
	Long queryByConditionCount(PageReq qo);

	List<CheckIn> queryByCondition(PageReq qo);

	Long queryAttendanceByEidCount(Long id);

	List<CheckIn> queryAttendanceByEid(Long id);

	void deleteAll();

	


}