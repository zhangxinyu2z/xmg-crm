package com._520it.crm.service;

import com._520it.crm.domain.Attendance;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import javax.servlet.ServletOutputStream;
import java.util.List;


public interface IAttendanceService {

    int save(Attendance attendance);

    int delete(Long id);

    Attendance get(Long id);

    int update(Attendance attendance);

    List<Attendance> listAll();

    /**
     * 查询所有的考勤统计
     *
     * @param qo
     * @return
     */
    PageResult queryByCondition(PageReq qo);

    int updateAttendance();

    /**
     * 查询员工的考勤统计
     *
     * @param id
     * @return
     */
    PageResult queryAttendanceByEid(Long id);

    /**
     * 导出员工考勤统计到execl中
     *
     * @param outputStream
     * @return
     */
    int exportAttendance(ServletOutputStream outputStream);


}
