package com.xo.common.quartz;

import org.quartz.Job;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xo
 * @DATE: 2020/9/28
 * @Description: QuartzUtil
 **/
public class QuartzUtil {

	private static AtomicInteger atomic = new AtomicInteger(0);

	/**
     * 根据cron开启一个定时任务,不带参数
	 * @param clazz
     * @param cronExpression
     * @throws SystemException
	 */
	public static void startCommonQuartzCronJob(Class<? extends Job> clazz,String cronExpression) throws SystemException {
		QuartzJobModel quartzJobModel = new QuartzJobModel();
		String jobName = clazz.getName() + "_" +getUniqueKey();
		quartzJobModel.setJobClass(clazz);
		quartzJobModel.setJobName(jobName);
		quartzJobModel.setCronExpression(cronExpression);
		quartzJobModel.setGroupName(clazz.getName());
		quartzJobModel.setIsReplaceable(true);
		quartzJobModel.setIsRecoverable(true);
		quartzJobModel.getJobContext().put("paramMap", null);
		TimeTaskManager.getInstance().startCronTask(quartzJobModel);
	}


	/**
	 * 根据cron开启一个定时任务，带参数paramMap
	 * @param clazz
	 * @param paramMap
	 * @param cronExpression
	 * @throws SystemException
	 */
	public static void startQuartzCronJob(Class<? extends Job> clazz, Map<String,Object> paramMap, String cronExpression) throws SystemException {
		QuartzJobModel quartzJobModel = new QuartzJobModel();
		String jobName = clazz.getName() + "_" + getUniqueKey();
		quartzJobModel.setJobClass(clazz);
		quartzJobModel.setJobName(jobName);
		quartzJobModel.setCronExpression(cronExpression);
		if(paramMap != null && paramMap.get("nick") != null) {
			quartzJobModel.setGroupName(paramMap.get("nick").toString());
		} else {
			quartzJobModel.setGroupName(clazz.getName());
		}
		quartzJobModel.setIsReplaceable(true);
		quartzJobModel.setIsRecoverable(true);
		quartzJobModel.getJobContext().put("paramMap", paramMap);
		TimeTaskManager.getInstance().startCronTask(quartzJobModel);
	}

	/**
	 * 开启一个普通的定时任务
	 * @param clazz
	 * @param paramMap
	 * @throws SystemException
	 */
	public static void startQuartzThreadJob(Class<? extends Job> clazz, Map<String,Object> paramMap) throws SystemException {
		QuartzJobModel quartzJobModel = new QuartzJobModel();
		String jobName = clazz.getName() + "_" + getUniqueKey();
		quartzJobModel.setJobClass(clazz);
		quartzJobModel.setJobName(jobName);
		if(paramMap != null && paramMap.get("nick") != null) {
			quartzJobModel.setGroupName(paramMap.get("nick").toString());
		} else {
			quartzJobModel.setGroupName(clazz.getName());
		}
		quartzJobModel.setIsReplaceable(true);
		quartzJobModel.setIsRecoverable(true);
		quartzJobModel.getJobContext().put("paramMap", paramMap);
		TimeTaskManager.getInstance().startSimpleJob(quartzJobModel);
	}



	/**
     * 定时任务是否在执行中
	 * @param nick
     * @param clazz
     * @return
     * @throws SystemException
	 */
	public static Boolean isJobExecuting(String nick, Class<? extends Job> clazz) throws SystemException {
		return TimeTaskManager.getInstance().isJobExecuting(clazz.getName(), nick, Boolean.TRUE);
	}

	/**
	 * 获取key
	 * @return
	 */
	public static long getUniqueKey() {
		if (atomic.get() > 9999) {
			atomic.set(1);
		}
		long time = System.currentTimeMillis();
		StringBuilder value = new StringBuilder().append(UUID.randomUUID()).append(time);
		long key = Math.abs(value.toString().hashCode());
		value.setLength(0);
		value.append(key).append(atomic.incrementAndGet());
		return Long.parseLong(value.toString());
	}

}
