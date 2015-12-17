/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 ************************************************************
 * @类名 : SqlSessionFactorySelector.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public interface SqlSessionFactorySelector {
    SqlSessionFactory select(List<SqlSessionFactory> slaves);
}
