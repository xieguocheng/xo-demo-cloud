package com.xo.common.quartz;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: xo
 * @DATE: 2020/9/28
 * @Description: SystemQuartzLoad
 **/
@Slf4j
public class SystemQuartzLoad {


    private static SystemQuartzLoad instance;

    private SystemQuartzLoad() {}

    public static SystemQuartzLoad getInstance() {
        if (instance == null) {
            instance = new SystemQuartzLoad();
        }
        return instance;
    }

    public void start() {
        TimeTaskManager.getInstance().init();
    }





}
