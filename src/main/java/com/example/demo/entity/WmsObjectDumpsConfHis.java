package com.example.demo.entity;

/**
 * Created by pc on 2018/12/19 9:39
 **/
public class WmsObjectDumpsConfHis {
    private Integer id;
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private Integer Strategy;
    private String Cron;
    private Integer operatorId;
    private String operatorTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public Integer getStrategy() {
        return Strategy;
    }

    public void setStrategy(Integer strategy) {
        Strategy = strategy;
    }

    public String getCron() {
        return Cron;
    }

    public void setCron(String cron) {
        Cron = cron;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(String operatorTime) {
        this.operatorTime = operatorTime;
    }
}
