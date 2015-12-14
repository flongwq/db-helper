/**
 * 
 */
package com.meila.meigou.dbhelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private StudentService service;

    @Test
    public void testGet() {
        for (int i = 0; i < 1; i++) {
            StudentEntity entity = service.get(1);
            System.out.println(entity.getName());
        }

    }

    @Test
    public void testUpdate() {
        // StudentEntity entity = service.get(1);
        StudentEntity entity = new StudentEntity();
        entity.setId(1);
        entity.setName("flongnew");
        entity.setScore(30);
        service.update(entity);
        System.out.println("ok");
    }

    @Test
    public void test() {
        Long max = 2199023255552L;
        Date date = new Date(max);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }
}
