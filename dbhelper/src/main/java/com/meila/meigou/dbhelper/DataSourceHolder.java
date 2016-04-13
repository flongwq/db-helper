/**
 * 
 */
package com.meila.meigou.dbhelper;

/**
 ************************************************************
 * @类名 : DataSourceHolder.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class DataSourceHolder {
    public final static String MASTER = "master";
    public final static String SLAVE = "slave";
    public final static String TRANSACTION_ON = "on";
    public final static String TRANSACTION_OFF = "off";
    private static ThreadLocal<String> datasource = new ThreadLocal<String>();
    private static ThreadLocal<String> transactionFlag = new ThreadLocal<String>();

    public static String getMaster() {
        return MASTER;
    }

    public static void setMaster() {
        datasource.set(MASTER);
    }

    public static String getSlave() {
        return SLAVE;
    }

    public static void setSlave() {
        datasource.set(SLAVE);
    }

    public static boolean isMaster() {
        return datasource.get() == MASTER;
    }

    public static boolean isSlave() {
        return datasource.get() == SLAVE;
    }

    public static boolean isNull() {
        return datasource.get() == null;
    }

    public static void clear() {
        datasource.remove();
    }

    public static String get() {
        return datasource.get();
    }

    public static String getFlag() {
        return transactionFlag.get();
    }

    public static void setFlag(String flag) {
        transactionFlag.set(flag);
    }

    public static boolean onTransaction() {
        return transactionFlag.get() == TRANSACTION_ON;
    }
}
