/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 ************************************************************
 * @类名 : SequenceSqlSessionFactorySelector.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */

public class SequenceSqlSessionFactorySelector implements SqlSessionFactorySelector {
    private static AtomicInteger cal = new AtomicInteger();

    @Override
    public SqlSessionFactory select(List<SqlSessionFactory> slaves) {
        int index = cal.getAndIncrement();
        if (index >= slaves.size()) {
            cal.set(1);
            index = 0;
        }
        return slaves.get(index);
    }
}
