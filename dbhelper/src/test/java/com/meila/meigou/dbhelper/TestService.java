/**
 * 
 */
package com.meila.meigou.dbhelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.meila.meigou.dbhelper.db.StudentEntity;
import com.meila.meigou.dbhelper.service.StudentService;

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
        for (int i = 0; i < 10; i++) {
            StudentEntity entity = service.get(1);
            System.out.println(JSON.toJSONString(entity));
        }

    }

    @Test
    public void testUpdate() {
        StudentEntity entity = service.get(1);
        try {
            // StudentEntity entity = new StudentEntity();
            // entity.setId(1);
            entity.setName("ddd");
            service.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    @Test
    public void test() {
        try {
            service.transaction(1);
            // service.transaction2(1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
