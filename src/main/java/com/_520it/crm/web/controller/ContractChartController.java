package com._520it.crm.web.controller;

import com._520it.crm.domain.ContractChartVO;
import com._520it.crm.req.ContractChartQueryObject;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IContractChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 年度报表
 *
 * @author
 */
@Controller
public class ContractChartController {
    @Autowired
    IContractChartService contractService;

    @RequestMapping("/contractChart_pie")
    @ResponseBody
    public List listYear1(ContractChartQueryObject qo) {
        qo.setStatus(3);
        Map map = new HashMap();
        List list = new ArrayList();
        List<Object[]> datas = new ArrayList<>();
        ContractChartVO vo = new ContractChartVO();
        List<ContractChartVO> listVO = contractService.selectPie(qo);
        for (ContractChartVO contractVO : listVO) {
            datas.add(new Object[]{contractVO.getTime(), contractVO.getAmountCustomer()});
        }
        list.add(datas);
        return list;
    }


    @RequestMapping("/groupContract")
    @ResponseBody
    public List listYear(ContractChartQueryObject qo) {
        qo.setStatus(3);
        Map map = new HashMap();
        List list = new ArrayList();
        List<String> time = new ArrayList();
        List<Long> amountCustomer = new ArrayList();
        time = contractService.selectTimeYear(qo);
        amountCustomer = contractService.selectAmountYear(qo);
        map.put("amountCustomers", amountCustomer);
        map.put("times", time);
        list.add(map);
        return list;
    }


    @RequestMapping("/contractChart_list")
    @ResponseBody
    public PageResult list(ContractChartQueryObject qo) {
        qo.setStatus(3);
        PageResult result = null;
        try {
            result = contractService.selectByCondition(qo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/contractmm")
    public String query() {
        return "contractChart";
    }

    @RequestMapping("/contractCloumn_pictrue")
    public String pictrue33(ContractChartQueryObject qo, HttpServletRequest request) {
        getTime(qo, request);
        return "contractChartCloumn";
    }

    @RequestMapping("/contractLine_pictrue")
    public String pictrue22(ContractChartQueryObject qo, HttpServletRequest request) {
        getTime(qo, request);
        return "contractChartLine";
    }

    @RequestMapping("/contractPie_pictrue")
    public String pictrue11(ContractChartQueryObject qo, HttpServletRequest request) {
        getTime(qo, request);
        return "contractChartPic";
    }

    public static void getTime(ContractChartQueryObject qo, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");

        if (qo.getBeginDate() != null) {

            request.setAttribute("beginDate", sdf.format(qo.getBeginDate()));
        }
        if (qo.getEndDate() != null) {

            request.setAttribute("endDate", sdf.format(qo.getEndDate()));
        }
        request.setAttribute("typee", qo.getTypee());
    }


}
