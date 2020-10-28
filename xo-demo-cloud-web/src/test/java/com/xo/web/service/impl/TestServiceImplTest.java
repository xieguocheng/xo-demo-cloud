package com.xo.web.service.impl;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.model.PutObjectResult;
import com.xo.WebApplicationTest;
import com.xo.oss.util.OssUtil;
import com.xo.web.service.AnnotationService;
import com.xo.common.utils.SendMailUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author: xo
 * @DATE: 2020/9/3
 * @Description: TestServiceImplTest
 **/
public class TestServiceImplTest extends WebApplicationTest {

    @Resource
    AnnotationService annotationService;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private SendMailUtil sendMailUtil;

    @Test
    public void annotationTest() {
        annotationService.sendMailTest();
    }

    @Test
    public void threadPoolTest() throws ExecutionException, InterruptedException {
//        threadPoolService.common();
        ListenableFuture<String> async1 = threadPoolService.async1();
        ListenableFuture<String> async2 = threadPoolService.async2();

       while (true){
           if (async1.get()!=null&&async2.get()!=null){
               System.out.println("over!!");
               System.out.println(async1.get());
               System.out.println(async2.get());
               break;
           }
       }
        System.out.println("===");

    }

    @Test
    public void testSendMail() throws IOException {
        sendMailUtil.sendAttachmentMail(SendMailUtil.DEFAULT_RECEIVE,"test","test dasf");
    }

    @Autowired
    OssUtil ossUtil;

    @Test
    public void testOss() throws FileNotFoundException {
        String name = UUID.randomUUID().toString();
//        List<String> strings = ossUtil.listFiles("xo/demo");
//        System.out.println(strings);
        File file = new File("C:\\Users\\XO\\Desktop\\1603515261(1).jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        PutObjectResult putObjectResult = ossUtil.writInputStream("file/" + name, fileInputStream);
//        PutObjectResult putObjectResult = ossUtil.writFile("xo/demo/"+name, file);
        System.out.println(putObjectResult);
    }






}