package com._520it.crm.service.impl;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.EmployeeMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.EmployeeQueryObject;
import com._520it.crm.service.EmployeeService;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

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
		// 测试事务
		// int i = 1 / 0;
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
			return new PageResult(0, Collections.EMPTY_LIST);
		}
		List<Employee> employeeList = employeeDao.queryForPage(queryObject);
		return new PageResult(count.intValue(), employeeList);
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

	/* (non-Javadoc)
	 * @see com._520it.crm.service.EmployeeService#export(javax.servlet.ServletOutputStream)
	 */
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
			// 设置第一行的列名
			Label labelID = new Label(0, 0, "ID", scf);
			Label labelName = new Label(1, 0, "用户名", scf);
			Label labelReal = new Label(2, 0, "REALNAME", scf);
			Label labelPwd = new Label(3, 0, "PASSWORD", scf);
			Label labelTel = new Label(4, 0, "TEL", scf);
			Label labelEmail = new Label(5, 0, "EMAIL", scf);
			Label labelDeptID = new Label(6, 0, "DEPTID", scf);
			Label labelTime = new Label(7, 0, "INPUTTIME", scf);
			Label labelState = new Label(8, 0, "STATE", scf);
			Label labelAdmin = new Label(9, 0, "ADMIN", scf);
			sheet.addCell(labelID);
			sheet.addCell(labelName);
			sheet.addCell(labelReal);
			sheet.addCell(labelPwd);
			sheet.addCell(labelTel);
			sheet.addCell(labelEmail);
			sheet.addCell(labelDeptID);
			sheet.addCell(labelTime);
			sheet.addCell(labelState);
			sheet.addCell(labelAdmin);
			Label label = null;
			for (int i = 0; i < employeeList.size(); i++) {
				label = new Label(0, i + 1, employeeList.get(i).getId() + "", scf);
				sheet.addCell(label);
				label = new Label(1, i + 1, employeeList.get(i).getUsername() + "", scf);
				sheet.addCell(label);
				label = new Label(2, i + 1, employeeList.get(i).getRealname() + "", scf);
				sheet.addCell(label);
				label = new Label(3, i + 1, employeeList.get(i).getPassword() + "", scf);
				sheet.addCell(label);
				label = new Label(4, i + 1, employeeList.get(i).getTel() + "", scf);
				sheet.addCell(label);
				label = new Label(5, i + 1, employeeList.get(i).getEmail() + "", scf);
				sheet.addCell(label);
				label = new Label(6, i + 1, employeeList.get(i).getDept().getId() + "", scf);
				sheet.addCell(label);
				String date = dateFormat.format(employeeList.get(i).getInputtime());
				label = new Label(7, i + 1, date + "", scf);
				sheet.addCell(label);
				label = new Label(8, i + 1, employeeList.get(i).getState() + "", scf);
				sheet.addCell(label);
				label = new Label(9, i + 1, employeeList.get(i).getAdmin() + "", scf);
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

	/** (non-Javadoc)
	 * @see com._520it.crm.service.EmployeeService#queryEmployeeByRole()
	 */
	@Override
	public List<Employee> queryEmployeeByRole() {
		return employeeDao.queryEmployeeByRole();
		 
	}

}
