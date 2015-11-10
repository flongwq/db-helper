/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 ************************************************************
 * @类名 : DataSourceAspect.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月10日
 ************************************************************
 */
@Component
@Aspect
@Order(0)
public class DataSourceAspect {
    @Pointcut("@annotation(com.meila.meigou.dbhelper.annotation.Master)")
    public void masterPoint() {
    }

    @Pointcut("@annotation(com.meila.meigou.dbhelper.annotation.Slave)")
    public void slavePoint() {
    }

    @Around("masterPoint()")
    public Object master(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments = pjp.getArgs();
        DataSourceHolder.setMaster();
        Object result = null;
        if ((arguments != null) && (arguments.length != 0)) {
            result = pjp.proceed(arguments);
        } else {
            result = pjp.proceed();
        }
        return result;
    }

    @Around("slavePoint()")
    public Object slave(ProceedingJoinPoint pjp) throws Throwable {
        Object[] arguments = pjp.getArgs();
        DataSourceHolder.setSlave();
        Object result = null;
        if ((arguments != null) && (arguments.length != 0)) {
            result = pjp.proceed(arguments);
        } else {
            result = pjp.proceed();
        }
        return result;
    }
}
