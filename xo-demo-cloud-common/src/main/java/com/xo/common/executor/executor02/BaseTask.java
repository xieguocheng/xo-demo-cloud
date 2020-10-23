package com.xo.common.executor.executor02;

import lombok.Data;

import java.util.Map;

/**
 * @author: xo
 * @DATE: 2020/9/16
 * @Description: BbtTask
 **/
@Data
public abstract class BaseTask implements Runnable{

    /** 上下文参数 */
    private Map<String,Object> context = null;

}