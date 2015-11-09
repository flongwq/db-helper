/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.springframework.beans.factory.annotation.Autowired;

import com.meila.meigou.dbhelper.db.StudentEntity;
import com.meila.meigou.dbhelper.db.StudentEntityMapper;

/**
 ************************************************************
 * @类名 : ServiceTest.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
public class StudentService {
    @Autowired
    private StudentEntityMapper mapper;

    public StudentEntity get(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    public void update(StudentEntity entity) {
        mapper.updateByPrimaryKey(entity);
    }
}
