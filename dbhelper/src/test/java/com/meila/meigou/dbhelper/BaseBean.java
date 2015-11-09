/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.lang.reflect.Field;

/**
 ************************************************************
 * @类名 : BaseBean.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class BaseBean {
    public String toString() {
        StringBuilder sb = new StringBuilder(2048);// 避免经常扩大
        try {
            Class<?> t = this.getClass();
            Field[] fields = t.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                boolean bAccess = field.isAccessible();
                try {
                    if (!bAccess) {
                        field.setAccessible(true);
                    }
                    sb.append('|');
                    sb.append(field.getName().toLowerCase());
                    sb.append('=');
                    if (field.getType() == Integer.class) {
                        sb.append(field.getInt(this));
                    } else if (field.getType() == Long.class) {
                        sb.append(field.getLong(this));
                    } else if (field.getType() == Boolean.class) {
                        sb.append(field.getBoolean(this));
                    } else if (field.getType() == char.class) {
                        sb.append(field.getChar(this));
                    } else if (field.getType() == Double.class) {
                        sb.append(field.getDouble(this));
                    } else if (field.getType() == Float.class) {
                        sb.append(field.getFloat(this));
                    } else {
                        sb.append(field.get(this));
                    }
                } finally {
                    if (!bAccess) {
                        field.setAccessible(bAccess);
                    }
                }
            }
        } catch (Exception e) {
        }

        return sb.toString();
    }
}
