package com.xo.service.impl;

import com.xo.common.annotation.SendMailAnnotation;
import com.xo.common.annotation.TbSyncLogAnnotation;
import com.xo.service.AnnotationService;
import org.springframework.stereotype.Service;

/**
 * @author: xo
 * @DATE: 2020/9/3
 * @Description: TestServiceimpl
 **/
@Service
public class AnnotationServiceImpl implements AnnotationService {

    @Override
    @TbSyncLogAnnotation(type = 1, tableName = "myTableName")
    public int test() {
        System.out.println("test");
        return 1;
    }
    @Override
    @SendMailAnnotation()
    public int sendMailTest() {
        int i=1/0;
        return 1;
    }
}
