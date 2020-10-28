package com.xo.common.config.aop;

import com.xo.common.annotation.SendMailAnnotation;
import com.xo.common.utils.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: SendMailAop
 **/
@Slf4j
@Aspect
@Component
public class SendMailAop {

    @Resource
    SendMailUtil sendMailUtil;

    @Pointcut(value = "@annotation(com.xo.common.annotation.SendMailAnnotation)")
    public void SendMailService() {
    }

    /**
     * 切面报错，发送邮件
     **/
    @AfterThrowing(value = "SendMailService()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint,Throwable exception) {
        //得到目标方法，以及注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SendMailAnnotation tbSyncLogAnnotation = method.getAnnotation(SendMailAnnotation.class);
        //获取注解参数
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String[] to = tbSyncLogAnnotation.to();
        String subject = tbSyncLogAnnotation.subject();
        String content = tbSyncLogAnnotation.content();
        //异常信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        for (String str : to) {
            if(StringUtils.isBlank(subject)){
                subject="SendMailAnnotation: 类"+className+" 方法"+methodName;
            }
            if(StringUtils.isBlank(content)){
                content=sw.toString();
            }
            log.info("begin发送邮件异常信息");
            sendMailUtil.sendTextMail(str,subject,content);
            log.info("end发送邮件异常信息");
        }

    }

}
