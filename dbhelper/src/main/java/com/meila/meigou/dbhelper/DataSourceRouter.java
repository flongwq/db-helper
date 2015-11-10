/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 ************************************************************
 * @类名 : DataSourceRouter.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class DataSourceRouter extends AbstractRoutingDataSource {
    private DataSource master;

    private List<DataSource> slaves;

    private DataSourceSelector selector;

    @Override
    protected DataSource determineTargetDataSource() {
        if (DataSourceHolder.isMaster()) {
            return master;
        } else if (DataSourceHolder.isSlave()) {
            // selector = new SequenceDataSourceSelector();
            DataSource ds = selector.select(slaves);
            return ds;
        } else {
            return master;
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        // do nothing
    }

    public DataSource getMaster() {
        return master;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public List<DataSource> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<DataSource> slaves) {
        this.slaves = slaves;
    }

    public DataSourceSelector getSelector() {
        return selector;
    }

    public void setSelector(DataSourceSelector selector) {
        this.selector = selector;
    }

}
