package com._520it.crm.web.controller;

import com._520it.crm.annotation.RequiredPermission;
import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Task;
import com._520it.crm.req.TaskQueryObject;
import com._520it.crm.resp.AjaxResult;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.DepartmentService;
import com._520it.crm.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 当日任务模块
 *
 * @author
 */
@Controller
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/task")
    public String index() {
        return "task";
    }

    /**
     * TODO:使用portal模式，在日历上添加日程安排
     * @return
     */
    @RequestMapping("/taskCalendar")
    public String calendarIndex() {
        return "taskCalendar";
    }

    @RequestMapping("/task_list")
    @ResponseBody
    public PageResult list(TaskQueryObject qo, HttpSession session) {
        PageResult result = null;
        try {
            result = taskService.selectByCondition(qo);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*@RequestMapping("/task_listAll")
    @ResponseBody
    public List<Task> listAll(TaskQueryObject qo) {
        PageResult result = null;
        try {
            result = taskService.selectByCondition(qo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getRows();
    }*/


    @RequestMapping("/task_save")
    @ResponseBody
    @RequiredPermission("今日计划分配")
    public AjaxResult save(Task task) {
        System.out.println("task.getTitle()" + task.getTitle());
        System.out.println(task.getRemark());
        System.out.println(task.getMinhandledescribe());
        System.out.println("task.getMintaskdescribe()" + task.getMintaskdescribe());
        AjaxResult result = null;
        try {
            task.setStatus(0);
            task.setStart(new Date());
            this.taskService.save(task);
            result = new AjaxResult(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }
        return result;
    }

    @ModelAttribute
    public void before(Long id, Model model) {
        if (id != null) {
            Task todayTask = (Task) this.taskService.get(id);
            todayTask.setHandleuser((Employee) null);
            model.addAttribute(todayTask);
        }
    }

    @RequestMapping("/task_update")
    @ResponseBody
    @RequiredPermission("分配任务编辑")
    public AjaxResult update(Task todayTask) {
        AjaxResult result = null;
        try {
            this.taskService.update(todayTask);
            result = new AjaxResult(true, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }
        return result;
    }


    /*    @ModelAttribute
    public void beforeupdate(Long aid, Model model) {
        if (aid != null) {
            Task task = (Task) this.taskService.get(aid);
            task.setRemark((String) null);
            model.addAttribute(task);
        }
    }*/


    @RequestMapping("/task_addDescribe")
    @ResponseBody
    public AjaxResult addDescribe(Task task) {
        AjaxResult result = null;
        try {
            this.taskService.update(task);
            result = new AjaxResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }
        return result;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping("/task_complete")
    @ResponseBody
    public AjaxResult complete(Long cId) {
        AjaxResult result = null;
        try {
            Task e = (Task) this.taskService.get(cId);
            Date date = e.getStart();
            Long time = Long.valueOf(System.currentTimeMillis() - date.getTime());
            long onedayTime = 86400000L;
            long day = time.longValue() / onedayTime;
            if (day >= 7L) {
                result = new AjaxResult("这条记录的状态修改时间超过了限制!请在7天内操作!");
            } else if (e.getStatus() == 0) {
                e.setStatus(1);
                this.taskService.update(e);
                result = new AjaxResult(true, "设置成功");
            } else {
                result = new AjaxResult("这条记录还没有被完成哦!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }

        return result;
    }

    @RequestMapping("/task_lose")
    @ResponseBody
    public AjaxResult lose(Long lId) {
        AjaxResult result = null;
        try {
            Task e = (Task) this.taskService.get(lId);
            Date date = e.getStart();
            Long time = Long.valueOf(System.currentTimeMillis() - date.getTime());
            long onedayTime = 86400000L;
            long day = time.longValue() / onedayTime;
            if (day >= 7L) {
                result = new AjaxResult("这条记录的状态修改时间超过了限制!请在7天内操作!");
            } else if (e.getStatus() == 0) {
                e.setStatus(2);
                this.taskService.update(e);
                result = new AjaxResult(true, "设置成功");
            } else {
                result = new AjaxResult("这条记录还没有被完成哦!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }
        return result;
    }

    @RequestMapping("/task_complateAll")
    @ResponseBody
    public AjaxResult complateAll(Long[] var1) {
        throw new Error("Unresolved compilation problems: \n\tType mismatch: cannot convert from ArrayList<?> to " +
                "List<String>\n\tCannot instantiate the type ArrayList<?>\n\tSyntax error on token \"<\", ? expected " +
                "after this token\n");
    }

    @RequestMapping("/task_loseAll")
    @ResponseBody
    public AjaxResult loseAll(Long[] var1) {
        throw new Error("Unresolved compilation problems: \n\tType mismatch: cannot convert from ArrayList<?> to " +
                "List<String>\n\tCannot instantiate the type ArrayList<?>\n\tSyntax error on token \"<\", ? expected " +
                "after this token\n");
    }

    @RequestMapping("/task_del")
    @ResponseBody
    public AjaxResult delete(Long id) {
        AjaxResult result = null;
        try {
            int effectCount = taskService.delete(id);
            if (effectCount > 0) {
                result = new AjaxResult(true, "删除成功");
            } else {
                result = new AjaxResult("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("删除发生异常了,请联系管理员!");
        }
        return result;
    }

    @RequestMapping("/task_delAll")
    @ResponseBody
    public AjaxResult deleteAll(Long[] ids) {

        System.out.println(Arrays.toString(ids));
        AjaxResult result = null;
        try {
            if (ids != null) {
                for (int e = 0; e < ids.length; ++e) {
                    //Task todayTask = (Task)this.taskService.get(id[e]);
                    taskService.delete(ids[e]);
                }
                result = new AjaxResult(true, "删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult("发生异常了,请联系管理员!");
        }
        return result;
    }

}
