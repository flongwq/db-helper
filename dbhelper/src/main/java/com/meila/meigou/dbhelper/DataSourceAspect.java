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
    public void master(ProceedingJoinPoint pjp) throws Throwable {
        DataSourceHolder.setMaster();
        DataSourceHolder.setFlag(DataSourceHolder.TRANSACTION_ON);
        pjp.proceed();
        DataSourceHolder.clear();
    }

    @Around("slavePoint()")
    public void slave(ProceedingJoinPoint pjp) throws Throwable {
        DataSourceHolder.setSlave();
        pjp.proceed();
        DataSourceHolder.clear();
    }
}
