/**
 * 
 */
package com.meila.meigou.dbhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meila.meigou.dbhelper.annotation.Master;
import com.meila.meigou.dbhelper.db.StudentEntity;
import com.meila.meigou.dbhelper.db.StudentEntityMapper;
import com.meila.meigou.dbhelper.db.TeacherEntity;
import com.meila.meigou.dbhelper.db.TeacherEntityMapper;

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
    @Autowired
    private TeacherEntityMapper teacherEntityMapper;

    // @Master
    public StudentEntity get(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    // @Slave
    public void update(StudentEntity entity) {
        mapper.updateByPrimaryKey(entity);
        // entity.setName(null);
        // mapper.updateByPrimaryKey(entity);
    }

    public void delete(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("fff");
        }

    }

    @Master
    public void transaction(int id) throws Exception {
        // 先查询
        System.out.println("start");
        StudentEntity entity = mapper.selectByPrimaryKey(id);
        System.out.println("select 1:" + entity.getName());

        StudentEntity entity2 = new StudentEntity();
        entity2.setId(1);
        entity2.setName("new");
        int result = mapper.updateByPrimaryKey(entity2);
        System.out.println("update:" + result);

        StudentEntity entity3 = mapper.selectByPrimaryKey(id);
        System.out.println("select 2:" + entity3.getName());
        for (int i = 0; i < 10; i++) {
            StudentEntity entityi = mapper.selectByPrimaryKey(id);
            System.out.println("select " + i + ":" + entityi.getName());
        }
    }

    public void transaction2(int id) throws Exception {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setId(1);
        teacherEntity.setName("teachernew");
        int result = teacherEntityMapper.updateByPrimaryKey(teacherEntity);
        TeacherEntity teacherEntity2 = teacherEntityMapper.test(1);
        System.out.println(teacherEntity2.getName());
        // 先查询
        System.out.println("start");
        StudentEntity entity = mapper.selectByPrimaryKey(id);
        System.out.println("select 1:" + entity.getName());
        entity.setName("studentnew");
        result = mapper.updateByPrimaryKey(entity);
        System.out.println("update:" + result);

        // entity.setName(null);
        // result = mapper.updateByPrimaryKey(entity);
        entity = mapper.selectByPrimaryKey(id);
        System.out.println("select 2:" + entity.getName());
    }
}
