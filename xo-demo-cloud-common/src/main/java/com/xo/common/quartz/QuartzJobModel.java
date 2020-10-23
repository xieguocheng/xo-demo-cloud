package com.xo.common.quartz;

import lombok.Data;
import org.quartz.Job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xo
 * @DATE: 2020/9/28
 * @Description: QuartzJob
 **/
@Data
public class QuartzJobModel {

    private Class<? extends Job> jobClass = null;

    private Map<String, Object> jobContext = new HashMap<>();

    private String groupName = null;

    private String jobName = null;

    private Date jobStartAtTime = null;

    private Date jobEndAtTime = null;

    private Integer repeatCount = null;

    private Integer secondlyInterval = null;

    private Integer hourlyInterval = null;

    private String cronExpression = null;

    private Integer jobPriority = null;

    private Boolean isRecoverable = null;

    private Boolean isReplaceable = null;

}
