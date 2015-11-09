/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 ************************************************************
 * @类名 : MSDataSourceTransactionManager.java
 *
 * @DESCRIPTION :master-slave 事务管理
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class MSDataSourceTransactionManager extends DataSourceTransactionManager {

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean readOnly = definition.isReadOnly();
        if (readOnly) {
            DataSourceHolder.setSlave();
        } else {
            DataSourceHolder.setMaster();
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DataSourceHolder.clear();
    }
}
