package com.sboot.study.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * create by faraway on 2018/12/27
 * description: 定时任务
 * 非@Controller @Service就用该通用注解
 */

@Component
public class SchedulerTest {

    private static final Logger log = LoggerFactory.getLogger(SchedulerTest.class);

    /**@Scheduled(cron = "0/10 * * * * ?")*/
    public void sysout() {
        System.out.println("0.0.....");
    }


}
