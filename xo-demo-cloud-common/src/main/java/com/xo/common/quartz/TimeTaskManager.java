package com.xo.common.quartz;

import com.xo.common.utils.TimeUtils;
import com.xo.common.utils.UtilFuns;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

/**
 * @author: xo
 * @DATE: 2020/9/28
 * @Description: TimeTaskManager
 **/
public class TimeTaskManager {

    private static Log LOG = LogFactory.getLog(TimeTaskManager.class);

    public static final String SYSTEM_GROUP = "SYSTEM_GROUP";

    private static TimeTaskManager timerManager = null;

    private Scheduler scheduler = null;

    private TimeTaskManager() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            LOG.fatal("init TimeTaskManager error!", e);
            System.exit(-1);
        }
    }

    public static TimeTaskManager getInstance() {
        if (timerManager == null) {
            timerManager = new TimeTaskManager();
        }
        return timerManager;
    }

    public static QuartzJobModel createQuartzJob() {
        return new QuartzJobModel();
    }

    public void init() {
        try {
            scheduler.start();
            LOG.info("QUARTZ component init success!");
        } catch (SchedulerException e) {
            LOG.fatal("start scheduler error!", e);
            System.exit(-1);
        }
    }

    /**
     * 执行普通JOB
     * @param quartzJobModel
     * @throws SystemException
     */
    @Deprecated
    public void startSimpleJob(QuartzJobModel quartzJobModel) throws SystemException {

        String jobName = quartzJobModel.getJobName();
        String groupName = quartzJobModel.getGroupName();
        Boolean isReplaceable = quartzJobModel.getIsReplaceable() == null ? false : quartzJobModel.getIsReplaceable();
        Boolean isRecoverable = quartzJobModel.getIsRecoverable() == null ? false : quartzJobModel.getIsRecoverable();
        Class<? extends Job> jobClass = quartzJobModel.getJobClass();
        if (jobClass == null) {
            throw new SystemException("startSimpleJob error for jobClass is null!");
        }
        if (isReplaceable && this.isJobExecuting(jobName, groupName, false)) {
            this.terminateJob(jobName, groupName);
        }
        JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, groupName).requestRecovery(isRecoverable).build();
        Map<String, Object> jobConext = quartzJobModel.getJobContext();
        for (Map.Entry<String, Object> entry : jobConext.entrySet()) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }

        SimpleScheduleBuilder scheduleBuilder = null;
        Integer repeatCount = quartzJobModel.getRepeatCount();
        Integer secondlyInterval = quartzJobModel.getSecondlyInterval();
        Integer hourlyInterval = quartzJobModel.getHourlyInterval();
        Date jobStartAtTime = quartzJobModel.getJobStartAtTime();
        Date jobEndAtTime = quartzJobModel.getJobEndAtTime();
        Integer jobPriority = quartzJobModel.getJobPriority();

        if (quartzJobModel.getRepeatCount() == null) {
            if (secondlyInterval != null) {
                scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(secondlyInterval);
            } else if (hourlyInterval != null) {
                scheduleBuilder = SimpleScheduleBuilder.repeatHourlyForever(hourlyInterval);
            } else {
                scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            }
        } else if (secondlyInterval != null) {
            scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(repeatCount, secondlyInterval);
        } else {
            throw new SystemException("invalid param of quartzJob.");
        }

        TriggerBuilder<SimpleTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder);
        triggerBuilder.withIdentity(jobName, groupName);
        if (jobStartAtTime != null) {
            triggerBuilder.startAt(jobStartAtTime);
        }

        if (jobEndAtTime != null) {
            triggerBuilder.endAt(jobEndAtTime);
        }

        if (jobPriority != null) {
            triggerBuilder.withPriority(jobPriority);
        }

        SimpleTrigger trigger = triggerBuilder.build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 执行一个cronJob
     * @param quartzJobModel
     * @throws SystemException
     */
    public void startCronTask(QuartzJobModel quartzJobModel) throws SystemException {

        String jobName = quartzJobModel.getJobName();
        String groupName = quartzJobModel.getGroupName();
        Integer jobPriority = quartzJobModel.getJobPriority();
        Boolean isReplaceable = quartzJobModel.getIsReplaceable() == null ? false : quartzJobModel.getIsReplaceable();
        Boolean isRecoverable = quartzJobModel.getIsRecoverable() == null ? false : quartzJobModel.getIsRecoverable();
        Class<? extends Job> jobClass = quartzJobModel.getJobClass();
        if (jobClass == null) {
            throw new SystemException("startCronTask error for jobClass is null!");
        }

        if (isReplaceable && this.isJobExecuting(jobName, groupName, false)) {
            this.terminateJob(jobName, groupName);
        }
        JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, groupName).requestRecovery(isRecoverable).build();
        Map<String, Object> jobConext = quartzJobModel.getJobContext();
        for (Map.Entry<String, Object> entry : jobConext.entrySet()) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }

        CronScheduleBuilder cronScheduleBuilder = null;
        String cronExpression = quartzJobModel.getCronExpression();
        Date jobStartAtTime = quartzJobModel.getJobStartAtTime();
        Date jobEndAtTime = quartzJobModel.getJobEndAtTime();
        if (!UtilFuns.isEmpty(cronExpression)) {
            cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        } else {
            throw new SystemException("invalid param of quartzJob.");
        }

        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder);
        triggerBuilder.withIdentity(jobName, groupName);

        if (jobStartAtTime != null) {
            triggerBuilder.startAt(jobStartAtTime);
        }

        if (jobEndAtTime != null) {
            triggerBuilder.endAt(jobEndAtTime);
        }

        if (jobPriority != null) {
            triggerBuilder.withPriority(quartzJobModel.getJobPriority());
        }
        CronTrigger trigger = triggerBuilder.build();

        Date ft = null;
        try {
            ft = scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
        LOG.info(jobDetail.getKey() + " will run at: " + TimeUtils.format(ft,"yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * @param jobName
     * @param groupName
     * @param isFuzzySearch 是否根据jobName模糊搜索
     * @return
     * @throws SystemException
     */
    public Boolean isJobExecuting(String jobName, String groupName, Boolean isFuzzySearch) throws SystemException {
        Boolean res = false;
        try {
            List<JobExecutionContext> jobExecutionContextList = this.scheduler.getCurrentlyExecutingJobs();
            JobKey jobKey = null;
            String tmpJobName = null;
            String tmpJobGroup = null;
            for (JobExecutionContext jobExecutionContext : jobExecutionContextList) {
                jobKey = jobExecutionContext.getJobDetail().getKey();
                tmpJobName = jobKey.getName();
                tmpJobGroup = jobKey.getGroup();
                if (tmpJobGroup.equals(groupName)) {
                    if (isFuzzySearch != null && isFuzzySearch) {
                        if (tmpJobName.contains(jobName)) {
                            res = true;
                            break;
                        }
                    } else {
                        if (tmpJobName.equals(jobName)) {
                            res = true;
                            break;
                        }
                    }
                }
            }
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
        return res;
    }

    /**
     * 终止JOB
     *
     * @param jobName
     * @param groupName
     * @throws SystemException
     */
    public void terminateJob(String jobName, String groupName) throws SystemException {
        try {
            this.scheduler.deleteJob(new JobKey(jobName, groupName));
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 暂停Job
     *
     * @param jobName
     * @param groupName
     * @throws SystemException
     */
    public void pauseJob(String jobName, String groupName) throws SystemException {
        try {
            this.scheduler.pauseJob(new JobKey(jobName, groupName));
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 恢复暂停的Job
     *
     * @param jobName
     * @param groupName
     * @throws SystemException
     */
    public void resumeJob(String jobName, String groupName) throws SystemException {
        try {
            this.scheduler.resumeJob(new JobKey(jobName, groupName));
        } catch (SchedulerException e) {
            throw new SystemException(e);
        }
    }
}
