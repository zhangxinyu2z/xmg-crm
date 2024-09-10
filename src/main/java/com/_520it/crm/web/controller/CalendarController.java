package com._520it.crm.web.controller;

import com._520it.crm.domain.Calendar;
import com._520it.crm.page.AjaxResult;
import com._520it.crm.query.CalendarQueryObject;
import com._520it.crm.service.ICalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private ICalendarService calendarService;


    @RequestMapping("/calendar")
    public String index(){
        return "calendar";
    }

    @RequestMapping("/calendar_list")
    @ResponseBody
    public List<Calendar> list(CalendarQueryObject qo) {
        List<Calendar> result = null;
        result = calendarService.selectAll();
        return result;
    }

    @RequestMapping("/calendar_save")
    @ResponseBody
    public AjaxResult save(Calendar calendar) {
        AjaxResult result = null;
        try {
            int effectRows = calendarService.insert(calendar);
            if (effectRows > 0) {
                result = new AjaxResult(true, "保存成功");
            } else {
                result = new AjaxResult(false, "保存失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "保存异常");
        }
        return result;
    }

    @RequestMapping("/calendar_update")
    @ResponseBody
    public AjaxResult update(Calendar calendar) {
        AjaxResult result = null;
        try {
            int effectRows = calendarService.updateByPrimaryKey(calendar);
            if (effectRows > 0) {
                result = new AjaxResult(true, "修改成功");
            } else {
                result = new AjaxResult(false, "修改失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "修改异常");
        }
        return result;
    }

    //日历拖拽
    @RequestMapping("/calendar_updateDayDelta")
    @ResponseBody
    public AjaxResult updateDayDelta(Long id,String start,String end) {
        int startInt = Integer.parseInt(start);
        int endInt = Integer.parseInt(end);
        AjaxResult result = null;
        try {
            int effectRows = calendarService.updateDayDelta(id,startInt,endInt);
            if (effectRows > 0) {
                result = new AjaxResult(true, "拖拽成功");
            } else {
                result = new AjaxResult(false, "拖拽失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result = new AjaxResult(false, "拖拽异常");
        }
        return result;
    }



    @RequestMapping("/calendar_delete")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {

            int row = calendarService.deleteByPrimaryKey(id);
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
}
