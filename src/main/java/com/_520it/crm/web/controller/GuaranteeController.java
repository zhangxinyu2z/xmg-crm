package com._520it.crm.web.controller;

import com._520it.crm.domain.*;
import com._520it.crm.req.GuaranteeQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.IGuaranteeItemService;
import com._520it.crm.service.IGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 保修计划
 *
 * @author
 */
@Controller
public class GuaranteeController {
    @Autowired
    private IGuaranteeService guaranteeService;
    @Autowired
    private IGuaranteeItemService guaranteeItemService;
    @Autowired
    private com._520it.crm.service.CustomerService CustomerService;

    @RequestMapping("/guarantee")
    public String index() {
        return "guarantee";

    }


    @RequestMapping("/guarantee_list")
    @ResponseBody
    public PageResult list(GuaranteeQueryObject qo) {
        PageResult result = null;

        try {
            result = guaranteeService.queryByCondition(qo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("/guarantee_update")
    @ResponseBody
    public AjaxResult update(Guarantee guarantee) {
        AjaxResult result = null;
        try {
            int effectCount = guaranteeService.update(guarantee);
            if (effectCount > 0) {
                result = new AjaxResult(true, "更新成功");
            } else {
                result = new AjaxResult("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("更新异常,请联系管理员");
        }
        return result;

    }


    /**
     * 回显明细保修单号
     *
     * @return
     */
    @RequestMapping("/guaranteeitem_queryName")
    @ResponseBody
    public List<Guarantee> queryName() {
        List<Guarantee> result = null;
        result = guaranteeService.selectAll();
        return result;
    }


    @RequestMapping("/guaranteeItem_save")
    @ResponseBody
    public AjaxResult itemSave(GuaranteeItem item) {
        AjaxResult result = null;
        try {
            //查询明细总数
            Long itemCount = guaranteeItemService.selectItems(item.getGuarantee().getId());
            if (itemCount < 3) {
                int effectCount = guaranteeItemService.save(item);
                if (effectCount > 0) {
                    result = new AjaxResult(true, "保存成功");
                } else {
                    result = new AjaxResult("保存失败");
                }

            } else {
                Integer cost = item.getCost();
                if (cost == 0) {
                    result = new AjaxResult("你保修次数已经超过3次,不可以在免费保修");
                } else {
                    int effectCount = guaranteeItemService.save(item);
                    if (effectCount > 0) {
                        result = new AjaxResult(true, "保存成功");
                    } else {
                        result = new AjaxResult("保存失败");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("保存异常,请联系管理员");
        }

        return result;
    }

    /**
     * 保修单对应的明细
     *
     * @param id
     * @return
     */
    @RequestMapping("/guaranteeItem_list")
    @ResponseBody
    public List<GuaranteeItem> itemList(Long id) {
        List<GuaranteeItem> result = null;
        result = guaranteeItemService.selectAllItem(id);
        return result;

    }


    /**
     * 修改保修单明细
     *
     * @param item
     * @return
     */
    @RequestMapping("/guaranteeItem_update")
    @ResponseBody
    public AjaxResult itemUpdate(GuaranteeItem item) {
        AjaxResult result = null;
        try {
            GuaranteeItem item2 = guaranteeItemService.selectByPrimaryKey(item.getId());
            Integer cost2 = item2.getCost();
            if (cost2 == 0) {
                int effectCount = guaranteeItemService.update(item);
                if (effectCount > 0) {
                    result = new AjaxResult(true, "更新成功");
                } else {
                    result = new AjaxResult("更新失败");
                }
            } else {
                Long itemCount = guaranteeItemService.selectItems(item.getGuarantee().getId());
                if (itemCount < 3) {
                    int effectCount = guaranteeItemService.update(item);
                    if (effectCount > 0) {
                        result = new AjaxResult(true, "更新成功");
                    } else {
                        result = new AjaxResult("更新失败");
                    }

                } else {
                    Integer cost = item.getCost();
                    if (cost == 0) {
                        result = new AjaxResult("当前用户的产品保修次数已经超过3次,不可以在免费保修");
                    } else {
                        int effectCount = guaranteeItemService.update(item);
                        if (effectCount > 0) {
                            result = new AjaxResult(true, "更新成功");
                        } else {
                            result = new AjaxResult("更新失败");
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("更新异常,亲联系管理员");
        }

        return result;
    }

    /**
     * 保修产品明细删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/guaranteeItem_delete")
    @ResponseBody
    public AjaxResult itemDelete(Long id) {
        AjaxResult result = null;
        try {
            int effectCount = guaranteeItemService.delete(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("删除异常,亲联系管理员");
        }

        return result;
    }

    /**
     * 保修单删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/guarantee_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int effectCount = guaranteeService.delete(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("删除异常,亲联系管理员");
        }

        return result;
    }


    /**
     * 保修单延保
     */
    @RequestMapping("/guarantee_keep")
    @ResponseBody
    public AjaxResult keep(Long id) {
        AjaxResult result = null;
        //查询明细总数
        try {
            int effectCount = guaranteeService.updateTime(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "延保成功");

            } else {
                result = new AjaxResult("延保失败");

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("延保出现异常,请联系管理员");
        }
        return result;
    }


    /**
     * 回显保修客户信息
     *
     * @return
     */
    @RequestMapping("/customer_queryByName")
    @ResponseBody
    public List<Customer> listAll() {
        List<Customer> result = null;
        result = CustomerService.selectAll();
        return result;
    }
}
