package com.imooc.o2o.config.quartz;

import com.imooc.o2o.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Autowired
    private ProductSellDailyService productSellDailyService;
    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailFactory;
    @Autowired
    private CronTriggerFactoryBean productSellDailyTriggerFactory;
    /**
     * 創建jobDetailFactory
     * @Author: Zac5566
     * @Date: 2020/7/5
     * @return: org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
     */
    @Bean(name = "jobDetailFactory")
    public MethodInvokingJobDetailFactoryBean createJobDetail(){
        //jobDetailFactory對象主要用來製作jobDetail，即製作一個任務
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        //設置 jobDetail的名字
        jobDetailFactoryBean.setName("product_sell_daily_job");
        //設置 jobDetail的組名
        jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
        //對於相同的jobDetail，當指定多個trigger時，可能會在第一個job完成前，第二個job就開始了
        //指定concurrrent為false，多個job就不會併發執行
        jobDetailFactoryBean.setConcurrent(false);
        //指定執行任務的類
        jobDetailFactoryBean.setTargetObject(productSellDailyService);
        //指定運行任務的方法
        jobDetailFactoryBean.setTargetMethod("dailyCalculate");
        return jobDetailFactoryBean;
    }

    /**
     * 創建CronTriggerFactory
     * @Author: Zac5566
     * @Date: 2020/7/5
     * @return: org.springframework.scheduling.quartz.CronTriggerFactoryBean
     */
    @Bean(name = "productSellDailyTriggerFactory")
    public CronTriggerFactoryBean createProductSellDailyTrigger(){
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        //設置名字
        triggerFactory.setName("prduct_sell_daily_trigger");
        //設置組名
        triggerFactory.setGroup("job_product_sell_daily_group");
        //綁定jobDetail
        triggerFactory.setJobDetail(jobDetailFactory.getObject());
        //設定cron表達式
        triggerFactory.setCronExpression("0 0 11 * * ? *");
        return triggerFactory;
    }
    /**
     * 創建調度工廠
     * @Author: Zac5566
     * @Date: 2020/7/5
     * @return: org.springframework.scheduling.quartz.SchedulerFactoryBean
     */
    @Bean(name = "scheduleFactory")
    public SchedulerFactoryBean createScheduleFactory(){
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTriggers(productSellDailyTriggerFactory.getObject());
        return schedulerFactory;
    }
}
