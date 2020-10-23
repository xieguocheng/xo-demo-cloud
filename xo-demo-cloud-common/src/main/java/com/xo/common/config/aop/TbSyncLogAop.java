package com.xo.common.config.aop;

import com.xo.common.annotation.TbSyncLogAnnotation;
import com.xo.mapper.TbSyncLogMapper;
import com.xo.model.TbSyncLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author: xo
 * @DATE: 2020/9/3
 * @Description: TbSyncLogAop
 **/
@Slf4j
@Aspect
@Component
public class TbSyncLogAop {

    @Resource
    private TbSyncLogMapper tbSyncLogMapper;

    @Pointcut(value = "@annotation(com.xo.common.annotation.TbSyncLogAnnotation)")
    public void tbSyncLogService() {

    }

    @Around("tbSyncLogService()")
    public Object recordTbSyncLog(ProceedingJoinPoint point) throws Throwable {
        //执行业务前添加日志
        TbSyncLog tbSyncLog = handleBefore(point);
        //执行业务
        Object result = null;
        int recordCnt = 0;
        try {
            result = point.proceed();
            recordCnt =(int)result;
            if(recordCnt<=0){
                return result;
            }
        } catch (Throwable throwable) {
           log.error("TbSyncLogAop recordTbSyncLog error!");
        }
        //执行业务后更新日志
        handleAfter(tbSyncLog,recordCnt);
        return result;
    }

    private void handleAfter(TbSyncLog tbSyncLog,int recordCnt) {
        try {
            tbSyncLog.setStatus(2);
            tbSyncLog.setEndTime(new Date());
            tbSyncLog.setRecordCnt((long)recordCnt);
            tbSyncLogMapper.updateByPrimaryKeySelective(tbSyncLog);
        } catch (Exception e) {
            log.error("TbSyncLogAop handleAfter error!");
        }
    }


    private TbSyncLog handleBefore(ProceedingJoinPoint point) {
        TbSyncLog tbSyncLog = new TbSyncLog();
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            //得到目标方法
            Method method = signature.getMethod();
            //得到方法之上的注解
            TbSyncLogAnnotation tbSyncLogAnnotation = method.getAnnotation(TbSyncLogAnnotation.class);
            //获取注解参数
            String tableName = tbSyncLogAnnotation.tableName();
            int type = tbSyncLogAnnotation.type();
            //添加日志记录
            tbSyncLog.setDateNo(20200903L);
            tbSyncLog.setStartTime(new Date());
            tbSyncLog.setCreateTime(new Date());
            tbSyncLog.setTime(22);
            tbSyncLog.setRecordCnt(0L);
            tbSyncLog.setState(1);
            tbSyncLog.setStatus(1);
            tbSyncLog.setLinkRatio(0D);
            tbSyncLog.setTableName(tableName);
            tbSyncLog.setType(type);
            tbSyncLogMapper.insertSelective(tbSyncLog);
        } catch (Exception e) {
            log.error("TbSyncLogAop handleBefore error!");
        }
        return tbSyncLog;
    }


//            // 获取方法名
//            String methodName = point.getSignature().getName();
//            // 反射获取目标类
//            Class<?> targetClass = point.getTarget().getClass();
//            // 拿到方法对应的参数类型
//            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
//            // 根据类、方法、参数类型（重载）获取到方法的具体信息
//            Method objMethod = targetClass.getMethod(methodName, parameterTypes);
//            // 拿到方法定义的注解信息
//            TbSyncLogAnnotation tbSyncLogAnnotation = objMethod.getDeclaredAnnotation(TbSyncLogAnnotation.class);

}
