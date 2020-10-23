package com.xo.common.executor.executor02;

import lombok.Data;
import org.HdrHistogram.Recorder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author: xo
 * @DATE: 2020/9/7
 * @Description: BbtCallable
 **/
@Data
public abstract class BaseCallable<T extends Object> implements Callable<T> {

    protected List<?> targetList;

    protected Map<String, Object> map;

    protected Recorder recorder;

}

