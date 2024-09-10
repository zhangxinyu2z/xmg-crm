package com._520it.crm.utils;

import com._520it.crm.service.IClewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob {

    @Autowired
    private IClewService clewService;


    /**
     * 定期的去同步数据库中clew表的数据到lucene仓库中
     */
    public void work() {
        System.out.println("定时执行任务调度");
        clewService.luceneWriteIndex();
        System.out.println("执行完毕");
    }
}
