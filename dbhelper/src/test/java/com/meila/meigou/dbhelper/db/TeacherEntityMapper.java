package com.meila.meigou.dbhelper.db;

import com.meila.meigou.dbhelper.db.TeacherEntity;

public interface TeacherEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherEntity record);

    int insertSelective(TeacherEntity record);

    TeacherEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherEntity record);

    int updateByPrimaryKey(TeacherEntity record);
}