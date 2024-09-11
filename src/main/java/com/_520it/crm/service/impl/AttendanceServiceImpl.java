package com._520it.crm.service.impl;

import com._520it.crm.domain.*;
import com._520it.crm.mapper.AttendanceMapper;
import com._520it.crm.mapper.EmployeeMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IAttendanceService;
import jxl.Workbook;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    private AttendanceMapper dao;
    @Autowired
    private EmployeeMapper employeeDAO;

    @Override
    public int save(Attendance attendance) {
        return dao.insert(attendance);
    }

    @Override
    public int delete(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public Attendance get(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int update(Attendance attendance) {
        return dao.updateByPrimaryKey(attendance);
    }

    @Override
    public List<Attendance> listAll() {
        return dao.selectAll();
    }

    /**
     * 该业务逻辑：首次导入，统计出当前员工的考勤天数，再次使用该功能即重新更新
     *
     * @return
     */
    @Override
    public int updateAttendance() {
        dao.deleteAll();
        List<Employee> emps = employeeDAO.selectAll();
        int effectCount = 0;
        for (Employee employee : emps) {
            Date date = new Date();
            // 获取到当前用户的id 通过id查询所有的考勤记录
            Long eid = employee.getId();
            Attendance att = dao.selectByEid(eid);
            // 获取迟到次数
            Long lateday = dao.getLateCount(eid);
            // 获取早退次数
            Long earlyday = dao.getEarlyCount(eid);
            // 获取出勤次数
            Long signinday = dao.getSigninCount(eid);
            //获取请假次数
            Long vacateday = dao.getVacateCount(eid);
            if (att == null) {
                Attendance attendance = new Attendance();
                attendance.setLateday(lateday);
                attendance.setEarlyday(earlyday);
                attendance.setSigninday(signinday);
                attendance.setVacateday(vacateday);
                attendance.setEmployee(employee);
                attendance.setDate(date);
                effectCount = dao.insert(attendance);
            } else { // 存在 更新
                att.setLateday(lateday);
                att.setEarlyday(earlyday);
                att.setSigninday(signinday);
                att.setVacateday(vacateday);
                att.setEmployee(employee);
                att.setDate(date);
                effectCount = dao.updateByPrimaryKey(att);
            }
        }
        return effectCount;
    }

    /**
     * 查询所有的考勤信息
     *
     * @param qo
     * @return
     */
    @Override
    public PageResult queryByCondition(PageReq qo) {
        // 根据查询条件查询出总条数
        Long count = dao.queryByConditionCount(qo);
        // 总条数为0,返回空的结果集
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CheckIn> listData = dao.queryByCondition(qo);
            return new PageResult(count, listData);
        }

    }

    /**
     * 员工个人考勤信息
     *
     * @param id
     * @return
     */
    @Override
    public PageResult queryAttendanceByEid(Long id) {
        // 根据查询条件查询出总条数
        Long count = dao.queryAttendanceByEidCount(id);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CheckIn> listData = dao.queryAttendanceByEid(id);
            return new PageResult(count, listData);
        }
    }

    @Override
    public int exportAttendance(ServletOutputStream outputStream) {
        WritableWorkbook wb = null;
        WritableSheet sheet = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 1.需要建立一个Excel工作的对象
        try {
            wb = Workbook.createWorkbook(outputStream);
            // 2.创建sheet主页
            sheet = wb.createSheet("第一个sheet页", 0);

            //定义高和宽
            sheet.setColumnView(1, 40);
            sheet.setRowView(1, 200);
            //设置普通格式化
            WritableCellFormat st = new WritableCellFormat();
            //设置单元格风格
            st.setAlignment(Alignment.CENTRE);
            st.setVerticalAlignment(VerticalAlignment.CENTRE);

            // 查询数据库中所有的数据
            List<Attendance> list = dao.selectAll();
            // 要插入到的Excel表格的行号，默认从0开始
            Label labelUsername = new Label(0, 0, "用户姓名");
            Label labelDate = new Label(1, 0, "日期");
            Label labelSignInDay = new Label(2, 0, "该月出勤天数");
            Label labelLateDay = new Label(3, 0, "该月迟到天数");
            Label labelEarlyDay = new Label(4, 0, "该月早退天数");
            Label labelVacateDay = new Label(5, 0, "该月请假次数");

            sheet.addCell(labelUsername);
            sheet.addCell(labelDate);
            sheet.addCell(labelSignInDay);
            sheet.addCell(labelLateDay);
            sheet.addCell(labelEarlyDay);
            sheet.addCell(labelVacateDay);
            for (int i = 0; i < list.size(); i++) {

                Label labelUsername_i = new Label(0, i + 1, list.get(i)
                        .getEmployee().getUsername()
                        + "", st);
                // 日期处理
                String date = sdf.format(list.get(i).getDate());
                Label labelDate_i = new Label(1, i + 1, date);

                Label labelSignInDay_i = new Label(2, i + 1, list.get(i)
                        .getSigninday() + "", st);

                Label labelLateDay_i = new Label(3, i + 1, list.get(i)
                        .getLateday() + "", st);

                Label labelEarlyDay_i = new Label(4, i + 1, list.get(i)
                        .getEarlyday() + "", st);

                Label labelVacateDay_i = new Label(5, i + 1, list.get(i)
                        .getVacateday() + "", st);

                sheet.addCell(labelUsername_i);
                sheet.addCell(labelDate_i);
                sheet.addCell(labelSignInDay_i);
                sheet.addCell(labelLateDay_i);
                sheet.addCell(labelEarlyDay_i);
                sheet.addCell(labelVacateDay_i);
            }
            // 写进文档
            wb.write();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            // 关闭Excel工作簿对象
            try {
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
