/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class StudentService {
    @Autowired
    private StudentEntityMapper mapper;

    // @Master
    public StudentEntity get(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    // @Slave
    public void update(StudentEntity entity) {
        mapper.updateByPrimaryKey(entity);
    }
}
