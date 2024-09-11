package com._520it.crm.service.impl;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Salary;
import com._520it.crm.mapper.SalaryMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.ISalaryService;
import jxl.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SalaryServiceImpl implements ISalaryService {

    @Autowired
    private SalaryMapper dao;

    @Override
    public int save(Salary salary) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public Salary get(Long id) {
        return null;
    }

    @Override
    public int update(Salary salary) {
        return 0;
    }

    @Override
    public List<Salary> listAll() {
        return dao.selectAll();
    }

    @Override
    public PageResult queryByCondition(PageReq qo) {
        // 根据查询条件查询出总条数
        Long count = dao.queryByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Salary> listData = dao.queryByCondition(qo);
            return new PageResult(count, listData);
        }
    }

    @Override
    public PageResult querySalaryByEid(Long id) {

        // 根据查询条件查询出总条数
        Long count = dao.querySalaryByEidCount(id);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Salary> listData = dao.querySalaryByEid(id);
            return new PageResult(count, listData);
        }
    }

    @Override
    public void uploadSalary(MultipartFile file) {
        dao.deleteAll();
        Workbook wb = null;
        InputStream is = null;
        try {
            //获取工作本对象
            is = file.getInputStream();
            wb = Workbook.getWorkbook(is);
            //获取sheet页信息
            Sheet sheet = wb.getSheet(0);
            //获取行列
            //3.获取到行列信息
            int rows = sheet.getRows();
            int cols = sheet.getColumns();
            Salary salary = new Salary();
            //用来存employee_id
            Employee employee = new Employee();
            //i为横坐标
            for (int i = 1; i < rows; i++) {
                //从第二行开始读取.  第一列 :员工id
                String eid = sheet.getCell(0, i).getContents();
                //第二列 :日期---时间格式化----
                Cell cell = sheet.getCell(1, i);
                Date date = null;
                if (cell.getType() == CellType.DATE) {
                    DateCell dc = (DateCell) cell;
                    //获取单元格的date类型
                    date = dc.getDate();
                }
                //第三列   薪资
                String sal = sheet.getCell(2, i).getContents();

                //把数据封装到对象中
                //设置员工id
                if (!StringUtils.isEmpty(eid)) {
                    employee.setId(Long.parseLong(eid));
                    salary.setEmployee(employee);
                }
                if (date != null) {
                    salary.setDate(date);
                }
                if (!StringUtils.isEmpty(sal)) {
                    salary.setSalary(new BigDecimal(sal));
                }
                //保存到数据库中
                dao.insert(salary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
