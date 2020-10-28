package com.xo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author: xo
 * @DATE: 2020/9/8
 * @Description: ThreadPoolService
 **/
@Slf4j
@Component
public class ThreadPoolService {

    @Async
    public void common() {
       log.info("common!!!!!");
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
        }
        log.info("common over!!!!");
    }


    @Async
    public ListenableFuture<String> async1() {
        log.info("async1 begin!!!!!");
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
        }
        log.info("async1 over!!!!");
        return new AsyncResult<>("async1");
    }


    @Async
    public ListenableFuture<String> async2() {
        log.info("async2 begin!!!!!");
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
        }
        log.info("async2 over!!!!");
        return new AsyncResult<>("async2");
    }

}
