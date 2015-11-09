/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

/**
 ************************************************************
 * @类名 : SequenceDataSourceSelector.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class SequenceDataSourceSelector implements DataSourceSelector {
    private static AtomicInteger cal = new AtomicInteger();

    public DataSource select(List<DataSource> slaveDataSources) {
        int index = cal.getAndIncrement();
        if (index >= slaveDataSources.size()) {
            cal.set(0);
            index = 0;
        }
        return slaveDataSources.get(index);
    }
}
