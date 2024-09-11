package com._520it.crm.web.controller;

import com._520it.crm.annotation.RequiredPermission;
import com._520it.crm.domain.*;
import com._520it.crm.req.ContractQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IContractService;
import com._520it.crm.service.IGuaranteeService;
import com._520it.crm.utils.UserContext;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Calendar;
import java.util.*;

/**
 * 客户签订（定金）订单，如果确定购买公司产品，需要签订购买合同.
 *
 * @author zxy
 */
@Controller
public class ContractController extends BaseController {

    @Autowired
    private IContractService contractService;

    @Autowired
    private IGuaranteeService guaranteeService;

    @RequestMapping("/contract")
    public String index() {
        return "contract";
    }

    @RequestMapping("/contract_list")
    @ResponseBody
    public PageResult list(ContractQueryObject qo) {
        PageResult result = null;
        result = contractService.queryByCondition(qo);
        return result;
    }


    @RequestMapping("/contract_save")
    @ResponseBody
    public AjaxResult save(Contract contract, MultipartFile pic, HttpServletRequest request) {
        System.out.println("===============");
        System.out.println(pic.getSize());
        System.out.println(pic.getName());
        System.out.println(pic.getContentType());
        System.out.println(pic.getOriginalFilename());
        System.out.println("===============");

        String filePath = mulipartFileUpload(pic, request);
        AjaxResult result = null;
        try {
            //保存路径至数据库
            contract.setSn(UUID.randomUUID().toString());
            contract.setFile(filePath);
            contract.setStatus(0);
            int effectCount = contractService.insert(contract);
            if (effectCount > 0) {
                return new AjaxResult(true, "保存成功");
            } else {
                return new AjaxResult("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("保存出现异常,请联系管理员");
        }
    }


    /**
     * 图片上传
     *
     * @param pic
     * @param request
     * @return
     */
    private String mulipartFileUpload(MultipartFile pic, HttpServletRequest request) {
        String realPath1 = request.getSession().getServletContext().getRealPath("/upload");
        String realPath2 = realPath1.replace("\\", "/") + "/";
        String realName1 = UUID.randomUUID() + "." + pic.getContentType().split("/")[1];
        String realName2 = "/upload/" + realName1;


        //springMVC图片上传
        if (pic != null) {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = pic.getInputStream();
                outputStream = new FileOutputStream(new File(realPath2 + realName1));
                IOUtils.copy(inputStream, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return realName2;
    }

    /**
     * 更新前预处理(避免file和sn丢失.)
     *
     * @param id
     * @param model
     */
    @ModelAttribute
    public void before(Long id, Model model) {
        if (id != null) {
            Contract contract = contractService.selectByPrimaryKey(id);
            model.addAttribute(contract);
        }
    }

    @RequestMapping("/contract_update")
    @ResponseBody
    public AjaxResult update(Contract contract, HttpSession session, MultipartFile pic, HttpServletRequest request) {
        String filePath = mulipartFileUpload(pic, request);
        AjaxResult result = null;
        try {
            Employee employee = (Employee) session.getAttribute(UserContext.USER_IN_SESSION);
            contract.setModifyuser(employee);
            contract.setModifytime(new Date());
            contract.setFile(filePath);
            int effectRows = contractService.updateByPrimaryKey(contract);
            if (effectRows > 0) {
                result = new AjaxResult(true, "更改成功");
            } else {
                result = new AjaxResult(false, "更改失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "更改异常");
        }
        return result;
    }

    @RequestMapping("/contract_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int row = contractService.deleteByPrimaryKey(id);
            if (row > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult(false, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "删除异常");
        }
        return result;
    }


    /**
     * 合同审核
     *
     * @param id
     * @return
     */
    @RequiredPermission("合同审核")
    @RequestMapping("/contract_checked")
    @ResponseBody
    public AjaxResult checked(Long id) {
        AjaxResult result = null;
        try {
            int row = contractService.checked(id);
            if (row > 0) {
                result = new AjaxResult(true, "审核成功");
            } else {
                result = new AjaxResult(false, "审核失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "审核异常");
        }
        return result;
    }

    @RequiredPermission("部门合同审核")
    public void departmentChecked() {

    }

    @RequiredPermission("财务合同审核")
    public void financeChecked() {

    }


    /**
     * 合同拒绝审核
     *
     * @param id
     * @return
     */
    @RequestMapping("/contract_noChecked")
    @ResponseBody
    public AjaxResult noChecked(Long id) {
        AjaxResult result = null;
        try {
            int row = contractService.noChecked(id);
            if (row > 0) {
                result = new AjaxResult(true, "拒绝审核成功");
            } else {
                result = new AjaxResult(false, "拒绝审核失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "拒绝审核异常");
        }
        return result;
    }

    /**
     * 生成维修单
     *
     * @param id
     * @param guarantee
     * @return
     */
    @RequestMapping("/contract_doGuarantee")
    @ResponseBody
    public AjaxResult doContract(Long id, Guarantee guarantee) {
        AjaxResult result = null;
        Contract contract = contractService.selectByPrimaryKey(id);
        Customer customer = contract.getCustomer();

        guarantee.setSn(UUID.randomUUID().toString());
        guarantee.setCustomer(customer);
        //设置保修时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        Date time = calendar.getTime();
        guarantee.setDuetime(time);
        try {
            int row = guaranteeService.save(guarantee);
            if (row > 0) {
                result = new AjaxResult(true, "维修单生成成功");
            } else {
                result = new AjaxResult(false, "维修单生成失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false, "维修单生成出现异常");
        }
        return result;
    }
}
