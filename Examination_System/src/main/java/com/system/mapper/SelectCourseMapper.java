package com.system.mapper;

import com.system.po.Select;
import com.system.po.SelectCustom;
import com.system.po.SelectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectCourseMapper {

    int countByExample(SelectExample example);

    int deleteByExample(SelectExample example);

    int insert(Select record);

    int insertSelective(Select record);

    List<Select> selectByExample(SelectExample example);

    int updateByExampleSelective(@Param("record") Select record, @Param("example") SelectExample example);

    int updateByExample(@Param("record") Select record, @Param("example") SelectExample example);

    List<Select> selectByStudentId(Integer studentid);

    List<Select> selectByCourseId(Integer courseid);

   int updateSelectList(@Param("record") Select record);

   int deleteSelect(Integer courseid,Integer studentid);

   int removeAll();
}