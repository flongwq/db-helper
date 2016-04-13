/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NestedRuntimeException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;

/**
 ************************************************************
 * @类名 : DataSourceReadWriteProcessor.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年12月15日
 ************************************************************
 */
public class DataSourceReadWriteProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceReadWriteProcessor.class);

    private Map<String, Boolean> readMethodMap = new HashMap<String, Boolean>();// 放置readonly的method前缀

    private boolean forceReadOnMaster = false;

    public void setForceReadOnMaster(boolean forceReadOnMaster) {
        this.forceReadOnMaster = forceReadOnMaster;
        // 设置全局变量
        DataSourceHolder.FORCE_ON_MASTER = forceReadOnMaster;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof NameMatchTransactionAttributeSource)) {
            return bean;
        }
        try {
            Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
            nameMapField.setAccessible(true);
            NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource) bean;
            Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);
            for (Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
                RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute) entry.getValue();
                // 只有标记了readonly方法的，需要转到从库读
                if (!attr.isReadOnly()) {
                    continue;
                }
                String methodName = entry.getKey();
                Boolean isReadOnMaster = Boolean.FALSE;
                if (forceReadOnMaster) {
                    // 支持当前事务，如果当前没有事务，就以非事务方式执行。
                    attr.setPropagationBehavior(Propagation.SUPPORTS.value());
                    isReadOnMaster = Boolean.TRUE;
                } else {
                    // 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
                    attr.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
                }
                logger.warn("DataSourceReadWriteProcess method:{}, read on master:{}", methodName, isReadOnMaster);
                readMethodMap.put(methodName, isReadOnMaster);
            }
        } catch (Exception e) {
            throw new DataSourceReadWriteException("DataSourceReadWrite error", e);
        }

        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        return bean;
    }

    private class DataSourceReadWriteException extends NestedRuntimeException {
        public DataSourceReadWriteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public Object determineReadOrWrite(ProceedingJoinPoint pjp) throws Throwable {
        if (isReadOnMaster(pjp.getSignature().getName())) {
            DataSourceHolder.setMaster();
        } else {
            DataSourceHolder.setSlave();
        }
        try {
            return pjp.proceed();
        } finally {
            if (!DataSourceHolder.onTransaction()) {// 同一事物中不清理theadlocal
                DataSourceHolder.clear();
            }
        }

    }

    private boolean isReadOnMaster(String methodName) {
        String bestNameMatch = null;
        for (String mappedName : this.readMethodMap.keySet()) {
            if (isMatch(methodName, mappedName)) {
                bestNameMatch = mappedName;
                break;
            }
        }
        if (bestNameMatch == null) {// method的前缀没有设置过readonly，读master
            return true;
        }
        boolean isReadOnlyConfig = false;
        Boolean isReadOnMaster = readMethodMap.get(bestNameMatch);
        if (isReadOnMaster == null) {// map中不存在method，说明method没有设置过readonly，不走从库
            return true;
        } else {
            isReadOnlyConfig = true;
        }
        if (isReadOnMaster == Boolean.FALSE) {// 设置过NOT_SUPPORTS事务挂起，开始读从库
            return false;
        } else {// 开启了强制读master
            if (DataSourceHolder.isMaster() == false && DataSourceHolder.isSlave() == false) {
                // 说明是first request，应该以tx:method配置为准
                return !isReadOnlyConfig;
            } else if (DataSourceHolder.isSlave()) {
                // 如果之前选择了slave 现在还选择 slave
                return false;
            } else {
                return true;
            }

        }
    }

    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }
}
