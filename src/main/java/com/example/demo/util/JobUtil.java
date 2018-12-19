package com.example.demo.util;

import org.quartz.*;

/**
 * Created by pc on 2018/12/19 10:11
 **/
public class JobUtil {

    public static void addJob(Scheduler scheduler,String jobClassName, String jobGroupName, String cronExpression,Integer strategy)throws Exception{
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                .withIdentity(jobClassName, jobGroupName)
                .usingJobData("strategy",strategy)
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * @description 暂停任务
     * @author hezhengzhi
     * @date 2018/12/19 10:22
    **/
    public static void jobPause(Scheduler scheduler,String jobClassName, String jobGroupName) throws Exception
    {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * @description 任务重新启动
     * @author hezhengzhi
     * @date 2018/12/19 10:23
    **/
    public static void jobResume(Scheduler scheduler,String jobClassName, String jobGroupName) throws Exception
    {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * @description 刷新任务
     * @author hezhengzhi
     * @date 2018/12/19 10:25
    **/
    public static void jobReschedule(Scheduler scheduler,String jobClassName, String jobGroupName, String cronExpression) throws Exception
    {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
            throw new Exception("更新定时任务失败");
        }
    }

    /**
     * @description 删除任务
     * @author hezhengzhi
     * @date 2018/12/19 10:25
    **/
    public static void jobDelete(Scheduler scheduler, String jobClassName, String jobGroupName) throws Exception
    {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    public static Job getClass(String classname) throws Exception
    {
        Class<?> class1 = Class.forName(classname);
        return (Job)class1.newInstance();
    }

}
