/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meila.meigou.dbhelper.db.StudentEntity;

/**
 ************************************************************
 * @类名 : TestService.java
 *
 * @DESCRIPTION :
 * @AUTHOR : flong
 * @DATE : 2015年11月9日
 ************************************************************
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class TestService {
    @Autowired
    private StudentService service;

    @Test
    public void testGet() {
        for (int i = 0; i < 5; i++) {
            StudentEntity entity = service.get(1);
            System.out.println(entity.getName());
        }

    }

    @Test
    public void testUpdate() {
        StudentEntity entity = service.get(1);
        entity.setScore(10);
        service.update(entity);
        System.out.println("ok");
    }
}
