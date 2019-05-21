package com.system.mapper;

import com.system.po.CourseCustom;
import com.system.po.PagingVO;

import java.util.List;


public interface CourseMapperCustom {

    //分页查询专业信息
    List<CourseCustom> findByPaging(PagingVO pagingVO) throws Exception;

    //分页查询学生信息
    List<CourseCustom> findStudentByPaging(PagingVO pagingVO) throws Exception;

    //分页查询教师信息
    List<CourseCustom> findTeacherByPaging(PagingVO pagingVO) throws Exception;



}
