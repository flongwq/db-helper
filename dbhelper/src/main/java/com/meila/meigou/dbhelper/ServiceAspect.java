/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 ************************************************************
 * @类名 : ServiceAspect.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2016年4月13日
 ************************************************************
 */
public class ServiceAspect {

    public Object doService(ProceedingJoinPoint pjp) throws Throwable {
        if (DataSourceHolder.FORCE_ON_MASTER) {
            DataSourceHolder.setFlag(DataSourceHolder.TRANSACTION_ON);
        }
        Object obj = pjp.proceed();
        if (DataSourceHolder.FORCE_ON_MASTER) {
            DataSourceHolder.clear();
        }
        return obj;
    }

}
