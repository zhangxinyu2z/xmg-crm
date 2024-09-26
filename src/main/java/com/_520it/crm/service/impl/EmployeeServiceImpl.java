package com._520it.crm.service.impl;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.EmployeeMapper;
import com._520it.crm.req.EmployeeQueryObject;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.EmployeeService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.lang.Boolean;

/**
 * @author zhang xinyu
 * @date 2021/06/16
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return employeeDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Employee record) {
        // 影响行数
        int effectCount = employeeDao.insert(record);
        // 插入中间表信息
        for (Role role : record.getRoles()) {
            employeeDao.insertRelation(record.getId(), role.getId());
        }
        return effectCount;
    }

    @Override
    public Employee selectByPrimaryKey(Long id) {
        return employeeDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> selectAll() {
        return employeeDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Employee record) {
        // 更新员工信息
        int effectCount = employeeDao.updateByPrimaryKey(record);
        // 删除旧有的角色信息
        employeeDao.deleteRelation(record.getId());
        // 添加新的角色信息
        for (Role role : record.getRoles()) {
            employeeDao.insertRelation(record.getId(), role.getId());
        }
        return effectCount;
    }

    @Override
    public Employee getEmployeeForLogin(String username, String password) {
        return employeeDao.getEmployeeForLogin(username, password);
    }

    /**
     * 查询分页数据
     * <p>
     * 查询总记录数，如果不为0，根据分页条件查询分页数据
     */
    @Override
    public PageResult queryForPage(EmployeeQueryObject queryObject) {
        Long count = employeeDao.queryForPageCount(queryObject);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<Employee> employeeList = employeeDao.queryForPage(queryObject);
        return new PageResult(count, employeeList);
    }

    /**
     * 离职员工，修改状态为0
     */
    @Override
    public void updateState(long id) {
        employeeDao.updateState(id);
    }

    @Override
    public List<Long> queryRoleById(Long eid) {
        return employeeDao.queryRoleById(eid);
    }

    @Override
    public void export(ServletOutputStream outputStream) {
        // 查出所有数据
        List<Employee> employeeList = employeeDao.selectAll();

        // 设计表格
        WritableWorkbook createWorkbook = null;
        try {
            createWorkbook = Workbook.createWorkbook(outputStream);
            // 2、创建工作表，插入顺序
            WritableSheet sheet = createWorkbook.createSheet("sheet1", 0);
            // 3、高宽
            sheet.setColumnView(1, 30);
            sheet.setRowView(1, 500);
            // 4、水平垂直居中
            WritableCellFormat scf = new WritableCellFormat();
            scf.setAlignment(Alignment.CENTRE);
            scf.setVerticalAlignment(VerticalAlignment.CENTRE);
            // 日期的格式处理
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 表头
            sheet.addCell(new Label(0, 0, "ID", scf));
            sheet.addCell(new Label(1, 0, "用户名", scf));
            sheet.addCell(new Label(2, 0, "真实姓名", scf));
            //Label labelPwd = new Label(3, 0, "PASSWORD", scf);
            sheet.addCell(new Label(4, 0, "TEL", scf));
            sheet.addCell(new Label(5, 0, "邮箱", scf));
            //sheet.addCell(new Label(6, 0, "DEPTID", scf));
            sheet.addCell(new Label(7, 0, "入职时间", scf));
            sheet.addCell(new Label(8, 0, "在职状态", scf));
            sheet.addCell(new Label(9, 0, "用户类型", scf));
            Label label = null;
            //行数据
            for (int i = 0; i < employeeList.size(); i++) {
                label = new Label(0, i + 1, employeeList.get(i).getId() + "", scf);
                sheet.addCell(label);
                label = new Label(1, i + 1, employeeList.get(i).getUsername() + "", scf);
                sheet.addCell(label);
                label = new Label(2, i + 1, employeeList.get(i).getRealname() + "", scf);
                sheet.addCell(label);
                //label = new Label(3, i + 1, employeeList.get(i).getPassword() + "", scf);
                //sheet.addCell(label);
                label = new Label(4, i + 1, employeeList.get(i).getTel() + "", scf);
                sheet.addCell(label);
                label = new Label(5, i + 1, employeeList.get(i).getEmail() + "", scf);
                sheet.addCell(label);
                //label = new Label(6, i + 1, employeeList.get(i).getDept().getId() + "", scf);
                //sheet.addCell(label);
                String date = dateFormat.format(employeeList.get(i).getInputtime());
                label = new Label(7, i + 1, date + "", scf);
                sheet.addCell(label);
                Boolean state = employeeList.get(i).getState();
                label = new Label(8, i + 1, state ? "在职" : "离职", scf);
                sheet.addCell(label);
                Boolean admin = employeeList.get(i).getAdmin();
                label = new Label(9, i + 1, admin ? "管理员" : "普通用户", scf);
                sheet.addCell(label);
            }

            createWorkbook.write();

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (createWorkbook != null) {
                try {
                    createWorkbook.close();
                } catch (WriteException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public List<Employee> queryEmployeeByRoleId(Long roleId) {
        return employeeDao.queryEmployeeByRoleId(roleId);
    }
}
