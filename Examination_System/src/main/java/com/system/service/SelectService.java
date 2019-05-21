package com.system.service;

import com.system.po.CourseCustom;
import com.system.po.Select;
import com.system.po.SelectCustom;

import java.util.List;

public interface SelectService {

    //查询指定课题
    SelectCustom findOne(SelectCustom selectCustom) throws Exception;

    //查询全表
    List<SelectCustom> findAll() throws Exception;


    //查询某学生
    List<SelectCustom> findByStudentid(Integer studentid) throws Exception;

    //查询某课题
    List<SelectCustom> findByCourseid(Integer studentid) throws Exception;

    //插入选题
    void save(SelectCustom selectCustom) throws Exception;

    //题目分配

    //删除某题目
    void remove(SelectCustom selectCustom) throws Exception;

//    更新信息
     void updateSelectList(SelectCustom selectCustom)throws Exception;

    void removeAll()throws Exception;
    }

