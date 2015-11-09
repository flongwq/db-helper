/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.util.List;

import javax.sql.DataSource;

/**
 ************************************************************
 * @类名 : DataSourceSelector.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public interface DataSourceSelector {
    DataSource select(List<DataSource> slaveDataSources);
}
