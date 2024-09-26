package com._520it.crm.service.impl;

import com._520it.crm.domain.Customer;
import com._520it.crm.domain.CustomerTransfer;
import com._520it.crm.domain.Employee;
import com._520it.crm.mapper.CustomerMapper;
import com._520it.crm.mapper.CustomerTransferMapper;
import com._520it.crm.req.CustomerQueryObject;
import com._520it.crm.req.TransferCustomerReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.CustomerService;
import com._520it.crm.service.CustomerTransferService;
import com._520it.crm.service.EmployeeService;
import com._520it.crm.utils.UserContext;
import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerDao;
    @Autowired
    private CustomerTransferMapper customerTransferDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerTransferService customerTransferService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Customer record) {
        return customerDao.insert(record);
    }

    @Override
    public Customer selectByPrimaryKey(Long id) {
        return customerDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> selectAll() {
        return customerDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Customer record) {
        return customerDao.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryForPage(CustomerQueryObject qo) {
        Long count = customerDao.queryCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        }
        List<Customer> customerList = customerDao.queryForPage(qo);
        return new PageResult(count, customerList);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        // 开发失败，修改客户状态
        customerDao.updateStatusById(id, status);
    }

    @Override
    public void transfer(TransferCustomerReq req) {
        // 1、修改客户的负责人
        customerDao.updateInChargerUser(req.getNewSellerId(), req.getCustomerId());
        // 2、插入一条修改记录
        CustomerTransfer customerTransfer = new CustomerTransfer();
        BeanUtils.copyProperties(req, customerTransfer);
        customerTransfer.setTranstime(new Date());
        // customerTransfer.setTransreason(req.getTransReason());
        // customerTransfer.setCustomerId(req.getCustomerId());
        // customerTransfer.setTransUserId(req.getTransUserId());
        // customerTransfer.setOldSellerId(req.getOldSellerId());
        // customerTransfer.setNewSellerId(req.getNewSellerId());
        customerTransferDao.insert(customerTransfer);
    }

    @Override
    public int updateByChargeId(Long id, Long inchargeuserId) {
        return customerDao.updateByChargeId(id, inchargeuserId);
    }

    @Override
    public int customerAdmit(Long id, Long id1) {
        return customerDao.customerAdmit(id, id1);
    }

    @Override
    public boolean save(Customer c) {
        int insert = customerDao.insert(c);
        return insert > 0;
    }

    @Override
    public int updateById(Customer c) {
        return customerDao.updateByPrimaryKey(c);
    }

    @Override
    public int updateStatusFalseById(Long id) {
        return customerDao.updateStatusFalseById(id);
    }

    @Override
    public int updateStatusSuccessById(Long id) {
        return customerDao.updateStatusSuccessById(id);
    }

    @Override
    public boolean shareOrTransfer(Customer c, Long inchargeId, String reason) {
        Employee employee = UserContext.getCurrentLoginEmployee(UserContext.USER_IN_SESSION, Employee.class);
        // 创建移交记录对象
        CustomerTransfer transfer = new CustomerTransfer();
        transfer.setCustomer(c);
        transfer.setOldseller(c.getInchargeuser());
        transfer.setNewseller(employeeService.selectByPrimaryKey(inchargeId));
        transfer.setTranstime(new Date());
        transfer.setTransuser(employee);
        transfer.setTransreason(reason);
        // 创建移交记录
        customerTransferService.save(transfer);
        int effectCount = updateByChargeId(c.getId(), inchargeId);
        return effectCount > 0;
    }

    @Override
    public boolean moveToResourcePool(Long id, Long id1) {
        return customerDao.moveToResourcePool(id, id1) > 0;
    }

    @Override
    public boolean lostCustomer(Long id, Long id1) {
        return customerDao.lostCustomer(id, id1) > 0;
    }

    @Override
    public int exportCustomer(ServletOutputStream outputStream) {
        WritableWorkbook wb = null;
        WritableSheet sheet = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 1.需要建立一个Excel工作的对象
        try {
            wb = Workbook.createWorkbook(outputStream);
            // 2.创建sheet主页
            sheet = wb.createSheet("第一个sheet页", 0);

            //定义高和宽
            sheet.setColumnView(1, 20);
            sheet.setRowView(1, 200);
            //设置普通格式化
            WritableCellFormat st = new WritableCellFormat();
            //设置单元格风格
            st.setAlignment(Alignment.CENTRE);
            st.setVerticalAlignment(VerticalAlignment.CENTRE);

            // 查询数据库中所有的数据
            List<Customer> list = customerDao.listAllFormalCustomer();
            // 要插入到的Excel表格的行号，默认从0开始
            Label labelSn = new Label(0, 0, "编号");// 表示第
            Label labelNname = new Label(1, 0, "姓名");
            Label labelAge = new Label(2, 0, "年龄");
            Label labelSex = new Label(3, 0, "性别");
            Label labelTel = new Label(4, 0, "电话");
            Label labelEmail = new Label(5, 0, "邮箱");
            Label labelQQ = new Label(6, 0, "QQ");
            Label labelwechat = new Label(7, 0, "微信");
            Label labeljob = new Label(8, 0, "职业");
            Label labelsalaryLevel = new Label(9, 0, "收入水平");
            Label labelSource = new Label(10, 0, "客户来源");
            Label labelInputtime = new Label(11, 0, "创建时间");
            Label labelstatus = new Label(12, 0, "状态");
            Label labelBecomeTime = new Label(13, 0, "转正时间");
            Label labelInputUser = new Label(14, 0, "创建人");
            Label labelInputCharguser = new Label(15, 0, "负责人");

            sheet.addCell(labelSn);
            sheet.addCell(labelNname);
            sheet.addCell(labelAge);
            sheet.addCell(labelSex);
            sheet.addCell(labelTel);
            sheet.addCell(labelEmail);
            sheet.addCell(labelQQ);
            sheet.addCell(labelwechat);
            sheet.addCell(labeljob);
            sheet.addCell(labelsalaryLevel);
            sheet.addCell(labelSource);
            sheet.addCell(labelInputtime);
            sheet.addCell(labelstatus);
            sheet.addCell(labelBecomeTime);
            sheet.addCell(labelInputUser);
            sheet.addCell(labelInputCharguser);
            for (int i = 0; i < list.size(); i++) {
                Label labelSn_i = new Label(0, i + 1, list.get(i).getId()
                    + "", st);
                Label labelName_i = new Label(1, i + 1, list.get(i).getName()
                    + "", st);
                Label labelAge_i = new Label(2, i + 1, list.get(i).getAge()
                    + "", st);
                Label labelSex_i = new Label(3, i + 1, list.get(i).getGender()
                    + "", st);
                Label labelTel_i = new Label(4, i + 1, list.get(i).getTel()
                    + "", st);
                Label labelEmail_i = new Label(5, i + 1, list.get(i).getEmail()
                    + "", st);
                Label labelQQ_i = new Label(6, i + 1, list.get(i).getQq()
                    + "", st);
                Label labelwechat_i = new Label(7, i + 1, list.get(i).getWechat()
                    + "", st);
                Label labeljob_i = new Label(8, i + 1, list.get(i).getJob()
                    + "", st);
                Label labelsalaryLevel_i = new Label(9, i + 1, list.get(i).getSalarylevel()
                    + "", st);
                Label labelSource_i = new Label(10, i + 1, list.get(i).getCustomersource()
                    + "", st);
                String inputtime = sdf.format(list.get(i).getInputtime());
                Label labelInputtime_i = new Label(11, i + 1, inputtime);

                Label labelstatus_i = new Label(12, i + 1, list.get(i).getStatus()
                    + "", st);
                String becometime = sdf.format(list.get(i).getBecometime());
                Label labelBecometime_i = new Label(13, i + 1, becometime);
                Label labelInputUser_i = new Label(14, i + 1, list.get(i).getInputuser().getUsername(), st);
                Label labelInputCharguser_i = new Label(15, i + 1, list.get(i).getInchargeuser().getUsername(), st);
                sheet.addCell(labelSn_i);
                sheet.addCell(labelName_i);
                sheet.addCell(labelAge_i);
                sheet.addCell(labelSex_i);
                sheet.addCell(labelTel_i);
                sheet.addCell(labelEmail_i);
                sheet.addCell(labelQQ_i);
                sheet.addCell(labelwechat_i);
                sheet.addCell(labeljob_i);
                sheet.addCell(labelsalaryLevel_i);
                sheet.addCell(labelSource_i);
                sheet.addCell(labelInputtime_i);
                sheet.addCell(labelstatus_i);
                sheet.addCell(labelBecometime_i);
                sheet.addCell(labelInputUser_i);
                sheet.addCell(labelInputCharguser_i);
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
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
